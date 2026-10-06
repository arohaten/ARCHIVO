package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dossiers")
data class DossierEntity(
    @PrimaryKey val id: String, // e.g. "VLC-014"
    val codeNumber: Int,
    val title: String,
    val classification: String, // DESCLASIFICADO PARCIAL, RESTRINGIDO, CONFIDENCIAL, etc.
    val accessLevel: String, // NIVEL I, II, III, MÁXIMO
    val status: String, // RECUPERADO, PARCIALMENTE RECUPERADO, CORRUPTO, NO LOCALIZADO, DESAPARECIDO, etc.
    val dateString: String,
    val location: String,
    val referenceCode: String,
    val registeredElements: String,
    val summary: String,
    val incidentReport: String,
    val recoveredMaterial: String,
    val missingMaterial: String,
    val crossReferences: String, // comma-separated codes: "VLC-027,VLC-134,VLC-402"
    val observations: String,
    val isCensored: Boolean = false,
    val censoredText: String = "",
    val drawableResName: String = "",
    val pagesCount: String = "01/01",
    val isMissing: Boolean = false,
    val missingReason: String = ""
)

@Entity(tableName = "evidences")
data class EvidenceEntity(
    @PrimaryKey val id: String, // e.g. "VLC-E014-B"
    val originDossierCode: String,
    val type: String, // FOTOGRAFÍA, VÍDEO, AUDIO, DOCUMENTO, CAPTURA, REGISTRO TÉCNICO
    val dateRecovery: String,
    val status: String, // VERIFICACIÓN PENDIENTE, AUTENTICADO, CORRUPTO, ANOMALÍA CONFIRMADA
    val description: String,
    val location: String,
    val technicalNotes: String,
    val drawableResName: String = "",
    val audioDuration: String = "",
    val isCitizenSubmitted: Boolean = false,
    val submitterTag: String = ""
)

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: String, // e.g. "LOC-311-A"
    val code: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val area: String,
    val relatedDossier: String,
    val description: String,
    val status: String,
    val visibilityLevel: String // PÚBLICA, PARCIAL, CENSURADA, EVENTO_NO_LUGAR
)

@Entity(tableName = "chronologies")
data class ChronologyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val year: Int,
    val dateFormatted: String,
    val title: String,
    val relatedDossier: String,
    val description: String,
    val isClassified: Boolean = false
)

@Entity(tableName = "citizen_reports")
data class CitizenReportEntity(
    @PrimaryKey val trackingCode: String, // e.g. "REC-VLC-8842"
    val timestamp: String,
    val municipality: String,
    val category: String, // ANOMALÍA VISUAL, CÁMARA SEGURIDAD / DASHCAM, VIBRACIÓN / AUDIO, MARCADO PERIMETRAL, OTRO
    val description: String,
    val status: String, // EN CUARENTENA / COTEJO ACTIVO, ASOCIADO A EXPEDIENTE, ARCHIVADO PENDIENTE
    val matchedDossier: String = "VLC-???",
    val isUrgent: Boolean = false,
    val verificationNotes: String = "Pendiente de cotejo espectral y extracción pericial de metadatos."
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val actionCode: String,
    val description: String,
    val level: String // INFO, WARNING, ALERT, BREACH
)
