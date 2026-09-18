package com.example.data

data class VillageItem(
    val id: Int,
    val name: String,
    val isKelurahan: Boolean = false,
    val imamDesa: String,
    val penyuluhPendamping: String,
    val totalMosques: Int,
    val totalWakafLand: Int
)

data class KuaOfficial(
    val name: String,
    val title: String,
    val nip: String,
    val roleDescription: String
)

object BiringbuluData {
    const val OFFICE_NAME = "Kantor Urusan Agama (KUA) Kecamatan Biringbulu"
    const val MINISTRY_NAME = "Kementerian Agama Kabupaten Gowa - Provinsi Sulawesi Selatan"
    const val ADDRESS = "Jl. Poros Malakaji - Biringbulu, Kec. Biringbulu, Kabupaten Gowa, Sulawesi Selatan 92174"
    const val PHONE_PTSP = "0812-4119-3634"
    const val WHATSAPP_PTSP = "+6281241193634"
    const val EMAIL = "kua.biringbulu@kemenag.go.id"
    const val WORK_HOURS = "Senin - Kamis: 07.30 - 16.00 WITA | Jumat: 07.30 - 16.30 WITA"
    const val COORDINATE_LAT = -5.4851
    const val COORDINATE_LNG = 119.8242
    const val QIBLA_AZIMUTH = 292.65 // Azimuth Kiblat dari Biringbulu Gowa (Utara ke Barat)

    val officials = listOf(
        KuaOfficial(
            name = "H. Muh. Ridwan, S.Ag., M.H.I.",
            title = "Kepala KUA & PPAIW",
            nip = "197805122005011004",
            roleDescription = "Penanggung Jawab Umum, PPAIW, dan Penghulu Madya"
        ),
        KuaOfficial(
            name = "Ust. Mansyur, S.H.I.",
            title = "Penghulu Muda",
            nip = "198409152011011009",
            roleDescription = "Pemeriksa Nikah, Konselor Keluarga & Petugas Akad"
        ),
        KuaOfficial(
            name = "Drs. Ahmad Syarifuddin",
            title = "Penyuluh Agama Islam Ahli Madya",
            nip = "197203101999031002",
            roleDescription = "Koordinator Zakat, Wakaf, Kemasjidan & Moderasi Beragama"
        ),
        KuaOfficial(
            name = "Nurhalimah, S.Sos.I.",
            title = "Penyuluh Agama Islam Ahli Pertama",
            nip = "199104182019032015",
            roleDescription = "Fasilitator Bimwin Catin & Pendamping Produk Halal (P3H)"
        ),
        KuaOfficial(
            name = "Syarifuddin Dg. Rate",
            title = "Pengelola Administrasi PTSP & SIMKAH",
            nip = "198811232014021003",
            roleDescription = "Verifikator Berkas Online, SIMAS, dan Persuratan"
        )
    )

    val villages = listOf(
        VillageItem(1, "Kelurahan Lauwa", true, "H. Syamsuddin Dg. Ngawing", "Ust. Ahmad Syarifuddin", 14, 18),
        VillageItem(2, "Desa Batumalonro", false, "Ust. Baharuddin Dg. Rapi", "Nurhalimah, S.Sos.I.", 12, 15),
        VillageItem(3, "Desa Baturappe", false, "H. Daeng Nappa", "Ust. Mansyur, S.H.I.", 11, 14),
        VillageItem(4, "Desa Berutallasa", false, "Ust. Jamaluddin", "Ust. Ahmad Syarifuddin", 9, 11),
        VillageItem(5, "Desa Borimasunggu", false, "Ust. Abdul Kadir Dg. Kulle", "Nurhalimah, S.Sos.I.", 10, 13),
        VillageItem(6, "Desa Julukanaya", false, "Ust. Muhammad Idris", "Ust. Mansyur, S.H.I.", 8, 9),
        VillageItem(7, "Desa Lembangloe", false, "Ust. H. Syarifuddin", "Ust. Ahmad Syarifuddin", 13, 16),
        VillageItem(8, "Desa Pencong", false, "Ust. Dg. Tarang", "Nurhalimah, S.Sos.I.", 15, 21),
        VillageItem(9, "Desa Taring", false, "Ust. Amirullah Dg. Talli", "Ust. Mansyur, S.H.I.", 12, 14),
        VillageItem(10, "Kelurahan Tonrorita", true, "H. Muh. Yahya Dg. Tompo", "Ust. Ahmad Syarifuddin", 16, 24),
        VillageItem(11, "Desa Parangloe", false, "Ust. M. Tahir Dg. Ronrong", "Nurhalimah, S.Sos.I.", 7, 8)
    )

    val serviceCharter = listOf(
        "Melayani masyarakat dengan sepenuh hati, ikhlas, ramah, dan bebas dari pungutan liar (Pungli).",
        "Pernikahan di Balai Nikah KUA pada hari dan jam kerja dijamin Rp 0,- (GRATIS).",
        "Pernikahan di luar KUA/hari libur disetor langsung Rp 600.000,- ke Kas Negara via SIMKAH / Kode Billing MPN G3.",
        "Memberikan kepastian waktu dan transparansi persyaratan pada seluruh 48 layanan KUA.",
        "Menjaga kerahasiaan data privasi dan memberikan solusi bimbingan syariah yang menyejukkan."
    )
}
