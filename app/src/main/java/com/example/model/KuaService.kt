package com.example.model

enum class KuaServiceCategory(
    val categoryId: String,
    val title: String,
    val shortName: String,
    val description: String,
    val icon: String
) {
    PELAYANAN_PERNIKAHAN(
        categoryId = "pernikahan",
        title = "Pelayanan Pernikahan",
        shortName = "Pernikahan",
        description = "Layanan pendaftaran, pencatatan, dokumen, dan administrasi nikah",
        icon = "Favorite"
    ),
    BIMBINGAN_PERKAWINAN(
        categoryId = "bimwin",
        title = "Bimbingan Perkawinan",
        shortName = "Bimwin & Konseling",
        description = "Bimwin Pra-nikah, Keluarga Sakinah, & Mediasi Rumah Tangga",
        icon = "Diversity1"
    ),
    ZAKAT_DAN_WAKAF(
        categoryId = "zakat_wakaf",
        title = "Zakat dan Wakaf",
        shortName = "Zakat & Wakaf",
        description = "Bimbingan zakat, ikrar wakaf (AIW/APAIW), legalitas tanah wakaf & nazhir",
        icon = "VolunteerActivism"
    ),
    KEMASJIDAN(
        categoryId = "kemasjidan",
        title = "Kemasjidan",
        shortName = "Kemasjidan",
        description = "ID SIMAS Masjid/Musala, rekomendasi bantuan, data, dan takmir",
        icon = "Mosque"
    ),
    DATA_KEAGAMAAN(
        categoryId = "data_keagamaan",
        title = "Data Keagamaan",
        shortName = "Data Keagamaan",
        description = "Penyediaan data keagamaan, sarana, majelis taklim & rekomendasi",
        icon = "MenuBook"
    ),
    KETATAUSAHAAN(
        categoryId = "ketatausahaan",
        title = "Ketatausahaan KUA",
        shortName = "Tata Usaha",
        description = "Pelayanan persuratan kedinasan dan kearsipan KUA",
        icon = "Inventory"
    ),
    FUNGSI_TAMBAHAN(
        categoryId = "fungsi_tambahan",
        title = "Fungsi Tambahan (Penugasan)",
        shortName = "Penugasan Menteri",
        description = "Deteksi dini konflik sosial keagamaan & sertifikasi jaminan produk halal",
        icon = "Verified"
    ),
    KONSULTASI_SYARIAH(
        categoryId = "konsultasi_syariah",
        title = "Konsultasi Syariah",
        shortName = "Konsultasi Syariah",
        description = "Konsultasi Hukum Islam (ibadah, waris, keluarga) & kalibrasi arah kiblat",
        icon = "Balance"
    ),
    PENERANGAN_ISLAM(
        categoryId = "penerangan_islam",
        title = "Penerangan Agama Islam",
        shortName = "Penerangan Agama",
        description = "Bimbingan & penyuluhan, sosialisasi produk halal, & cegah konflik sosial",
        icon = "RecordVoiceOver"
    )
}

data class KuaServiceItem(
    val id: Int,
    val category: KuaServiceCategory,
    val numberInCategory: Int,
    val title: String,
    val subtitle: String,
    val legalBasis: String,
    val processingTime: String,
    val cost: String,
    val outputDocument: String,
    val targetAudience: String,
    val requirements: List<String>,
    val procedures: List<String>,
    val note: String,
    val sampleTemplateText: String
)
