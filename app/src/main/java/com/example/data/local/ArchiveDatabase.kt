package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.ArchiveDao
import com.example.data.local.entity.*

@Database(
    entities = [
        DossierEntity::class,
        EvidenceEntity::class,
        LocationEntity::class,
        ChronologyEntity::class,
        CitizenReportEntity::class,
        AuditLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ArchiveDatabase : RoomDatabase() {
    abstract fun archiveDao(): ArchiveDao

    companion object {
        @Volatile
        private var INSTANCE: ArchiveDatabase? = null

        fun getInstance(context: Context): ArchiveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArchiveDatabase::class.java,
                    "archivo_vlc_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
