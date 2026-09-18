package com.example.data.repository

import com.example.data.KuaServiceData
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
import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem
import com.example.model.KuaStaff
import com.example.model.KuaStaffData
import com.example.model.StaffRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class KuaRepository(
    private val applicationDao: ServiceApplicationDao,
    private val consultationDao: ConsultationDao,
    private val bookmarkDao: BookmarkDao,
    private val staffDao: StaffDao,
    private val ikmSurveyDao: IkmSurveyDao
) {
    val allApplications: Flow<List<ServiceApplicationEntity>> = applicationDao.getAllApplications()
    val allConsultations: Flow<List<ConsultationEntity>> = consultationDao.getAllConsultations()
    val bookmarkedIds: Flow<List<Int>> = bookmarkDao.getAllBookmarkedServiceIds()
    val allIkmSurveys: Flow<List<IkmSurveyEntity>> = ikmSurveyDao.getAllSurveys()

    // Persistent Room Staffing State
    val staffList: Flow<List<KuaStaff>> = staffDao.getAllStaff().map { list ->
        list.map { it.toModel() }
    }

    suspend fun updateStaff(updatedStaff: KuaStaff) {
        staffDao.insertStaff(StaffEntity.fromModel(updatedStaff))
    }

    fun getAllServices(): List<KuaServiceItem> = KuaServiceData.allServices

    fun getServiceById(id: Int): KuaServiceItem? = KuaServiceData.getServiceById(id)

    fun getServicesByCategory(category: KuaServiceCategory): List<KuaServiceItem> =
        KuaServiceData.getServicesByCategory(category)

    fun searchServices(query: String): List<KuaServiceItem> = KuaServiceData.searchServices(query)

    suspend fun getApplicationByTrackingCode(trackingCode: String): ServiceApplicationEntity? =
        applicationDao.getApplicationByTrackingCode(trackingCode)

    suspend fun submitApplication(
        serviceId: Int,
        serviceTitle: String,
        categoryName: String,
        applicantName: String,
        applicantNik: String,
        applicantPhone: String,
        applicantVillage: String,
        applicantAddress: String,
        notes: String,
        appointmentDate: String,
        uploadedDocuments: String = ""
    ): String {
        val dateFormat = SimpleDateFormat("yyMM", Locale.getDefault())
        val randomNum = Random.nextInt(1000, 9999)
        val trackingCode = "KB-${dateFormat.format(Date())}-$randomNum"

        val entity = ServiceApplicationEntity(
            trackingCode = trackingCode,
            serviceId = serviceId,
            serviceTitle = serviceTitle,
            categoryName = categoryName,
            applicantName = applicantName.trim(),
            applicantNik = applicantNik.trim(),
            applicantPhone = applicantPhone.trim(),
            applicantVillage = applicantVillage,
            applicantAddress = applicantAddress.trim(),
            notes = notes.trim(),
            appointmentDate = appointmentDate,
            uploadedDocuments = uploadedDocuments,
            status = "TERKIRIM",
            statusNotes = "Permohonan berhasil didaftarkan di sistem digital KUA Biringbulu.",
            createdAtTimestamp = System.currentTimeMillis(),
            updatedAtTimestamp = System.currentTimeMillis()
        )

        applicationDao.insertApplication(entity)
        return trackingCode
    }

    suspend fun updateApplicationStatus(id: Long, newStatus: String, newNotes: String) {
        val app = applicationDao.getApplicationById(id) ?: return
        val updated = app.copy(
            status = newStatus,
            statusNotes = newNotes,
            updatedAtTimestamp = System.currentTimeMillis()
        )
        applicationDao.updateApplication(updated)
    }

    suspend fun deleteApplication(id: Long) {
        applicationDao.deleteApplicationById(id)
    }

    suspend fun toggleBookmark(serviceId: Int, isCurrentlyBookmarked: Boolean) {
        if (isCurrentlyBookmarked) {
            bookmarkDao.deleteBookmarkByServiceId(serviceId)
        } else {
            bookmarkDao.insertBookmark(BookmarkEntity(serviceId = serviceId))
        }
    }

    suspend fun saveConsultation(
        topic: String,
        question: String,
        answer: String,
        category: String,
        isFromAi: Boolean = true
    ): Long {
        return consultationDao.insertConsultation(
            ConsultationEntity(
                topic = topic,
                question = question,
                answer = answer,
                category = category,
                isFromAi = isFromAi,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteConsultation(id: Long) {
        consultationDao.deleteConsultationById(id)
    }

    suspend fun seedInitialDataIfEmpty() {
        val count = applicationDao.getApplicationCount()
        if (count == 0) {
            seedInitialApplications()
        }
        val consultCount = consultationDao.getConsultationCount()
        if (consultCount == 0) {
            seedInitialConsultations()
        }
        val staffCount = staffDao.getStaffCount()
        if (staffCount == 0) {
            val entities = KuaStaffData.initialStaffList.map { StaffEntity.fromModel(it) }
            staffDao.insertAllStaff(entities)
        }
        val surveyCount = ikmSurveyDao.getSurveyCount()
        if (surveyCount == 0) {
            seedInitialIkmSurveys()
        }
    }

    suspend fun submitIkmSurvey(
        respondentName: String,
        respondentPhone: String = "",
        serviceName: String,
        village: String,
        overallRating: Int,
        ratingRequirements: Int,
        ratingProcedure: Int,
        ratingSpeed: Int,
        ratingCost: Int,
        ratingStaff: Int,
        ratingFacility: Int,
        feedback: String
    ): Long {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
        val entity = IkmSurveyEntity(
            respondentName = if (respondentName.isBlank()) "Warga Biringbulu (Anonim)" else respondentName.trim(),
            respondentPhone = respondentPhone.trim(),
            serviceName = serviceName,
            village = village,
            overallRating = overallRating.coerceIn(1, 5),
            ratingRequirements = ratingRequirements.coerceIn(1, 5),
            ratingProcedure = ratingProcedure.coerceIn(1, 5),
            ratingSpeed = ratingSpeed.coerceIn(1, 5),
            ratingCost = ratingCost.coerceIn(1, 5),
            ratingStaff = ratingStaff.coerceIn(1, 5),
            ratingFacility = ratingFacility.coerceIn(1, 5),
            feedback = feedback.trim(),
            timestamp = System.currentTimeMillis(),
            formattedDate = dateFormat.format(Date())
        )
        return ikmSurveyDao.insertSurvey(entity)
    }

    suspend fun deleteIkmSurvey(id: Long) {
        ikmSurveyDao.deleteSurveyById(id)
    }

    private suspend fun seedInitialIkmSurveys() {
        val s1 = IkmSurveyEntity(
            respondentName = "H. Baso Dg. Sitaba",
            respondentPhone = "0812-4112-xxxx",
            serviceName = "Pendaftaran Nikah (SIMKAH)",
            village = "Tonrorita",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 5,
            ratingSpeed = 5,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 5,
            feedback = "Pelayanan nikah di Balai KUA sangat memuaskan, benar-benar Rp 0,- tanpa biaya tambahan sepeser pun. Penghulu dan staf sangat ramah dan sopan.",
            timestamp = System.currentTimeMillis() - 86400000L * 2,
            formattedDate = "16 September 2026, 10:15"
        )
        val s2 = IkmSurveyEntity(
            respondentName = "Nurhaeni, S.Pd.",
            respondentPhone = "0852-9844-xxxx",
            serviceName = "Surat Rekomendasi Nikah (N10)",
            village = "Kelurahan Lauwa",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 5,
            ratingSpeed = 5,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 4,
            feedback = "Proses rekomendasi nikah luar daerah sangat cepat, berkas diverifikasi secara digital dan langsung selesai dalam 15 menit. Luar biasa PTSP KUA Biringbulu!",
            timestamp = System.currentTimeMillis() - 86400000L * 4,
            formattedDate = "14 September 2026, 11:30"
        )
        val s3 = IkmSurveyEntity(
            respondentName = "Dg. Mangngassai",
            respondentPhone = "0813-5520-xxxx",
            serviceName = "Akta Ikrar Wakaf (AIW)",
            village = "Baturappe",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 5,
            ratingSpeed = 4,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 5,
            feedback = "Pengurusan akta wakaf tanah masjid dibimbing dengan sangat sabar oleh PPAIW KUA. Tidak ada pungutan liar, semuanya transparan dan jelas.",
            timestamp = System.currentTimeMillis() - 86400000L * 7,
            formattedDate = "11 September 2026, 09:45"
        )
        val s4 = IkmSurveyEntity(
            respondentName = "Rahmat Hidayat (Catin)",
            respondentPhone = "0821-8733-xxxx",
            serviceName = "Bimbingan Perkawinan (Bimwin Catin)",
            village = "Pencong",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 5,
            ratingSpeed = 5,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 5,
            feedback = "Materi bimbingan perkawinan sangat bermanfaat bagi bekal rumah tangga kami. Fasilitator dan narasumber menyampaikan materi dengan interaktif dan menyenangkan.",
            timestamp = System.currentTimeMillis() - 86400000L * 9,
            formattedDate = "09 September 2026, 14:00"
        )
        val s5 = IkmSurveyEntity(
            respondentName = "Ust. Syarifuddin",
            respondentPhone = "0813-4299-xxxx",
            serviceName = "Penerbitan ID SIMAS Masjid",
            village = "Berutallasa",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 4,
            ratingSpeed = 5,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 4,
            feedback = "Sertifikat ID SIMAS Nasional langsung terbit dan data masjid langsung sinkron ke Kemenag Pusat. Pelayanan sangat responsif dan amanah.",
            timestamp = System.currentTimeMillis() - 86400000L * 12,
            formattedDate = "06 September 2026, 10:20"
        )
        ikmSurveyDao.insertSurvey(s1)
        ikmSurveyDao.insertSurvey(s2)
        ikmSurveyDao.insertSurvey(s3)
        ikmSurveyDao.insertSurvey(s4)
        ikmSurveyDao.insertSurvey(s5)
    }

    private suspend fun seedInitialApplications() {
        val sample1 = ServiceApplicationEntity(
            trackingCode = "KB-2608-4821",
            serviceId = 1,
            serviceTitle = "Pelayanan Pendaftaran Kehendak Nikah di Balai Nikah",
            categoryName = "Pelayanan Pernikahan",
            applicantName = "Siti Nurhaliza (Catin)",
            applicantNik = "7306085507980002",
            applicantPhone = "0852-9988-7766",
            applicantVillage = "Batumalonro",
            applicantAddress = "Jl. Poros Batumalonro Dusun Balassuka No. 14",
            notes = "Calon Suami: Rahmat Hidayat (Desa Tonrorita). Mohon jadwal pemeriksaan berkas dan Bimwin.",
            appointmentDate = "28 Agustus 2026",
            status = "JADWAL_PEMERIKSAAN",
            statusNotes = "Berkas N1-N4 lengkap terverifikasi. Jadwal pemeriksaan calon pengantin hari Kamis pkl 09:00 WITA di Balai Nikah KUA Biringbulu.",
            createdAtTimestamp = System.currentTimeMillis() - 86400000L * 3,
            updatedAtTimestamp = System.currentTimeMillis() - 3600000L * 4
        )

        val sample2 = ServiceApplicationEntity(
            trackingCode = "KB-2608-8832",
            serviceId = 10,
            serviceTitle = "Pelayanan Surat Rekomendasi Nikah (N10) ke Luar Wilayah",
            categoryName = "Pelayanan Pernikahan",
            applicantName = "Ahmad Fauzi, S.Kom.",
            applicantNik = "7306081205950001",
            applicantPhone = "0812-4567-8901",
            applicantVillage = "Tonrorita",
            applicantAddress = "Dusun Tonrorita Barat RT 02 / RW 01",
            notes = "Tujuan KUA Kecamatan Somba Opu, Kabupaten Gowa. Berkas kelurahan sudah lengkap.",
            appointmentDate = "25 Agustus 2026",
            status = "SELESAI",
            statusNotes = "Surat Rekomendasi Nikah (N10) telah diterbitkan dan ditandatangani Kepala KUA Biringbulu. Dokumen siap diambil di loket PTSP.",
            createdAtTimestamp = System.currentTimeMillis() - 86400000L * 5,
            updatedAtTimestamp = System.currentTimeMillis() - 3600000L * 8
        )

        val sample3 = ServiceApplicationEntity(
            trackingCode = "KB-2608-1094",
            serviceId = 15,
            serviceTitle = "Pelayanan Permohonan Akta Ikrar Wakaf (AIW)",
            categoryName = "Zakat dan Wakaf",
            applicantName = "Ustadz H. Syamsuddin, S.Ag.",
            applicantNik = "7306080504820003",
            applicantPhone = "0813-5544-3322",
            applicantVillage = "Kelurahan Lauwa",
            applicantAddress = "Kompleks Masjid Besar Nurul Iman, Lingkungan Lauwa Timur",
            notes = "Wakaf tanah seluas 450 m2 untuk perluasan sarana pendidikan madrasah / TPA Masjid Nurul Iman.",
            appointmentDate = "30 Agustus 2026",
            status = "VERIFIKASI_BERKAS",
            statusNotes = "PPAIW KUA Biringbulu sedang meneliti sertifikat/alas hak tanah dan kelengkapan saksi ikrar wakaf.",
            createdAtTimestamp = System.currentTimeMillis() - 86400000L * 2,
            updatedAtTimestamp = System.currentTimeMillis() - 3600000L * 2
        )

        val sample4 = ServiceApplicationEntity(
            trackingCode = "KB-2608-7719",
            serviceId = 32,
            serviceTitle = "Pelayanan Sertifikasi Jaminan Produk Halal (Self-Declare)",
            categoryName = "Fungsi Tambahan (Penugasan)",
            applicantName = "Ahmad Fauzi, S.Kom.",
            applicantNik = "7306081205950001",
            applicantPhone = "0812-4567-8901",
            applicantVillage = "Tonrorita",
            applicantAddress = "Dusun Tonrorita Barat RT 02 / RW 01",
            notes = "Usaha Olahan Pangan Lokal Jagung Marning Biringbulu.",
            appointmentDate = "02 September 2026",
            status = "TERKIRIM",
            statusNotes = "Data permohonan sertifikat halal self-declare berhasil diajukan ke Pendamping Proses Produk Halal (P3H) KUA Biringbulu.",
            createdAtTimestamp = System.currentTimeMillis() - 86400000L * 1,
            updatedAtTimestamp = System.currentTimeMillis() - 86400000L * 1
        )

        applicationDao.insertApplication(sample1)
        applicationDao.insertApplication(sample2)
        applicationDao.insertApplication(sample3)
        applicationDao.insertApplication(sample4)
    }

    private suspend fun seedInitialConsultations() {
        val consult1 = ConsultationEntity(
            topic = "Biaya Resmi Pernikahan di Balai Nikah vs Luar Balai",
            question = "Berapa tarif resmi menikah di KUA Biringbulu sesuai aturan Kemenag?",
            answer = "Sesuai PP No. 59 Tahun 2018:\n1. Di Balai Nikah KUA (Hari & Jam Kerja): Rp 0,- (GRATIS tanpa pungli).\n2. Di Luar Balai Nikah / Hari Libur: Dikenakan tarif PNBP Rp 600.000,- yang dibayarkan langsung via kode billing Bank/Pos/SIMKAH, bukan kepada petugas perorangan.\n3. Warga miskin/kurang mampu (SKTM) atau korban bencana: Rp 0,- (Bebas Biaya dengan melampirkan SKTM dari Kepala Desa).",
            category = "Syariah & Layanan",
            isFromAi = true,
            timestamp = System.currentTimeMillis() - 86400000L * 2
        )

        val consult2 = ConsultationEntity(
            topic = "Tata Cara Pendaftaran ID SIMAS Masjid / Musala",
            question = "Bagaimana alur pendaftaran nomor ID SIMAS untuk masjid di desa?",
            answer = "Persyaratan ID SIMAS Kemenag di KUA Biringbulu:\n1. Surat Permohonan dari Pengurus/Takmir Masjid.\n2. SK Susunan Pengurus Takmir dari Desa/Kelurahan.\n3. Dokumen status tanah (Wakaf/Hibah/Hak Milik).\n4. Foto tampak depan masjid & titik koordinat lokasi.\nPetugas KUA Biringbulu akan memverifikasi dan menerbitkan Sertifikat ID SIMAS Nasional secara GRATIS.",
            category = "Kemasjidan",
            isFromAi = true,
            timestamp = System.currentTimeMillis() - 86400000L * 1
        )

        consultationDao.insertConsultation(consult1)
        consultationDao.insertConsultation(consult2)
    }
}
