package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchiveDao {
    // Dossiers
    @Query("SELECT * FROM dossiers ORDER BY codeNumber ASC")
    fun getAllDossiers(): Flow<List<DossierEntity>>

    @Query("SELECT * FROM dossiers WHERE id = :dossierId LIMIT 1")
    suspend fun getDossierById(dossierId: String): DossierEntity?

    @Query("SELECT * FROM dossiers WHERE isMissing = 1")
    fun getMissingDossiers(): Flow<List<DossierEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDossier(dossier: DossierEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDossiers(dossiers: List<DossierEntity>)

    @Update
    suspend fun updateDossier(dossier: DossierEntity)

    @Delete
    suspend fun deleteDossier(dossier: DossierEntity)

    // Evidences
    @Query("SELECT * FROM evidences ORDER BY id ASC")
    fun getAllEvidences(): Flow<List<EvidenceEntity>>

    @Query("SELECT * FROM evidences WHERE originDossierCode = :dossierCode")
    fun getEvidencesForDossier(dossierCode: String): Flow<List<EvidenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvidence(evidence: EvidenceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvidences(evidences: List<EvidenceEntity>)

    @Update
    suspend fun updateEvidence(evidence: EvidenceEntity)

    @Delete
    suspend fun deleteEvidence(evidence: EvidenceEntity)

    // Locations
    @Query("SELECT * FROM locations ORDER BY code ASC")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    // Chronology
    @Query("SELECT * FROM chronologies ORDER BY year ASC, id ASC")
    fun getAllChronologies(): Flow<List<ChronologyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChronologies(chronologies: List<ChronologyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChronology(chronology: ChronologyEntity)

    // Citizen Reports
    @Query("SELECT * FROM citizen_reports ORDER BY timestamp DESC")
    fun getAllCitizenReports(): Flow<List<CitizenReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCitizenReport(report: CitizenReportEntity)

    @Update
    suspend fun updateCitizenReport(report: CitizenReportEntity)

    // Audit Logs
    @Query("SELECT * FROM audit_logs ORDER BY id DESC")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLogs(logs: List<AuditLogEntity>)
}
