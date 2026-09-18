package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.BookmarkDao
import com.example.data.local.dao.ConsultationDao
import com.example.data.local.dao.IkmSurveyDao
import com.example.data.local.dao.ServiceApplicationDao
import com.example.data.local.dao.StaffDao
import com.example.data.local.entity.BookmarkEntity
import com.example.data.local.entity.ConsultationEntity
import com.example.data.local.entity.IkmSurveyEntity
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.data.local.entity.StaffEntity

@Database(
    entities = [
        ServiceApplicationEntity::class,
        ConsultationEntity::class,
        BookmarkEntity::class,
        StaffEntity::class,
        IkmSurveyEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class KuaDatabase : RoomDatabase() {
    abstract fun serviceApplicationDao(): ServiceApplicationDao
    abstract fun consultationDao(): ConsultationDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun staffDao(): StaffDao
    abstract fun ikmSurveyDao(): IkmSurveyDao

    companion object {
        @Volatile
        private var INSTANCE: KuaDatabase? = null

        fun getDatabase(context: Context): KuaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KuaDatabase::class.java,
                    "kua_biringbulu_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
