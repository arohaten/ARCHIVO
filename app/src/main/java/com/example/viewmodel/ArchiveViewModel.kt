package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ArchiveDatabase
import com.example.data.local.SeedData
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ArchiveViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ArchiveDatabase.getInstance(application).archiveDao()

    val dossiers: StateFlow<List<DossierEntity>> = dao.getAllDossiers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val missingDossiers: StateFlow<List<DossierEntity>> = dao.getMissingDossiers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val evidences: StateFlow<List<EvidenceEntity>> = dao.getAllEvidences()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val locations: StateFlow<List<LocationEntity>> = dao.getAllLocations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chronologies: StateFlow<List<ChronologyEntity>> = dao.getAllChronologies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val citizenReports: StateFlow<List<CitizenReportEntity>> = dao.getAllCitizenReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val auditLogs: StateFlow<List<AuditLogEntity>> = dao.getAllAuditLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDossier = MutableStateFlow<DossierEntity?>(null)
    val selectedDossier: StateFlow<DossierEntity?> = _selectedDossier.asStateFlow()

    val searchQuery = MutableStateFlow("")
    val filterStatus = MutableStateFlow("TODOS")

    val filteredDossiers = combine(dossiers, searchQuery, filterStatus) { list, query, filter ->
        list.filter { dossier ->
            val matchesQuery = query.isBlank() ||
                    dossier.id.contains(query, ignoreCase = true) ||
                    dossier.title.contains(query, ignoreCase = true) ||
                    dossier.location.contains(query, ignoreCase = true) ||
                    dossier.registeredElements.contains(query, ignoreCase = true)

            val matchesFilter = when (filter) {
                "TODOS" -> true
                "RECUPERADO" -> dossier.status.contains("RECUPERADO")
                "PARCIAL" -> dossier.status.contains("PARCIAL")
                "CORRUPTO" -> dossier.status.contains("CORRUPTO")
                "NO LOCALIZADO" -> dossier.status.contains("NO LOCALIZADO")
                "DESAPARECIDO" -> dossier.status.contains("DESAPARECIDO")
                "EN INVESTIGACIÓN" -> dossier.status.contains("INVESTIGACIÓN")
                else -> true
            }
            matchesQuery && matchesFilter
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isAdminAuthenticated = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            // Seed database if empty
            val current = dao.getDossierById("VLC-000")
            if (current == null) {
                dao.insertDossiers(SeedData.dossiers)
                dao.insertEvidences(SeedData.evidences)
                dao.insertLocations(SeedData.locations)
                dao.insertChronologies(SeedData.chronologies)
                for (report in SeedData.citizenReports) {
                    dao.insertCitizenReport(report)
                }
                dao.insertAuditLogs(SeedData.auditLogs)
            }
        }
    }

    fun selectDossier(dossier: DossierEntity?) {
        _selectedDossier.value = dossier
    }

    fun selectDossierById(id: String) {
        viewModelScope.launch {
            val d = dao.getDossierById(id)
            _selectedDossier.value = d
        }
    }

    fun submitCitizenReport(
        municipality: String,
        category: String,
        description: String,
        isUrgent: Boolean,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val codeNum = (1000..9999).random()
            val trackingCode = "REC-VLC-$codeNum"
            val nowStr = SimpleDateFormat("dd/MM/yyyy – HH:mm", Locale.getDefault()).format(Date())

            val report = CitizenReportEntity(
                trackingCode = trackingCode,
                timestamp = nowStr,
                municipality = municipality.ifBlank { "Término no declarado" },
                category = category,
                description = description,
                status = "EN CUARENTENA / COTEJO ACTIVO",
                matchedDossier = if (category.contains("VISUAL")) "VLC-014" else if (category.contains("AUDIO") || category.contains("DASHCAM")) "VLC-041" else "VLC-311",
                isUrgent = isUrgent,
                verificationNotes = "Registro recibido mediante terminal pública. Asignado a cuarentena de aislamiento pericial."
            )
            dao.insertCitizenReport(report)

            dao.insertAuditLog(
                AuditLogEntity(
                    timestamp = nowStr,
                    actionCode = "CITIZEN-SUBMISSION",
                    description = "Aportación ciudadana incorporada: $trackingCode ($municipality). Categoría: $category.",
                    level = if (isUrgent) "ALERT" else "INFO"
                )
            )
            onSuccess(trackingCode)
        }
    }

    fun toggleCensor(dossierId: String) {
        viewModelScope.launch {
            val d = dao.getDossierById(dossierId) ?: return@launch
            val updated = d.copy(isCensored = !d.isCensored)
            dao.updateDossier(updated)
            _selectedDossier.value = updated
        }
    }

    fun saveDossier(dossier: DossierEntity) {
        viewModelScope.launch {
            dao.insertDossier(dossier)
            val nowStr = SimpleDateFormat("dd/MM/yyyy – HH:mm", Locale.getDefault()).format(Date())
            dao.insertAuditLog(
                AuditLogEntity(
                    timestamp = nowStr,
                    actionCode = "ADMIN-UPDATE",
                    description = "Expediente ${dossier.id} actualizado o insertado por administrador.",
                    level = "INFO"
                )
            )
        }
    }

    fun addLocation(loc: LocationEntity) {
        viewModelScope.launch {
            dao.insertLocation(loc)
        }
    }

    fun addAuditEntry(action: String, desc: String, level: String = "INFO") {
        viewModelScope.launch {
            val nowStr = SimpleDateFormat("dd/MM/yyyy – HH:mm", Locale.getDefault()).format(Date())
            dao.insertAuditLog(AuditLogEntity(timestamp = nowStr, actionCode = action, description = desc, level = level))
        }
    }

    fun authenticateAdmin(pass: String): Boolean {
        val clean = pass.trim()
        val success = clean == "Archivo27798" || clean.equals("Archivo27798", ignoreCase = true) ||
                clean == "014" || clean == "VLC-ROOT" || clean == "vlc2026"
        isAdminAuthenticated.value = success
        if (success) {
            addAuditEntry("ADMIN-LOGIN", "Acceso pericial autenticado (Clave raíz: Archivo27798). Modo edición habilitado.", "INFO")
        }
        return success
    }

    fun exitAdminMode() {
        isAdminAuthenticated.value = false
        addAuditEntry("ADMIN-LOGOUT", "Cierre de sesión pericial. Modo consulta restaurado.", "INFO")
    }

    fun deleteDossier(dossier: DossierEntity) {
        viewModelScope.launch {
            dao.deleteDossier(dossier)
            if (_selectedDossier.value?.id == dossier.id) {
                _selectedDossier.value = null
            }
            addAuditEntry("ADMIN-DELETE", "Expediente ${dossier.id} eliminado del repositorio.", "WARNING")
        }
    }

    fun saveEvidence(evidence: EvidenceEntity) {
        viewModelScope.launch {
            dao.insertEvidence(evidence)
            addAuditEntry("EVIDENCE-INSERT", "Nueva evidencia pericial registrada: ${evidence.id} (${evidence.type}).", "INFO")
        }
    }

    fun deleteEvidence(evidence: EvidenceEntity) {
        viewModelScope.launch {
            dao.deleteEvidence(evidence)
            addAuditEntry("EVIDENCE-DELETE", "Evidencia ${evidence.id} eliminada.", "WARNING")
        }
    }

    fun deleteLocation(location: LocationEntity) {
        viewModelScope.launch {
            dao.deleteLocation(location)
            addAuditEntry("LOC-DELETE", "Coordenada ${location.code} eliminada de la red geodésica.", "INFO")
        }
    }

    fun updateReportStatus(report: CitizenReportEntity, newStatus: String) {
        viewModelScope.launch {
            val updated = report.copy(status = newStatus)
            dao.updateCitizenReport(updated)
            addAuditEntry("REPORT-UPDATE", "Informe ${report.trackingCode} actualizado a estado: $newStatus.", "INFO")
        }
    }
}
