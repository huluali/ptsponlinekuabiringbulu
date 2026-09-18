package com.example.model

enum class StaffRole(
    val title: String,
    val shortTitle: String,
    val badgeColor: Long,
    val description: String
) {
    KEPALA_KUA(
        title = "Kepala KUA Kecamatan",
        shortTitle = "Kepala KUA",
        badgeColor = 0xFF004D40,
        description = "Pimpinan Kantor Urusan Agama & Pejabat Pembuat Akta Ikrar Wakaf (PPAIW)"
    ),
    PENGHULU(
        title = "Penghulu Ahli / Fungsional",
        shortTitle = "Penghulu",
        badgeColor = 0xFF1565C0,
        description = "Pemeriksa berkas kehendak nikah, pembimbing pra-nikah, dan pencatat akad nikah/rujuk"
    ),
    PENYULUH(
        title = "Penyuluh Agama Islam",
        shortTitle = "Penyuluh",
        badgeColor = 0xFF2E7D32,
        description = "Pembina keagamaan, kemasjidan SIMAS, pendamping sertifikasi halal, hisab rukyat & konsultasi syariah"
    ),
    PETUGAS_PTSP(
        title = "Petugas Layanan PTSP & SIMKAH",
        shortTitle = "Petugas PTSP",
        badgeColor = 0xFFE65100,
        description = "Front Office penerimaan 48 layanan PTSP, verifikator berkas digital & operator SIMKAH Gen 4"
    )
}

data class KuaStaff(
    val id: String,
    val name: String,
    val role: StaffRole,
    val nip: String,
    val rankGrade: String,
    val positionTitle: String,
    val employmentStatus: String, // PNS, PPPK, Non-ASN Kemenag
    val education: String,
    val appointmentSk: String,
    val serviceArea: String,
    val mainDuties: List<String>,
    val phone: String,
    val email: String,
    val scheduleTime: String,
    val roomDesk: String,
    val isDutyActive: Boolean = true,
    val statusMessage: String = "Bertugas melayani masyarakat",
    val initials: String
)

object KuaStaffData {
    val initialStaffList: List<KuaStaff> = listOf(
        KuaStaff(
            id = "staff_kepala_1",
            name = "Drs. H. Muhammad Amin, M.H.",
            role = StaffRole.KEPALA_KUA,
            nip = "19750310 200212 1 003",
            rankGrade = "Pembina (IV/a)",
            positionTitle = "Kepala KUA Kec. Biringbulu & PPAIW",
            employmentStatus = "PNS Kemenag",
            education = "S2 Magister Hukum Islam - UIN Alauddin Makassar",
            appointmentSk = "KMA/Gowa/Kp.07.6/142/2023",
            serviceArea = "Wilayah Kerja Seluruh Kecamatan Biringbulu (11 Desa)",
            mainDuties = listOf(
                "Memimpin pelaksanaan tugas operasional Kantor Urusan Agama Kecamatan Biringbulu.",
                "Bertindak sebagai Pejabat Pembuat Akta Ikrar Wakaf (PPAIW) wilayah Kec. Biringbulu.",
                "Menandatangani Buku Nikah, Surat Rekomendasi Nikah Luar Wilayah (N10), dan Duplikat Buku Nikah.",
                "Mengkoordinasikan pelayanan bimbingan kemasjidan, hisab rukyat, zakat, wakaf, dan produk halal.",
                "Pengawasan tata kelola administrasi keuangan PNBP Nikah Rujuk dan pelayanan PTSP digital."
            ),
            phone = "0811-4422-0011",
            email = "m.amin.kua@kemenag.go.id",
            scheduleTime = "Senin - Jumat | 07.30 - 16.00 WITA",
            roomDesk = "Ruang Pimpinan Kepala KUA",
            isDutyActive = true,
            statusMessage = "Siaga di Kantor KUA - Melayani konsultasi pimpinan & legalisasi dokumen",
            initials = "MA"
        ),
        KuaStaff(
            id = "staff_penghulu_1",
            name = "H. Ahmad Fauzi, S.HI., M.Pd.",
            role = StaffRole.PENGHULU,
            nip = "19840615 200912 1 004",
            rankGrade = "Penata Tingkat I (III/d)",
            positionTitle = "Penghulu Ahli Muda",
            employmentStatus = "PNS Kemenag",
            education = "S2 Pendidikan Agama Islam - Pascasarjana UIN Alauddin",
            appointmentSk = "Kemenag.Gowa/B.II/Penghulu/88/2022",
            serviceArea = "Kecamatan Biringbulu (Kelurahan Tonrorita, Kelurahan Lauwa, Batumalonro, Pencong, Baturappe)",
            mainDuties = listOf(
                "Pemeriksaan keabsahan berkas kehendak nikah (Formulir N1 sampai N4).",
                "Memverifikasi syarat wali nikah, saksi, status catin, dan penetapan dispensasi nikah.",
                "Pelaksanaan akad nikah di Balai Nikah KUA (Gratis) maupun di luar kantor (PNBP SIMPONI).",
                "Instruktur Kursus Calon Pengantin (Suscatin) & Bimbingan Perkawinan (Bimwin).",
                "Pencatatan peristiwa rujuk, penerbitan rekomendasi nikah luar daerah, dan bimbingan keluarga sakinah."
            ),
            phone = "0812-4567-8901",
            email = "ahmad.fauzi.penghulu@kemenag.go.id",
            scheduleTime = "Senin - Jumat | 08.00 - 16.00 WITA (Jadwal Akad Siaga)",
            roomDesk = "Ruang Balai Nikah & Pemeriksaan Penghulu",
            isDutyActive = true,
            statusMessage = "Siaga Pemeriksaan Berkas Catin & Jadwal Akad Nikah",
            initials = "AF"
        ),
        KuaStaff(
            id = "staff_penyuluh_1",
            name = "Ustadz H. Syamsuddin, S.Ag., M.Sos.",
            role = StaffRole.PENYULUH,
            nip = "19820504 201411 1 002",
            rankGrade = "Penata (III/c)",
            positionTitle = "Penyuluh Agama Islam Ahli Pertama",
            employmentStatus = "PNS Kemenag",
            education = "S2 Komunikasi & Penyiaran Islam - UIN Alauddin",
            appointmentSk = "Kemenag.Gowa/Penyuluh/KUA-BB/05/2021",
            serviceArea = "Kelurahan Lauwa, Lembangloe, Berutallasa, Borimasunggu, Taring, Nyeremang",
            mainDuties = listOf(
                "Bimbingan dan penyuluhan keagamaan masyarakat, majelis taklim, dan remaja masjid.",
                "Verifikasi data masjid/musala untuk penerbitan Nomor ID SIMAS Nasional Kemenag RI.",
                "Pendamping Proses Produk Halal (P3H) bagi pelaku usaha mikro makanan/minuman (Self-Declare).",
                "Pengukuran dan kalibrasi arah kiblat masjid/musala berbasis theodolite dan hisab falak.",
                "Konsultasi hukum waris syariah (faraidh), zakat fitrah & mal, serta pembinaan mualaf desa."
            ),
            phone = "0813-5544-3322",
            email = "syamsuddin.penyuluh@kemenag.go.id",
            scheduleTime = "Senin - Jumat | 08.00 - 15.30 WITA (Jadwal Binaan Lapangan)",
            roomDesk = "Ruang Posko Bimbingan Penyuluh Agama",
            isDutyActive = true,
            statusMessage = "Siaga Konsultasi Syariah, Kalibrasi Kiblat & Pendampingan Sertifikat Halal",
            initials = "HS"
        ),
        KuaStaff(
            id = "staff_ptsp_1",
            name = "Andi Muhammad Risal, S.Kom.",
            role = StaffRole.PETUGAS_PTSP,
            nip = "19920814 202012 1 007",
            rankGrade = "Penata Muda (III/a)",
            positionTitle = "Pengelola Layanan Operasional & Admin SIMKAH",
            employmentStatus = "PNS Kemenag",
            education = "S1 Sistem Informasi Komputer - Universitas Hasanuddin",
            appointmentSk = "Kemenag.Gowa/PTSP/KUA-BB/12/2021",
            serviceArea = "Loket PTSP Digital KUA Kecamatan Biringbulu",
            mainDuties = listOf(
                "Penerimaan dan registrasi 48 jenis permohonan layanan PTSP digital dan tatap muka.",
                "Operator Aplikasi SIMKAH (Sistem Informasi Manajemen Nikah) Generasi 4 Kemenag RI.",
                "Sinkronisasi data NIK pemohon dengan database Dukcapil Kemendagri.",
                "Penerbitan billing kode pembayaran PNBP Nikah SIMPONI Kemenkeu.",
                "Pencetakan Kartu Nikah Digital, Buku Nikah, dan Surat Rekomendasi/Keterangan PTSP."
            ),
            phone = "0852-4211-9988",
            email = "risal.ptsp.kua@kemenag.go.id",
            scheduleTime = "Senin - Jumat | 07.30 - 16.00 WITA",
            roomDesk = "Loket 1 & 2 Front Office PTSP Digital",
            isDutyActive = true,
            statusMessage = "Melayani Pemohon di Loket Front Office PTSP Digital",
            initials = "AR"
        )
    )
}
