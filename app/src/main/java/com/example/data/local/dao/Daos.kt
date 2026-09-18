package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.BookmarkEntity
import com.example.data.local.entity.ConsultationEntity
import com.example.data.local.entity.IkmSurveyEntity
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.data.local.entity.StaffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceApplicationDao {
    @Query("SELECT * FROM service_applications ORDER BY createdAtTimestamp DESC")
    fun getAllApplications(): Flow<List<ServiceApplicationEntity>>

    @Query("SELECT * FROM service_applications WHERE trackingCode = :trackingCode LIMIT 1")
    suspend fun getApplicationByTrackingCode(trackingCode: String): ServiceApplicationEntity?

    @Query("SELECT * FROM service_applications WHERE id = :id LIMIT 1")
    suspend fun getApplicationById(id: Long): ServiceApplicationEntity?

    @Query("SELECT COUNT(*) FROM service_applications")
    suspend fun getApplicationCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(application: ServiceApplicationEntity): Long

    @Update
    suspend fun updateApplication(application: ServiceApplicationEntity)

    @Query("DELETE FROM service_applications WHERE id = :id")
    suspend fun deleteApplicationById(id: Long)
}

@Dao
interface ConsultationDao {
    @Query("SELECT * FROM consultations ORDER BY timestamp DESC")
    fun getAllConsultations(): Flow<List<ConsultationEntity>>

    @Query("SELECT COUNT(*) FROM consultations")
    suspend fun getConsultationCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsultation(consultation: ConsultationEntity): Long

    @Query("DELETE FROM consultations WHERE id = :id")
    suspend fun deleteConsultationById(id: Long)
}

@Dao
interface BookmarkDao {
    @Query("SELECT serviceId FROM service_bookmarks")
    fun getAllBookmarkedServiceIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM service_bookmarks WHERE serviceId = :serviceId)")
    fun isBookmarked(serviceId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM service_bookmarks WHERE serviceId = :serviceId")
    suspend fun deleteBookmarkByServiceId(serviceId: Int)
}

@Dao
interface StaffDao {
    @Query("SELECT * FROM kua_staff ORDER BY id ASC")
    fun getAllStaff(): Flow<List<StaffEntity>>

    @Query("SELECT * FROM kua_staff WHERE id = :id LIMIT 1")
    suspend fun getStaffById(id: String): StaffEntity?

    @Query("SELECT COUNT(*) FROM kua_staff")
    suspend fun getStaffCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: StaffEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStaff(staffList: List<StaffEntity>)

    @Update
    suspend fun updateStaff(staff: StaffEntity)

    @Query("DELETE FROM kua_staff WHERE id = :id")
    suspend fun deleteStaffById(id: String)
}

@Dao
interface IkmSurveyDao {
    @Query("SELECT * FROM ikm_surveys ORDER BY timestamp DESC")
    fun getAllSurveys(): Flow<List<IkmSurveyEntity>>

    @Query("SELECT COUNT(*) FROM ikm_surveys")
    suspend fun getSurveyCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurvey(survey: IkmSurveyEntity): Long

    @Query("DELETE FROM ikm_surveys WHERE id = :id")
    suspend fun deleteSurveyById(id: Long)
}
