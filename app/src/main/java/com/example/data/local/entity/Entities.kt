package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.KuaStaff
import com.example.model.StaffRole

@Entity(tableName = "service_applications")
data class ServiceApplicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val trackingCode: String,
    val serviceId: Int,
    val serviceTitle: String,
    val categoryName: String,
    val applicantName: String,
    val applicantNik: String,
    val applicantPhone: String,
    val applicantVillage: String,
    val applicantAddress: String,
    val notes: String,
    val appointmentDate: String,
    val uploadedDocuments: String = "", // Semicolon or JSON separated: "RequirementName:::fileName:::fileUri:::fileSize"
    val status: String = "TERKIRIM", // TERKIRIM, VERIFIKASI_BERKAS, JADWAL_PEMERIKSAAN, SELESAI, DITOLAK
    val statusNotes: String = "Permohonan berhasil dikirim ke loket digital KUA Biringbulu.",
    val createdAtTimestamp: Long = System.currentTimeMillis(),
    val updatedAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "kua_staff")
data class StaffEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val role: String,
    val nip: String,
    val rankGrade: String,
    val positionTitle: String,
    val employmentStatus: String,
    val education: String,
    val appointmentSk: String,
    val serviceArea: String,
    val mainDutiesSerialized: String,
    val phone: String,
    val email: String,
    val scheduleTime: String,
    val roomDesk: String,
    val isDutyActive: Boolean = true,
    val statusMessage: String = "Bertugas melayani masyarakat",
    val initials: String
) {
    fun toModel(): KuaStaff {
        val parsedRole = try {
            StaffRole.valueOf(role)
        } catch (e: Exception) {
            StaffRole.PETUGAS_PTSP
        }
        val duties = if (mainDutiesSerialized.isBlank()) emptyList() else mainDutiesSerialized.split("|||")
        return KuaStaff(
            id = id,
            name = name,
            role = parsedRole,
            nip = nip,
            rankGrade = rankGrade,
            positionTitle = positionTitle,
            employmentStatus = employmentStatus,
            education = education,
            appointmentSk = appointmentSk,
            serviceArea = serviceArea,
            mainDuties = duties,
            phone = phone,
            email = email,
            scheduleTime = scheduleTime,
            roomDesk = roomDesk,
            isDutyActive = isDutyActive,
            statusMessage = statusMessage,
            initials = initials
        )
    }

    companion object {
        fun fromModel(staff: KuaStaff): StaffEntity {
            return StaffEntity(
                id = staff.id,
                name = staff.name,
                role = staff.role.name,
                nip = staff.nip,
                rankGrade = staff.rankGrade,
                positionTitle = staff.positionTitle,
                employmentStatus = staff.employmentStatus,
                education = staff.education,
                appointmentSk = staff.appointmentSk,
                serviceArea = staff.serviceArea,
                mainDutiesSerialized = staff.mainDuties.joinToString("|||"),
                phone = staff.phone,
                email = staff.email,
                scheduleTime = staff.scheduleTime,
                roomDesk = staff.roomDesk,
                isDutyActive = staff.isDutyActive,
                statusMessage = staff.statusMessage,
                initials = staff.initials
            )
        }
    }
}

@Entity(tableName = "consultations")
data class ConsultationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val question: String,
    val answer: String,
    val category: String,
    val isFromAi: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "service_bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val serviceId: Int,
    val bookmarkedAt: Long = System.currentTimeMillis()
)
