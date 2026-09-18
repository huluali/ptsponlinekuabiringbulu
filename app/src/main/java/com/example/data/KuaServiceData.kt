package com.example.data

import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem

object KuaServiceData {

    val allServices: List<KuaServiceItem> = listOf(
        // ==========================================
        // 1. PELAYANAN PERNIKAHAN (20 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 1,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 1,
            title = "Pendaftaran Kehendak Nikah",
            subtitle = "Pendaftaran awal rencana akad nikah secara online SIMKAH atau langsung di Balai Nikah KUA",
            legalBasis = "PMA No. 20 Tahun 2019 tentang Pencatatan Pernikahan",
            processingTime = "Minimal 10 hari kerja sebelum akad",
            cost = "Gratis di Balai Nikah (Jam Kerja) / Rp 600.000 (Luar Balai Nikah/Hari Libur via Kas Negara)",
            outputDocument = "Tanda Terima Pendaftaran & Bukti Billing Simkah",
            targetAudience = "Calon Pengantin Pria & Wanita",
            requirements = listOf(
                "Surat Pengantar Nikah dari Desa/Kelurahan (Model N1)",
                "Surat Persetujuan Calon Mempelai (Model N4)",
                "Surat Izin Orang Tua bagi yang belum berusia 21 tahun (Model N5)",
                "Fotokopi KTP, KK, dan Akta Kelahiran/Ijazah Calon Pengantin",
                "Fotokopi KTP Orang Tua / Wali Nikah dan 2 orang Saksi Nikah",
                "Surat Rekomendasi Nikah dari KUA asal (jika akad di luar kecamatan domisili)",
                "Pasfoto berwarna latar biru ukuran 2x3 (4 lembar) dan 4x6 (2 lembar) berpeci/berkerudung",
                "Surat Keterangan Sehat & Bebas Anemia dari Puskesmas / Elsimil"
            ),
            procedures = listOf(
                "Pemohon mengurus surat pengantar N1-N4 di kantor Desa/Kelurahan setempat di Biringbulu",
                "Mendaftarkan berkas ke PTSP KUA Biringbulu atau via aplikasi SIMKAH Kemenag minimal 10 hari kerja sebelum akad",
                "Petugas PTSP memverifikasi kelengkapan berkas fisik dan data digital",
                "Penetapan jadwal pemeriksaan nikah dan bimbingan pranikah"
            ),
            note = "Jika pendaftaran kurang dari 10 hari kerja, wajib menyertakan Surat Dispensasi dari Camat Biringbulu.",
            sampleTemplateText = "SURAT PERMOHONAN KEHENDAK NIKAH\nKepada Yth. Kepala KUA Kec. Biringbulu\nDengan hormat, kami bermaksud mendaftarkan pernikahan atas nama..."
        ),
        KuaServiceItem(
            id = 2,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 2,
            title = "Pemeriksaan Nikah",
            subtitle = "Verifikasi keabsahan data catin, wali nikah, dan pemeriksaan rukun serta syarat syar'i",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 10 & Kompilasi Hukum Islam (KHI)",
            processingTime = "1 Hari Kerja (Saat pemeriksaan fisik)",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Berita Acara Pemeriksaan Nikah (Model N7)",
            targetAudience = "Calon Pengantin Pria, Wanita, dan Wali Nikah",
            requirements = listOf(
                "Kehadiran Calon Pengantin Pria dan Wanita",
                "Kehadiran Wali Nikah (Nasab atau Hakim)",
                "Membawa KTP asli seluruh pihak yang diperiksa",
                "Dokumen asli N1, N4, N5, dan ijazah/akta lahir untuk pencocokan nama orang tua"
            ),
            procedures = listOf(
                "Penghulu memeriksa kesesuaian identitas fisik dengan dokumen administrasi",
                "Penghulu meneliti status wali nikah, nasab, serta memastikan tidak adanya halangan syar'i",
                "Penandatanganan Berita Acara Pemeriksaan Nikah (N7) oleh Catin, Wali, dan Penghulu"
            ),
            note = "Pastikan wali nasab yang berhak hadir langsung; jika berhalangan, dibuatkan surat taukil wali.",
            sampleTemplateText = "BERITA ACARA PEMERIKSAAN NIKAH (MODEL N7)\nPada hari ini telah dilakukan pemeriksaan kehendak nikah di KUA Kec. Biringbulu..."
        ),
        KuaServiceItem(
            id = 3,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 3,
            title = "Surat Keterangan Ikrar Berwakil Wali",
            subtitle = "Penerbitan surat keterangan pelimpahan hak taukil wali nikah kepada orang lain atau Penghulu KUA",
            legalBasis = "PMA No. 20 Tahun 2019 & Fatwa MUI tentang Taukil Wali",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Ikrar Taukil / Berwakil Wali",
            targetAudience = "Wali Nikah yang berhalangan hadir pada saat akad",
            requirements = listOf(
                "Surat permohonan berwakil wali dari wali nasab sah",
                "Fotokopi KTP dan KK Wali Nasab serta penerima wakil (Taukildan)",
                "Surat Keterangan alasan ketidakhadiran (kesehatan, domisili luar daerah, tugas)",
                "Dua orang saksi ikrar berwakil wali beserta KTP"
            ),
            procedures = listOf(
                "Wali nasab menghadap Kepala KUA/Penghulu didampingi 2 orang saksi",
                "Wali mengikrarkan taukil wali secara lisan dan tertulis",
                "Penghulu menerbitkan surat pengesahan ikrar taukil wali"
            ),
            note = "Pengucapan ikrar wajib dilakukan sebelum waktu pelaksanaan ijab qabul.",
            sampleTemplateText = "SURAT IKRAR TAUWIL WALI\nSaya yang bertanda tangan di bawah ini selaku wali nasab dengan ini mewakilkan pernikahan anak saya..."
        ),
        KuaServiceItem(
            id = 4,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 4,
            title = "Pemberitahuan Kekurangan Syarat / Penolakan",
            subtitle = "Penerbitan surat resmi mengenai berkas nikah yang belum lengkap atau adanya halangan hukum",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 15 & UU No. 1 Tahun 1974",
            processingTime = "1 Hari Kerja setelah verifikasi",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Model N8 (Kekurangan Syarat) / Model N9 (Penolakan Nikah)",
            targetAudience = "Calon Pengantin yang berkasnya tertunda/tertahan",
            requirements = listOf(
                "Tanda bukti pendaftaran kehendak nikah",
                "Berkas pendaftaran yang telah diverifikasi oleh petugas verifikator KUA"
            ),
            procedures = listOf(
                "KUA memeriksa data dan menemukan kekurangan dokumen atau larangan perkawinan",
                "KUA menerbitkan surat pemberitahuan Model N8/N9 dengan mencantumkan alasan rinci",
                "Pemohon dapat melengkapi kekurangan atau mengajukan banding ke Pengadilan Agama jika terjadi penolakan"
            ),
            note = "Catin diberi waktu untuk melengkapi kekurangan dokumen sebelum tanggal rencana akad nikah.",
            sampleTemplateText = "SURAT PEMBERITAHUAN KEKURANGAN SYARAT NIKAH (MODEL N8)\nBerdasarkan hasil pemeriksaan berkas nikah, diberitahukan bahwa..."
        ),
        KuaServiceItem(
            id = 5,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 5,
            title = "Pengumuman Nikah",
            subtitle = "Publikasi pengumuman kehendak nikah pada papan pengumuman resmi KUA atau digital",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 14",
            processingTime = "Ditempel selama 10 hari kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Pengumuman Nikah (Model N10)",
            targetAudience = "Masyarakat Umum & Pihak Terkait",
            requirements = listOf(
                "Berkas pemeriksaan nikah (N7) yang telah dinyatakan lengkap dan memenuhi syarat"
            ),
            procedures = listOf(
                "KUA menyusun daftar pengumuman rencana pernikahan calon mempelai",
                "Pemasangan pengumuman di papan KUA Biringbulu dan portal SIMKAH",
                "Menerima sanggahan masyarakat bila terdapat halangan hukum perkawinan"
            ),
            note = "Memberi kesempatan bagi pihak yang berkepentingan untuk menyampaikan sanggahan yang sah.",
            sampleTemplateText = "PENGUMUMAN KEHENDAK NIKAH (MODEL N10)\nDiberitahukan kepada khalayak ramai bahwa akan dilangsungkan pernikahan antara..."
        ),
        KuaServiceItem(
            id = 6,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 6,
            title = "Pelaksanaan dan Pencatatan Nikah",
            subtitle = "Pengawasan akad, pemanduan ijab kabul, dan pencatatan resmi dalam register Akta Nikah",
            legalBasis = "UU No. 1 Tahun 1974 jo UU No. 16 Tahun 2019 & PMA No. 20/2019",
            processingTime = "Hari H Pelaksanaan Akad",
            cost = "Gratis di Balai Nikah (Jam Kerja) / Rp 600.000,- (Luar Kantor/Hari Libur via Kas Negara)",
            outputDocument = "Akta Nikah (Model N) dan Register Pencatatan Nikah",
            targetAudience = "Kedua Mempelai, Wali, dan Saksi Nikah",
            requirements = listOf(
                "Kehadiran Catin Pria dan Wanita yang telah lolos uji berkas",
                "Kehadiran Wali Nikah yang sah",
                "Kehadiran minimal 2 (dua) orang saksi nikah laki-laki muslim dewasa",
                "Kuitansi/Bukti setor PNBP Rp 600.000 jika akad di luar kantor KUA Biringbulu"
            ),
            procedures = listOf(
                "Penghulu memverifikasi ulang kehadiran mempelai, wali, saksi, dan mahar (mas kawin)",
                "Khotbah nikah, istighfar, syahadat, dan pemanduan akad ijab qabul",
                "Penandatanganan Buku Akta Nikah oleh Suami, Istri, Wali, Saksi, dan Penghulu",
                "Penyerahan Buku Nikah dan pembacaan sighat taklik talak (opsional)"
            ),
            note = "Penghulu dilarang menerima tip, gratifikasi, atau uang transport dalam bentuk apapun.",
            sampleTemplateText = "REGISTER AKTA NIKAH KUA BIRINGBULU\nNomor Akta: ... Telah dilangsungkan akad nikah yang sah antara..."
        ),
        KuaServiceItem(
            id = 7,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 7,
            title = "Penyerahan Buku Nikah",
            subtitle = "Penyerahan fisik sepasang Buku Nikah resmi (Suami & Istri) beserta Kartu Nikah Digital",
            legalBasis = "PMA No. 20 Tahun 2019 & Kepdirjen Bimas Islam",
            processingTime = "Seketika setelah akad atau maksimal 1 hari kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Buku Nikah Asli (Hijau & Cokelat) + Akses Kartu Nikah Digital QR Code",
            targetAudience = "Pasangan Suami Istri Baru",
            requirements = listOf(
                "Telah menyelesaikan seluruh prosesi akad nikah dan penandatanganan akta nikah",
                "Pengisian tanda terima penyerahan dokumen"
            ),
            procedures = listOf(
                "Penghulu menyerahkan langsung Buku Nikah kepada mempelai pria dan wanita",
                "Mempelai menerima link unduhan Kartu Nikah Digital SIMKAH dengan QR Code terverifikasi",
                "Pengambilan dokumentasi resmi penyerahan dokumen di Balai Nikah"
            ),
            note = "Periksa kembali ejaan nama, NIK, tanggal lahir, dan tanggal akad sebelum meninggalkan lokasi.",
            sampleTemplateText = "BERITA ACARA PENYERAHAN BUKU NIKAH\nTelah diserahkan sepasang Buku Nikah Nomor Seri: ... kepada..."
        ),
        KuaServiceItem(
            id = 8,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 8,
            title = "Pelaporan Nikah",
            subtitle = "Pelaporan periodik data peristiwa nikah dan rujuk ke Kemenag Kabupaten Gowa dan Dukcapil",
            legalBasis = "PMA No. 20 Tahun 2019 & PKS Kemenag-Dukcapil",
            processingTime = "Bulanan / Berkala",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Laporan Bulanan NR (Nikah Rujuk) & Update Status Kependudukan",
            targetAudience = "Kemenag Kab. Gowa, Disdukcapil, Instansi Pembina",
            requirements = listOf(
                "Register Akta Nikah dan data transaksi SIMKAH KUA Biringbulu",
                "Rekapitulasi setoran PNBP Nikah ke Kas Negara"
            ),
            procedures = listOf(
                "Operator KUA mengompilasi seluruh pencatatan nikah bulanan",
                "Sinkronisasi data online ke Sistem Informasi Administrasi Kependudukan (SIAK) Dukcapil",
                "Penerbitan laporan resmi Kepala KUA Biringbulu ke Kemenag Gowa"
            ),
            note = "Memastikan status perkawinan pasutri di KK dan KTP langsung terintegrasi menjadi 'Kawin Tercatat'.",
            sampleTemplateText = "LAPORAN BULANAN PERISTIWA NIKAH DAN RUJUK\nKUA Kecamatan Biringbulu Kabupaten Gowa..."
        ),
        KuaServiceItem(
            id = 9,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 9,
            title = "Surat Rekomendasi Nikah (Numpang Nikah)",
            subtitle = "Penerbitan surat rekomendasi bagi warga Biringbulu yang akan melangsungkan akad di luar kecamatan",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 9",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Nikah Resmi KUA Biringbulu",
            targetAudience = "Warga domisili Biringbulu yang menikah di luar wilayah",
            requirements = listOf(
                "Surat Pengantar Nikah (N1-N4) dari Desa setempat di Biringbulu",
                "Fotokopi KTP dan Kartu Keluarga Pemohon",
                "Fotokopi Akta Kelahiran atau Ijazah terakhir",
                "Pasfoto berwarna 2x3 dan 3x4 latar biru (masing-masing 2 lembar)",
                "Surat Keterangan KUA Tujuan (Nama KUA dan Kabupaten/Kota lokasi akad)"
            ),
            procedures = listOf(
                "Pemohon mengajukan berkas rekomendasi ke PTSP KUA Biringbulu",
                "Petugas memeriksa data kependudukan dan status belum pernah tercatat nikah di Biringbulu",
                "Penerbitan dan legalisasi Surat Rekomendasi Nikah oleh Kepala KUA"
            ),
            note = "Surat rekomendasi berlaku selama 3 bulan sejak tanggal diterbitkan.",
            sampleTemplateText = "SURAT REKOMENDASI NIKAH\nNomor: B-.../Kua.21.06/PW.01/.../2026\nKepala KUA Biringbulu menerangkan bahwa..."
        ),
        KuaServiceItem(
            id = 10,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 10,
            title = "Perbaikan Data Nikah (Salah Tulis)",
            subtitle = "Perbaikan kesalahan penulisan nama, tanggal lahir, atau NIK pada Buku Nikah sesuai dokumen kependudukan",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 37",
            processingTime = "1 - 2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Catatan Perubahan pada Buku Nikah Asli / Buku Nikah Baru",
            targetAudience = "Pemegang Buku Nikah yang memiliki kesalahan ketik redaksional",
            requirements = listOf(
                "Buku Nikah Asli Suami dan Istri",
                "Fotokopi KTP, KK, dan Akta Kelahiran/Ijazah yang memuat data yang benar",
                "Surat Keterangan Beda Nama / Data dari Kepala Desa di Biringbulu (bila diperlukan)",
                "Surat Permohonan Perbaikan Data ditujukan kepada Kepala KUA"
            ),
            procedures = listOf(
                "Pemohon mengajukan berkas dan buku nikah asli ke KUA Biringbulu",
                "Pemeriksaan silang dengan Buku Register Akta Nikah fisik di arsip KUA",
                "Pemberian catatan resmi perbaikan pada halaman perubahan atau cetak ulang buku nikah"
            ),
            note = "Perbaikan hanya untuk kesalahan tulis redaksional minor. Jika perubahan substansial harus melalui penetapan Pengadilan.",
            sampleTemplateText = "SURAT PERMOHONAN PERBAIKAN DATA BUKU NIKAH\nKepada Yth. Kepala KUA Kec. Biringbulu\nSaya bermaksud mengajukan perbaikan penulisan nama..."
        ),
        KuaServiceItem(
            id = 11,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 11,
            title = "Perubahan Data Nikah (Penetapan Pengadilan)",
            subtitle = "Pencatatan perubahan data identitas perkawinan berdasarkan Putusan/Penetapan Pengadilan Agama/Negeri",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 38 & UU Adminduk",
            processingTime = "2 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Catatan Pinggir Akta Nikah & Buku Nikah Terkoreksi",
            targetAudience = "Warga yang memiliki Salinan Penetapan Pengadilan tentang data nikah",
            requirements = listOf(
                "Salinan Putusan / Penetapan Pengadilan yang telah berkekuatan hukum tetap (inkracht)",
                "Buku Nikah Asli Suami dan Istri",
                "Fotokopi KTP dan KK terbaru",
                "Surat Permohonan Pencatatan Perubahan Data"
            ),
            procedures = listOf(
                "Penyerahan salinan putusan pengadilan asli dan buku nikah ke KUA",
                "Petugas mencocokkan isi amar putusan dengan nomor registrasi akta nikah",
                "Kepala KUA membuat Catatan Pinggir pada Register Akta Nikah dan Buku Nikah"
            ),
            note = "Wajib melampirkan surat keterangan inkracht dari Panitera Pengadilan terkait.",
            sampleTemplateText = "SURAT PERMOHONAN PENCATATAN PERUBAHAN DATA PERKAWINAN\nBerdasarkan Salinan Penetapan Pengadilan Agama..."
        ),
        KuaServiceItem(
            id = 12,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 12,
            title = "Penggantian Buku Nikah (Hilang / Rusak)",
            subtitle = "Penerbitan Duplikat Buku Nikah (Kutipan Akta Nikah Pengganti) karena rusak berat atau hilang",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 39",
            processingTime = "1 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Duplikat Buku Nikah Resmi (Cap Duplikat)",
            targetAudience = "Warga yang kehilangan atau buku nikahnya rusak",
            requirements = listOf(
                "Surat Tanda Lapor Kehilangan dari Kepolisian (Polsek Biringbulu/Polres Gowa) - jika hilang",
                "Buku Nikah yang rusak fisik (jika karena rusak)",
                "Fotokopi KTP dan Kartu Keluarga Suami & Istri",
                "Pasfoto berwarna latar biru 2x3 (2 lembar)",
                "Surat Pernyataan bermeterai Rp 10.000 tentang kebenaran kehilangan/kerusakan"
            ),
            procedures = listOf(
                "Pemohon mengajukan berkas ke PTSP KUA Biringbulu",
                "Petugas melacak nomor seri dan register akta nikah di buku register KUA",
                "Pencetakan dan penerbitan Duplikat Buku Nikah resmi KUA Biringbulu"
            ),
            note = "Duplikat Buku Nikah memiliki kekuatan hukum yang sama persis dengan Buku Nikah Asli.",
            sampleTemplateText = "SURAT PERMOHONAN DUPLIKAT BUKU NIKAH\nKepada Yth. Kepala KUA Kec. Biringbulu\nSehubungan dengan hilangnya Buku Nikah kami pada..."
        ),
        KuaServiceItem(
            id = 13,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 13,
            title = "Legalisasi Buku Nikah",
            subtitle = "Pengesahan salinan fotokopi Buku Nikah / Duplikat untuk keperluan administrasi resmi, haji, paspor, perbankan",
            legalBasis = "PMA No. 20 Tahun 2019 & Kepdirjen Bimas Islam No. DJ.II/1/2013",
            processingTime = "Langsung Selesai (15 - 30 Menit)",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Fotokopi Buku Nikah Berstempel & Tanda Tangan Basah / Digital Legalisir",
            targetAudience = "Masyarakat yang membutuhkan legalisir akta perkawinan",
            requirements = listOf(
                "Membawa Buku Nikah Asli Suami atau Istri",
                "Fotokopi Buku Nikah (halaman identitas & akad) maksimal 5 rangkap",
                "KTP Asli Pemohon / Kuasa"
            ),
            procedures = listOf(
                "Pemohon memperlihatkan Buku Nikah asli kepada petugas PTSP",
                "Petugas memvalidasi keaslian blangko dan kecocokan data dengan arsip digital KUA",
                "Pembubuhan cap legalisir dan tanda tangan pejabat KUA Biringbulu"
            ),
            note = "Hanya Buku Nikah yang diterbitkan oleh KUA bersangkutan atau dengan konfirmasi ke KUA penerbit.",
            sampleTemplateText = "FORMULIR PERMOHONAN LEGALISASI BUKU NIKAH\nNama Pemohon: ... Nomor Akta Nikah: ... Jumlah Rangkap: ..."
        ),
        KuaServiceItem(
            id = 14,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 14,
            title = "Surat Keterangan Status Belum Menikah",
            subtitle = "Penerbitan surat keterangan resmi bahwa yang bersangkutan belum pernah tercatat menikah di KUA Biringbulu",
            legalBasis = "KMA Standar Pelayanan Publik Kemenag",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Keterangan Belum Menikah Resmi KUA",
            targetAudience = "Warga yang membutuhkan untuk syarat beasiswa, dinas, lamaran kerja, kedutaan",
            requirements = listOf(
                "Surat Pengantar Keterangan Belum Pernah Menikah dari Kepala Desa di Biringbulu",
                "Surat Pernyataan Belum Pernah Menikah bermeterai Rp 10.000",
                "Fotokopi KTP dan Kartu Keluarga Pemohon",
                "Pasfoto berwarna 3x4 latar biru (2 lembar)"
            ),
            procedures = listOf(
                "Pemohon mengajukan berkas ke loket PTSP KUA Biringbulu",
                "Petugas memeriksa buku register nikah untuk memastikan ketiadaan riwayat pernikahan",
                "Penandatanganan Surat Keterangan oleh Kepala KUA Biringbulu"
            ),
            note = "Surat ini berlaku untuk keperluan administratif legal dalam dan luar negeri.",
            sampleTemplateText = "SURAT KETERANGAN BELUM MENIKAH\nNomor: B-.../Kua.21.06/PW.01/.../2026\nMenerangkan bahwa nama tersebut di atas belum pernah tercatat menikah..."
        ),
        KuaServiceItem(
            id = 15,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 15,
            title = "Pendaftaran Bukti Nikah Luar Negeri",
            subtitle = "Pelaporan dan pencatatan perkawinan WNI yang telah melangsungkan pernikahan sah di luar negeri",
            legalBasis = "UU No. 23/2006 jo UU No. 24/2013 & PMA No. 20/2019",
            processingTime = "3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Tanda Bukti Pendaftaran Perkawinan Luar Negeri",
            targetAudience = "WNI yang menikah di luar negeri dan berdomisili di Biringbulu",
            requirements = listOf(
                "Marriage Certificate / Sertifikat Nikah dari negara setempat",
                "Surat Keterangan / Laporan Perkawinan dari KBRI / KJRI negara setempat",
                "Terjemahan resmi sertifikat nikah oleh penerjemah tersumpah (sworn translator)",
                "Fotokopi Paspor, KTP, dan KK Suami & Istri",
                "Fotokopi Akta Kelahiran kedua mempelai"
            ),
            procedures = listOf(
                "Pemohon menyerahkan dokumen luar negeri asli beserta legalisasi KBRI ke KUA",
                "KUA memverifikasi keabsahan dokumen syar'i dan diplomatik",
                "Pencatatan dalam register khusus perkawinan luar negeri dan penerbitan tanda bukti"
            ),
            note = "Wajib didaftarkan maksimal 1 tahun setelah kembali ke Indonesia.",
            sampleTemplateText = "SURAT TANDA BUKTI PENDAFTARAN PERKAWINAN LUAR NEGERI\nKUA Kec. Biringbulu telah menerima pelaporan perkawinan..."
        ),
        KuaServiceItem(
            id = 16,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 16,
            title = "Pencatatan Isbat Nikah",
            subtitle = "Pencatatan dan penerbitan Buku Nikah bagi pasangan yang pernikahannya telah disahkan Pengadilan Agama",
            legalBasis = "PMA No. 20 Tahun 2019 Pasal 36 & Sema Mahkamah Agung",
            processingTime = "2 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Buku Nikah Resmi Berdasarkan Penetapan Isbat Nikah",
            targetAudience = "Pasangan yang memiliki Salinan Penetapan Isbat Nikah dari Pengadilan Agama",
            requirements = listOf(
                "Salinan Penetapan Isbat Nikah Asli dari Pengadilan Agama (Pengadilan Agama Sungguminasa)",
                "Surat Keterangan Kematian (jika isbat nikah untuk salah satu pihak yang telah wafat)",
                "Fotokopi KTP dan KK Suami & Istri",
                "Pasfoto berwarna latar biru 2x3 (4 lembar) dan 4x6 (2 lembar)",
                "Surat Pengantar dari Kepala Desa setempat di Biringbulu"
            ),
            procedures = listOf(
                "Pemohon menyerahkan salinan penetapan isbat nikah ke loket KUA",
                "KUA memeriksa nomor register penetapan dan amar putusan",
                "Penerbitan Buku Nikah resmi dengan mencantumkan tanggal akad sesuai amar isbat"
            ),
            note = "Sangat penting bagi warga Biringbulu yang nikah siri di masa lalu untuk memperoleh legalitas formal.",
            sampleTemplateText = "PERMOHONAN PENCATATAN ISBAT NIKAH\nBerdasarkan Salinan Penetapan Pengadilan Agama Sungguminasa Nomor: ..."
        ),
        KuaServiceItem(
            id = 17,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 17,
            title = "Pencatatan Perjanjian Nikah (Pre-nuptial Agreement)",
            subtitle = "Pencatatan akta notaris perjanjian perkawinan (pemisahan harta, dll.) sebelum atau saat pernikahan",
            legalBasis = "UU No. 1/1974 jo Putusan MK No. 69/PUU-XIII/2015 & PMA No. 20/2019",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Catatan Perjanjian Perkawinan pada Akta & Buku Nikah",
            targetAudience = "Calon Pengantin atau Pasangan Suami Istri",
            requirements = listOf(
                "Akta Notaris Asli Perjanjian Perkawinan yang dibuat oleh Notaris berwenang",
                "Fotokopi KTP dan KK kedua belah pihak",
                "Buku Nikah Asli (jika dibuat setelah perkawinan berlangsung)"
            ),
            procedures = listOf(
                "Pemohon menyerahkan akta notaris kepada Pegawai Pencatat Nikah (PPN)",
                "Petugas mencatat nomor dan tanggal akta notaris pada Akta Nikah dan Buku Nikah",
                "Pemberian tanda pengesahan pencatatan perjanjian"
            ),
            note = "Dapat dilakukan sebelum akad nikah atau selama dalam ikatan perkawinan sesuai putusan Mahkamah Konstitusi.",
            sampleTemplateText = "SURAT PERMOHONAN PENCATATAN PERJANJIAN PERKAWINAN\nMelampirkan Akta Notaris Nomor: ... Tanggal: ..."
        ),
        KuaServiceItem(
            id = 18,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 18,
            title = "Pencatatan Pembatalan / Perubahan Perjanjian Nikah",
            subtitle = "Pencatatan resmi perubahan atau pembatalan isi perjanjian perkawinan berdasarkan akta notaris baru",
            legalBasis = "PMA No. 20 Tahun 2019 & KUHPerdata / KHI",
            processingTime = "1 - 2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Catatan Pinggir Perubahan Perjanjian pada Register Akta Nikah",
            targetAudience = "Suami Istri yang mengubah kesepakatan perjanjian perkawinan",
            requirements = listOf(
                "Akta Notaris Perubahan / Pembatalan Perjanjian Perkawinan",
                "Buku Nikah Asli Suami dan Istri",
                "Fotokopi KTP dan KK kedua pihak"
            ),
            procedures = listOf(
                "Penyerahan berkas ke KUA Biringbulu",
                "PPN memeriksa kesesuaian akta notaris perubahan dengan pencatatan terdahulu",
                "Pembubuhan catatan pinggir pada Akta Nikah dan Buku Nikah"
            ),
            note = "Perubahan tidak boleh merugikan pihak ketiga yang telah terikat perjanjian sebelumnya.",
            sampleTemplateText = "PERMOHONAN PENCATATAN PERUBAHAN PERJANJIAN NIKAH\nDengan ini melaporkan perubahan klausul perjanjian perkawinan..."
        ),
        KuaServiceItem(
            id = 19,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 19,
            title = "Pencatatan Rujuk",
            subtitle = "Pencatatan kembalinya ikatan perkawinan suami-istri yang masih dalam masa iddah talak raj'i",
            legalBasis = "UU No. 1/1974, Kompilasi Hukum Islam Pasal 163-169 & PMA No. 20/2019",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Buku Pendaftaran Rujuk & Kutipan Akta Rujuk (Model R)",
            targetAudience = "Pasangan Suami Istri yang hendak rujuk dalam masa iddah",
            requirements = listOf(
                "Akta Cerai Asli dari Pengadilan Agama (talak satu atau talak dua raj'i)",
                "Surat Keterangan masih dalam masa iddah dari Desa/Kelurahan",
                "Persetujuan tertulis dari pihak Istri untuk kembali rujuk",
                "Kehadiran Suami, Istri, dan 2 (dua) orang saksi muslim",
                "Fotokopi KTP dan KK masing-masing pihak"
            ),
            procedures = listOf(
                "Suami dan Istri bersama saksi menghadap Penghulu di KUA Biringbulu",
                "Penghulu memeriksa masa iddah dan kesukarelaan kedua belah pihak",
                "Pengucapan ikrar rujuk di hadapan Penghulu dan saksi",
                "Penandatanganan Akta Rujuk dan penyerahan Kutipan Akta Rujuk"
            ),
            note = "Jika masa iddah telah lewat, pasangan tidak dapat rujuk melainkan harus melakukan akad nikah baru.",
            sampleTemplateText = "SURAT PERMOHONAN PENCATATAN RUJUK\nKami suami istri bermaksud mengajukan pencatatan rujuk dalam masa iddah..."
        ),
        KuaServiceItem(
            id = 20,
            category = KuaServiceCategory.PELAYANAN_PERNIKAHAN,
            numberInCategory = 20,
            title = "Pengajuan Pembatalan Nikah",
            subtitle = "Penerimaan laporan dan pengadministrasian putusan pembatalan perkawinan dari Pengadilan Agama",
            legalBasis = "UU No. 1/1974 Pasal 22-28 & Kompilasi Hukum Islam Pasal 70-76",
            processingTime = "1 - 2 Hari Kerja setelah putusan inkracht",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Keterangan Pencabutan / Pembatalan Akta Nikah dalam Register",
            targetAudience = "Pihak yang pernikahannya dibatalkan oleh Pengadilan Agama",
            requirements = listOf(
                "Salinan Putusan Pembatalan Perkawinan yang berkekuatan hukum tetap dari Pengadilan Agama",
                "Buku Nikah Asli Suami dan Istri yang akan ditarik oleh KUA",
                "Fotokopi KTP dan KK Pemohon"
            ),
            procedures = listOf(
                "Penyerahan salinan putusan pembatalan nikah ke KUA Biringbulu",
                "KUA mencoret nomor register akta nikah bersangkutan dengan garis merah dan catatan pembatalan",
                "Penarikan fisik Buku Nikah dari peredaran untuk dimusnahkan/diarsipkan"
            ),
            note = "Pembatalan nikah berakibat perkawinan dianggap tidak pernah ada sejak awal sesuai hukum Islam.",
            sampleTemplateText = "LAPORAN PUTUSAN PEMBATALAN PERKAWINAN\nNomor Putusan Pengadilan Agama: ... Menyerahkan Buku Nikah untuk dibatalkan..."
        ),

        // ==========================================
        // 2. BIMBINGAN PERKAWINAN (3 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 21,
            category = KuaServiceCategory.BIMBINGAN_PERKAWINAN,
            numberInCategory = 1,
            title = "Bimbingan Perkawinan Pra-Nikah (Bimwin Catin)",
            subtitle = "Kursus pembekalan calon pengantin menuju keluarga sakinah, mawaddah, warahmah & cegah stunting",
            legalBasis = "Kepdirjen Bimas Islam No. 172 Tahun 2022 tentang Petunjuk Teknis Bimbingan Perkawinan Catin",
            processingTime = "2 Hari Pelatihan (Tatap Muka / Terjadwal)",
            cost = "Rp 0,- (Gratis Dibiayai APBN)",
            outputDocument = "Sertifikat Bimbingan Perkawinan (Sertifikat Bimwin)",
            targetAudience = "Seluruh Calon Pengantin yang telah terdaftar di KUA Biringbulu",
            requirements = listOf(
                "Telah terdaftar sebagai calon pengantin di KUA Biringbulu",
                "Pasfoto berwarna 3x4 latar biru (1 lembar)",
                "Komitmen menghadiri seluruh sesi materi (Keluarga Sakinah, Psikologi, Kesehatan Reproduksi, Keuangan)"
            ),
            procedures = listOf(
                "Catin menerima undangan jadwal kelas Bimwin dari KUA Biringbulu",
                "Mengikuti pelatihan interaktif bersama Fasilitator Kemenag, Puskesmas, dan PLKB",
                "Evaluasi pemahaman dan penyerahan Sertifikat Bimwin resmi"
            ),
            note = "Sertifikat Bimwin menjadi dokumen pelengkap wajib dalam indeks ketahanan keluarga nasional.",
            sampleTemplateText = "FORMULIR PENDAFTARAN BIMBINGAN PERKAWINAN CATIN\nNama Catin Pria: ... Nama Catin Wanita: ... Gelombang: ..."
        ),
        KuaServiceItem(
            id = 22,
            category = KuaServiceCategory.BIMBINGAN_PERKAWINAN,
            numberInCategory = 2,
            title = "Bimbingan Keluarga Sakinah",
            subtitle = "Program penguatan ketahanan keluarga, edukasi parenting islami, dan pembinaan pasca nikah",
            legalBasis = "Kepdirjen Bimas Islam No. 491 Tahun 2020 tentang Fasilitasi Gerakan Keluarga Sakinah",
            processingTime = "Sesuai Jadwal Program / Berkala",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Materi Edukasi & Rekomendasi Ketahanan Keluarga",
            targetAudience = "Pasangan Suami Istri usia perkawinan muda (1-5 tahun) di Biringbulu",
            requirements = listOf(
                "Fotokopi Buku Nikah atau Kartu Nikah Digital",
                "Fotokopi KTP dan KK domisili Kecamatan Biringbulu",
                "Mendaftarkan diri secara individu atau melalui kelompok Majelis Taklim"
            ),
            procedures = listOf(
                "Pendaftaran peserta bimbingan keluarga di KUA atau kelompok binaan desa",
                "Pelaksanaan workshop tematik: Resolusi Konflik Rumah Tangga & Finansial Islami",
                "Sesi tanya jawab privat dengan Konselor Keluarga KUA"
            ),
            note = "Menyediakan layanan konsultasi kelanjutan bagi keluarga yang membutuhkan pendampingan khusus.",
            sampleTemplateText = "FORMULIR PENDAFTARAN KELUARGA SAKINAH\nKepala Keluarga: ... Alamat Desa di Biringbulu: ..."
        ),
        KuaServiceItem(
            id = 23,
            category = KuaServiceCategory.BIMBINGAN_PERKAWINAN,
            numberInCategory = 3,
            title = "Bimbingan Konseling dan Mediasi Keluarga (Masa Nikah)",
            subtitle = "Layanan konseling privat, mediasi sengketa rumah tangga, dan upaya rekonsiliasi pencegahan perceraian (BP4)",
            legalBasis = "KMA No. 379 Tahun 1993 tentang BP4 & Kompilasi Hukum Islam Pasal 143",
            processingTime = "1 - 3 Sesi Konseling",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Berita Acara Hasil Konseling / Mediasi Rumah Tangga",
            targetAudience = "Suami Istri yang sedang mengalami perselisihan rumah tangga",
            requirements = listOf(
                "Fotokopi Buku Nikah / Kartu Keluarga",
                "Fotokopi KTP Suami dan Istri",
                "Surat Permohonan Konseling / Mediasi dari salah satu atau kedua belah pihak"
            ),
            procedures = listOf(
                "Penerimaan pengaduan dan penjadwalan sesi mediasi di Ruang Konseling KUA Biringbulu",
                "Penghulu/Konselor mendengarkan penjelasan dari masing-masing pihak secara terpisah & bersama",
                "Pemberian nasihat syar'i, pencarian solusi, dan pembuatan komitmen kesepakatan damai"
            ),
            note = "Menjaga kerahasiaan penuh privasi keluarga demi keutuhan rumah tangga.",
            sampleTemplateText = "SURAT PERMOHONAN MEDIASI RUMAH TANGGA\nKepada Yth. Ketua BP4 / Kepala KUA Biringbulu\nMemohon fasilitasi mediasi sengketa keluarga kami..."
        ),

        // ==========================================
        // 3. ZAKAT DAN WAKAF (7 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 24,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 1,
            title = "Bimbingan Zakat dan Wakaf",
            subtitle = "Konsultasi perhitungan nisab zakat mal/profesi/pertanian dan tata kelola tanah wakaf produktif",
            legalBasis = "UU No. 23 Tahun 2011 tentang Zakat & UU No. 41 Tahun 2004 tentang Wakaf",
            processingTime = "Langsung / 1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Lembar Simulasi Zakat / Panduan Pengelolaan Wakaf",
            targetAudience = "Muzakki, Mustahiq, Wakif, Nazhir di Kecamatan Biringbulu",
            requirements = listOf(
                "Data rincian harta/hasil pertanian untuk perhitungan zakat",
                "Data objek tanah/aset yang direncanakan untuk diwakafkan"
            ),
            procedures = listOf(
                "Konsultasi tatap muka atau online dengan Pengelola Zawa KUA Biringbulu",
                "Penghitungan nisab, haul, dan kadar zakat sesuai syariat fiqih",
                "Rekomendasi penyaluran ke BAZNAS Kab. Gowa atau UPZ resmi"
            ),
            note = "Termasuk perhitungan khusus zakat hasil bumi (jagung & padi) yang menjadi komoditas utama Biringbulu.",
            sampleTemplateText = "LEMBAR KONSULTASI ZAKAT & WAKAF\nNama Konsultan: ... Topik: Perhitungan Zakat Hasil Pertanian / Tanah Wakaf..."
        ),
        KuaServiceItem(
            id = 25,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 2,
            title = "Pembuatan AIW atau APAIW",
            subtitle = "Penerbitan Akta Ikrar Wakaf (AIW) atau Akta Pengganti AIW (APAIW) oleh Kepala KUA selaku PPAIW",
            legalBasis = "UU No. 41 Tahun 2004 Pasal 32-39 & PMA No. 73 Tahun 2013",
            processingTime = "3 - 7 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Blangko Resmi Akta Ikrar Wakaf (AIW) / APAIW",
            targetAudience = "Wakif, Nazhir, dan Ahli Waris",
            requirements = listOf(
                "Surat Permohonan Pendaftaran Ikrar Wakaf",
                "Sertifikat Hak Milik (SHM) / Surat Girik / Rincik / SPPT PBB Tanah Asli",
                "Surat Keterangan Riwayat Tanah dan Tidak Sengketa dari Kepala Desa",
                "Fotokopi KTP Wakif, Nazhir (Ketua, Sekretaris, Bendahara, Anggota), dan 2 Saksi",
                "Surat Pengesahan Susunan Nazhir dari KUA Biringbulu",
                "Surat Persetujuan Ahli Waris Wakif (jika wakif telah wafat untuk APAIW)"
            ),
            procedures = listOf(
                "PPAIW meneliti keabsahan bukti kepemilikan tanah dan status nazhir",
                "Pelaksanaan pengucapan ikrar wakaf di hadapan Kepala KUA selaku PPAIW dan 2 saksi",
                "Penandatanganan Buku Akta Ikrar Wakaf (AIW/APAIW)",
                "Penyerahan salinan AIW kepada Wakif, Nazhir, BWI, dan BPN"
            ),
            note = "AIW adalah dasar hukum utama untuk pengurusan Sertifikat Tanah Wakaf gratis di Kantor Pertanahan (BPN).",
            sampleTemplateText = "AKTA IKRAR WAKAF (AIW)\nNomor: W.2/.../2026\nPada hari ini telah menghadap kepada saya Kepala KUA selaku PPAIW Biringbulu..."
        ),
        KuaServiceItem(
            id = 26,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 3,
            title = "Pendaftaran Tanah Wakaf",
            subtitle = "Pencatatan tanah wakaf ke dalam Sistem Informasi Wakaf (SIWAK) Kemenag dan pendampingan sertifikasi BPN",
            legalBasis = "PMA No. 73 Tahun 2013 & MoU Kemenag - ATR/BPN",
            processingTime = "3 - 5 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Nomor Register SIWAK & Surat Pengantar Sertifikasi Wakaf BPN",
            targetAudience = "Nazhir Tanah Wakaf Masjid, Musala, Makam, Madrasah",
            requirements = listOf(
                "Salinan Akta Ikrar Wakaf (AIW) atau APAIW yang telah diterbitkan PPAIW",
                "Surat Ukur / Sketsa Lokasi Tanah dari Desa",
                "Fotokopi KTP dan SK Pengesahan Nazhir"
            ),
            procedures = listOf(
                "Nazhir mendaftarkan berkas AIW ke operator SIWAK KUA Biringbulu",
                "Input data spasial, luas, peruntukan, dan batas tanah ke database Kemenag RI",
                "Penerbitan surat rekomendasi pendaftaran hak atas tanah wakaf ke BPN Gowa"
            ),
            note = "Mendukung program nasional percepatan sertifikasi tanah wakaf rumah ibadah tanpa biaya.",
            sampleTemplateText = "PERMOHONAN REGISTRASI TANAH WAKAF (SIWAK)\nNama Objek Wakaf: ... Peruntukan: Masjid / Makam / Pendidikan..."
        ),
        KuaServiceItem(
            id = 27,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 4,
            title = "Mutasi Harta Benda Wakaf (Tukar Guling / Ruislag)",
            subtitle = "Rekomendasi teknis alih fungsi / penukaran harta benda wakaf untuk kepentingan umum",
            legalBasis = "UU No. 41 Tahun 2004 Pasal 40-41 & PP No. 42 Tahun 2006",
            processingTime = "14 Hari Kerja (Proses Kajian Lapangan)",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Hasil Tinjauan Lapangan Mutasi Wakaf",
            targetAudience = "Nazhir Wakaf dan Pihak Pemohon Ruislag (Instansi/Pemerintah)",
            requirements = listOf(
                "Surat Permohonan Izin Tukar Menukar dari Nazhir",
                "Alasan mendesak (kepentingan umum / pembangunan fasilitas publik)",
                "Data tanah pengganti yang nilainya sekurang-kurangnya setara atau lebih tinggi",
                "Berita Acara Musyawarah warga, tokoh agama, dan aparat desa setempat"
            ),
            procedures = listOf(
                "Tim KUA Biringbulu melakukan verifikasi berkas dan tinjauan lokasi tanah wakaf & calon pengganti",
                "Penyusunan Berita Acara Kajian Teknis Lapangan",
                "Penyampaian rekomendasi Kepala KUA ke Kantor Kemenag Gowa dan BWI (Badan Wakaf Indonesia)"
            ),
            note = "Harta benda wakaf tidak boleh diruislag tanpa izin tertulis dari Menteri Agama atas persetujuan BWI.",
            sampleTemplateText = "SURAT REKOMENDASI MUTASI / RUISLAG TANAH WAKAF\nBerdasarkan hasil verifikasi lapangan atas tanah wakaf di Desa..."
        ),
        KuaServiceItem(
            id = 28,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 5,
            title = "AIW / APAIW Hilang atau Rusak",
            subtitle = "Penerbitan Salinan Resmi Akta Ikrar Wakaf sebagai pengganti warkah yang hilang atau rusak",
            legalBasis = "PMA No. 73 Tahun 2013 tentang Tata Cara Perwakafan",
            processingTime = "2 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Salinan Resmi AIW / APAIW Berlegalisir",
            targetAudience = "Nazhir yang kehilangan warkah akta ikrar wakaf",
            requirements = listOf(
                "Surat Tanda Lapor Kehilangan dari Polsek Biringbulu (jika hilang)",
                "Warkah AIW fisik yang rusak (jika rusak)",
                "Surat Pengantar dari Kepala Desa setempat",
                "Fotokopi KTP Ketua Nazhir yang masih aktif"
            ),
            procedures = listOf(
                "PPAIW KUA Biringbulu membuka Buku Register Induk Arsip Perwakafan",
                "Pencocokan data batas tanah, wakif, dan peruntukan warkah",
                "Penerbitan Salinan Pengganti Akta Ikrar Wakaf bertanda tangan Kepala KUA"
            ),
            note = "Salinan pengganti memiliki keabsahan hukum yang sama untuk kelanjutan pensertifikatan BPN.",
            sampleTemplateText = "PERMOHONAN SALINAN AKTA IKRAR WAKAF PENGGANTI\nNomor AIW Lama: ... Objek: Tanah Wakaf Masjid..."
        ),
        KuaServiceItem(
            id = 29,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 6,
            title = "Rekomendasi Penggantian Nazhir",
            subtitle = "Penerbitan surat rekomendasi penggantian nazhir perorangan/organisasi yang meninggal, berhenti, atau tidak aktif",
            legalBasis = "UU No. 41 Tahun 2004 Pasal 45 & PMA No. 73/2013",
            processingTime = "2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Penggantian Nazhir ke BWI",
            targetAudience = "Badan Wakaf Indonesia (BWI), Pengurus Masjid, dan Ahli Waris Nazhir",
            requirements = listOf(
                "Surat Kematian nazhir lama (jika meninggal) atau surat pengunduran diri",
                "Berita Acara Rapat Pembentukan Pengurus Nazhir Baru tingkat desa/jamaah",
                "Daftar susunan calon nazhir baru (minimal 3 orang: Ketua, Sekretaris, Bendahara)",
                "Fotokopi KTP dan KK seluruh calon nazhir baru"
            ),
            procedures = listOf(
                "Penyerahan berkas musyawarah jamaah ke KUA Biringbulu",
                "KUA memeriksa syarat administratif dan integritas syar'i calon nazhir",
                "Penerbitan Surat Rekomendasi Penetapan Nazhir Baru"
            ),
            note = "Nazhir yang baru wajib berkomitmen menjaga dan mengembangkan aset wakaf sesuai amanah wakif.",
            sampleTemplateText = "REKOMENDASI PENGGANTIAN NAZHIR WAKAF\nNomor: B-.../Kua.21.06/BA.03/.../2026\nMenerangkan bahwa susunan Nazhir Wakaf telah diperbarui..."
        ),
        KuaServiceItem(
            id = 30,
            category = KuaServiceCategory.ZAKAT_DAN_WAKAF,
            numberInCategory = 7,
            title = "Penerbitan Surat Pengesahan Nazhir & Penggantian Nazhir",
            subtitle = "Penerbitan Surat Keputusan (SK) Pengesahan Nazhir Wakaf resmi dari KPAIW KUA Biringbulu",
            legalBasis = "PMA No. 73 Tahun 2013 & Peraturan BWI No. 1 Tahun 2020",
            processingTime = "2 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Keputusan (SK) Pengesahan Nazhir KUA Biringbulu",
            targetAudience = "Pengurus Nazhir Tanah Wakaf se-Kecamatan Biringbulu",
            requirements = listOf(
                "Surat Permohonan Pengesahan Nazhir",
                "Daftar Susunan Pengurus Nazhir (Ketua, Sekretaris, Bendahara, Anggota)",
                "Surat Pernyataan Kesediaan menjadi Nazhir bermeterai",
                "Fotokopi KTP seluruh pengurus",
                "Fotokopi AIW / APAIW atau surat keterangan tanah wakaf"
            ),
            procedures = listOf(
                "PPAIW meneliti kelayakan calon nazhir (Amanah, Baligh, Beragama Islam, Mampu Mengelola)",
                "Pembuatan draft SK Pengesahan Nazhir Wakaf",
                "Penandatanganan dan penyerahan SK resmi kepada pengurus nazhir"
            ),
            note = "Masa bakti nazhir perorangan berlaku selama 5 tahun dan dapat diperpanjang.",
            sampleTemplateText = "SURAT KEPUTUSAN PENGESAHAN NAZHIR WAKAF\nKepala Kantor Urusan Agama Kec. Biringbulu selaku PPAIW menetapkan..."
        ),

        // ==========================================
        // 4. KEMASJIDAN (6 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 31,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 1,
            title = "Penerbitan ID Masjid / Musala (SIMAS)",
            subtitle = "Pendaftaran dan penerbitan Nomor Identitas Nasional Masjid/Musala pada Sistem Informasi Masjid (SIMAS)",
            legalBasis = "Kepdirjen Bimas Islam No. DJ.II/802 Tahun 2014 tentang Standar Pembinaan Kemasjidan",
            processingTime = "2 - 3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Tanda Terdaftar & Piagam Nomor Identitas Nasional SIMAS",
            targetAudience = "Pengurus Takmir Masjid dan Musala se-Kecamatan Biringbulu",
            requirements = listOf(
                "Formulir Profil Masjid/Musala (Nama, Tipologi, Luas Bangunan/Tanah, Tahun Berdiri, Status Tanah)",
                "Susunan Pengurus Takmir Masjid yang disahkan Kepala Desa",
                "Foto fisik bangunan masjid (Tampak Depan, Dalam, Mihrab, dan Plang Nama)",
                "Titik Koordinat Geografis (Latitude & Longitude) lokasi masjid",
                "Fotokopi Surat Tanah / AIW Wakaf"
            ),
            procedures = listOf(
                "Takmir menyerahkan formulir profil dan foto masjid ke KUA",
                "Operator SIMAS KUA Biringbulu memverifikasi berkas dan titik koordinat",
                "Generate ID SIMAS 16 Digit dan pencetakan Surat Keterangan Terdaftar Nasional"
            ),
            note = "ID SIMAS wajib dimiliki untuk pengajuan bantuan hibah Kemenag, BAZNAS, maupun permohonan arah kiblat.",
            sampleTemplateText = "FORMULIR PENDAFTARAN SIMAS\nNama Masjid: ... Tipologi: Masjid Jami / Masjid Desa ... Alamat Desa: ..."
        ),
        KuaServiceItem(
            id = 32,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 2,
            title = "Surat Keterangan Terdaftar (SKT) Masjid/Musala",
            subtitle = "Penerbitan surat keterangan legalitas terdaftar untuk keperluan pembukaan rekening bank takmir & proposal",
            legalBasis = "Kepdirjen Bimas Islam tentang Pengelolaan Data Rumah Ibadah",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Keterangan Terdaftar (SKT) KUA Biringbulu",
            targetAudience = "Pengurus Takmir Masjid / Musala",
            requirements = listOf(
                "Nomor ID SIMAS yang telah terbit",
                "SK Pengurus Takmir yang masih berlaku",
                "Surat Permohonan dari Ketua Takmir Masjid"
            ),
            procedures = listOf(
                "Takmir mengajukan permohonan ke KUA",
                "Verifikasi keaktifan data SIMAS oleh staf kemasjidan",
                "Penerbitan dan penandatanganan SKT oleh Kepala KUA Biringbulu"
            ),
            note = "Sangat dibutuhkan takmir untuk membuat rekening bank atas nama masjid (bukan rekening pribadi).",
            sampleTemplateText = "SURAT KETERANGAN TERDAFTAR MASJID/MUSALA\nNomor: B-.../Kua.21.06/BA.01/.../2026\nMenerangkan bahwa Masjid ... telah terdaftar resmi..."
        ),
        KuaServiceItem(
            id = 33,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 3,
            title = "Rekomendasi Bantuan Masjid / Musala",
            subtitle = "Penerbitan rekomendasi resmi KUA untuk pengajuan bantuan rehabilitasi atau operasional dari Kemenag / Pemda",
            legalBasis = "Petunjuk Teknis Bantuan Pembangunan/Rehabilitasi Rumah Ibadah Kemenag RI",
            processingTime = "2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Bantuan Rumah Ibadah KUA Biringbulu",
            targetAudience = "Panitia Pembangunan / Takmir Masjid se-Biringbulu",
            requirements = listOf(
                "Proposal Permohonan Bantuan lengkap dengan Rencana Anggaran Biaya (RAB)",
                "Surat Keterangan Terdaftar SIMAS",
                "SK Panitia Pembangunan / Pengurus Takmir",
                "Foto kondisi fisik bangunan yang memerlukan renovasi",
                "Fotokopi Buku Rekening Bank atas nama Masjid"
            ),
            procedures = listOf(
                "Panitia menyerahkan proposal pembangunan ke KUA Biringbulu",
                "Staf kemasjidan meninjau kelayakan administratif dan fakta kebutuhan lapangan",
                "Penerbitan Surat Rekomendasi Resmi ditujukan ke Kemenag Gowa / Kanwil Kemenag Sulsel"
            ),
            note = "KUA Biringbulu menjamin proses rekomendasi objektif tanpa pungutan apapun.",
            sampleTemplateText = "SURAT REKOMENDASI PERMOHONAN BANTUAN\nBerdasarkan proposal yang diajukan Panitia Pembangunan Masjid..."
        ),
        KuaServiceItem(
            id = 34,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 4,
            title = "Rekomendasi Perubahan Status Musala Menjadi Masjid",
            subtitle = "Verifikasi persyaratan teknis dan penerbitan rekomendasi peningkatan fungsi musala menjadi masjid jami",
            legalBasis = "Peraturan Bersama Menteri Agama & Mendagri No. 9 & 8 Tahun 2006",
            processingTime = "5 Hari Kerja (Termasuk Tinjauan Lapangan)",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Peningkatan Status Rumah Ibadah",
            targetAudience = "Pengurus Musala dan Jamaah Lingkungan Setempat",
            requirements = listOf(
                "Surat Permohonan dari Pengurus Musala",
                "Daftar minimal 90 orang calon jamaah shalat Jumat ber-KTP setempat",
                "Surat persetujuan dari minimal 60 orang warga masyarakat sekitar",
                "Rekomendasi tertulis dari Kepala Desa setempat di Biringbulu",
                "Hasil musyawarah tidak menimbulkan friksi dengan masjid jami terdekat"
            ),
            procedures = listOf(
                "Penyampaian berkas permohonan dan musyawarah jamaah",
                "Tim KUA Biringbulu melakukan verifikasi jarak dan tinjauan kelayakan shalat Jumat",
                "Penerbitan Surat Rekomendasi Peningkatan Status Musala Menjadi Masjid"
            ),
            note = "Memperhatikan jarak ideal antar masjid demi menjaga kekompakan dan syiar ukhuwah jamaah.",
            sampleTemplateText = "REKOMENDASI PERUBAHAN STATUS MUSALA MENJADI MASJID\nSetelah melakukan verifikasi lapangan atas Musala..."
        ),
        KuaServiceItem(
            id = 35,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 5,
            title = "Perubahan Data Masjid / Musala",
            subtitle = "Pembaruan informasi luas tanah, nama masjid, tipologi, atau titik koordinat pada database SIMAS",
            legalBasis = "Kepdirjen Bimas Islam tentang SIMAS Nasional",
            processingTime = "1 - 2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Bukti Update Profil SIMAS Terkini",
            targetAudience = "Takmir Masjid/Musala yang mengalami perubahan data",
            requirements = listOf(
                "Nomor ID SIMAS lama",
                "Berita Acara Perubahan Data (nama baru / penambahan luas / pergantian imam)",
                "Dokumen pendukung (Sertifikat tanah baru / Foto renovasi terbaru)"
            ),
            procedures = listOf(
                "Takmir menyerahkan formulir pembaruan data",
                "Operator SIMAS melakukan sinkronisasi pembaruan ke server pusat Bimas Islam",
                "Penerbitan lembar profil SIMAS termutakhir"
            ),
            note = "Data akurat memudahkan alokasi program pembinaan dan bantuan kemenag.",
            sampleTemplateText = "FORMULIR PERUBAHAN DATA SIMAS\nID SIMAS: ... Data yang diubah: Nama Masjid / Luas Bangunan..."
        ),
        KuaServiceItem(
            id = 36,
            category = KuaServiceCategory.KEMASJIDAN,
            numberInCategory = 6,
            title = "Rekomendasi Penetapan Kepengurusan Takmir Masjid Besar",
            subtitle = "Fasilitasi dan rekomendasi susunan pengurus Takmir Masjid Besar tingkat Kecamatan Biringbulu",
            legalBasis = "Kepdirjen Bimas Islam No. DJ.II/802 Tahun 2014",
            processingTime = "3 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Pengesahan Pengurus Takmir Masjid Besar",
            targetAudience = "Pengurus Masjid Besar Kecamatan Biringbulu & Camat Biringbulu",
            requirements = listOf(
                "Berita Acara Rapat Formatur Pemilihan Pengurus Masjid Besar Biringbulu",
                "Daftar Nama Susunan Penasehat, Pengurus Harian, dan Seksi-seksi",
                "Fotokopi KTP seluruh pengurus inti"
            ),
            procedures = listOf(
                "Panitia musyawarah menyerahkan berkas susunan pengurus",
                "Kepala KUA melakukan koordinasi bersama unsur Forkopimcam Biringbulu",
                "Penerbitan Rekomendasi pengesahan SK oleh Camat Biringbulu"
            ),
            note = "Masjid Besar menjadi sentra kegiatan keagamaan dan pembinaan umat tingkat kecamatan.",
            sampleTemplateText = "REKOMENDASI KEPENGURUSAN MASJID BESAR BIRINGBULU\nNomor: B-.../Kua.21.06/BA.01/.../2026\nMenyatakan persetujuan atas susunan pengurus..."
        ),

        // ==========================================
        // 5. DATA KEAGAMAAN (3 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 37,
            category = KuaServiceCategory.DATA_KEAGAMAAN,
            numberInCategory = 1,
            title = "Penyediaan Data Keagamaan dan Sarana Keagamaan",
            subtitle = "Pelayanan data statistik rumah ibadah, lembaga keagamaan, ormas Islam, dan tokoh agama se-Biringbulu",
            legalBasis = "UU No. 14 Tahun 2008 tentang Keterbukaan Informasi Publik (KIP) & PMA SOP KUA",
            processingTime = "1 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Buku / Lembar Data Statistik Keagamaan Kecamatan Biringbulu",
            targetAudience = "Mahasiswa/Peneliti, Instansi Pemerintah, Ormas, Masyarakat Umum",
            requirements = listOf(
                "Surat Permohonan Data resmi dari kampus / lembaga / instansi",
                "Identitas Pemohon (KTP / Kartu Mahasiswa)",
                "Mengisi formulir peruntukan pemanfaatan data riset"
            ),
            procedures = listOf(
                "Pemohon mengajukan surat permohonan ke loket PTSP KUA",
                "Petugas data menyusun tabel statistik data yang diminta (Masjid, Wakaf, Nikah, Majelis Taklim)",
                "Penyerahan lembar data resmi bertanda tangan pejabat KUA"
            ),
            note = "Menyediakan data lengkap 11 desa di wilayah Kecamatan Biringbulu.",
            sampleTemplateText = "PERMOHONAN DATA KEAGAMAAN & SARANA IBADAH\nKeperluan: Riset Ilmiah / Perencanaan Pembangunan..."
        ),
        KuaServiceItem(
            id = 38,
            category = KuaServiceCategory.DATA_KEAGAMAAN,
            numberInCategory = 2,
            title = "Penerbitan Surat Rekomendasi Bantuan Keagamaan",
            subtitle = "Penerbitan rekomendasi bagi ormas Islam, pondok pesantren, TPQ/TPA, dan lembaga dakwah",
            legalBasis = "PMA No. 67 Tahun 2015 tentang Bantuan Pemerintah pada Kemenag",
            processingTime = "2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi Lembaga Keagamaan",
            targetAudience = "Pengelola TPQ, LPPTKA, Madrasah Diniyah, Majelis Taklim Biringbulu",
            requirements = listOf(
                "Proposal Bantuan Lembaga",
                "SK Izin Operasional / Tanda Terdaftar Kemenag",
                "Daftar Santri / Jamaah dan Struktur Ustadz/Pengurus",
                "Fotokopi KTP Pimpinan Lembaga"
            ),
            procedures = listOf(
                "Penyerahan berkas ke staf Penerangan Agama Islam KUA",
                "Verifikasi faktual aktivitas pembelajaran keagamaan di desa",
                "Penerbitan Surat Rekomendasi Kepala KUA Biringbulu"
            ),
            note = "Mendukung pengembangan pendidikan Al-Quran dan dakwah masyarakat pedesaan.",
            sampleTemplateText = "SURAT REKOMENDASI BANTUAN LEMBAGA KEAGAMAAN\nMenerangkan bahwa TPA/TPQ ... aktif melaksanakan kegiatan pembinaan Al-Quran..."
        ),
        KuaServiceItem(
            id = 39,
            category = KuaServiceCategory.DATA_KEAGAMAAN,
            numberInCategory = 3,
            title = "Penerbitan Surat Rekomendasi Pendirian Majelis Taklim",
            subtitle = "Pencatatan dan penerbitan rekomendasi legalitas pendaftaran Majelis Taklim ke Kemenag Kab. Gowa",
            legalBasis = "PMA No. 29 Tahun 2019 tentang Majelis Taklim",
            processingTime = "2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Rekomendasi & Lembar Tanda Daftar Majelis Taklim",
            targetAudience = "Pengurus Majelis Taklim Ibu-ibu / Bapak-bapak di desa",
            requirements = listOf(
                "Surat Permohonan Rekomendasi Pendirian",
                "Susunan Pengurus Majelis Taklim (Ketua, Sekretaris, Bendahara, Ustadz/Guru)",
                "Daftar minimal 15 orang jamaah aktif beserta fotokopi KTP",
                "Alamat domisili sekretariat dan tempat pengajian berkala",
                "Surat Keterangan Domisili dari Kepala Desa"
            ),
            procedures = listOf(
                "Pengurus mengajukan berkas formulir Majelis Taklim ke Penyuluh Agama Islam KUA",
                "Penyuluh meneliti susunan kurikulum kajian dan struktur kepengurusan",
                "Penerbitan Surat Rekomendasi untuk penerbitan SKT Kemenag Kabupaten"
            ),
            note = "Terdaftar di Kemenag membuka akses bantuan sarana dakwah dan pembinaan berkala.",
            sampleTemplateText = "SURAT REKOMENDASI PENDAFTARAN MAJELIS TAKLIM\nNama Majelis Taklim: ... Tempat Pengajian: Masjid ... Desa: ..."
        ),

        // ==========================================
        // 6. KETATAUSAHAAN KUA (2 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 40,
            category = KuaServiceCategory.KETATAUSAHAAN,
            numberInCategory = 1,
            title = "Pelayanan Persuratan Kedinasan",
            subtitle = "Penerimaan surat masuk, disposisi, pengiriman surat keluar dinas, dan administrasi umum",
            legalBasis = "PMA No. 34 Tahun 2016 tentang Tata Persuratan Dinas Kementerian Agama",
            processingTime = "Langsung / Hari yang sama",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Tanda Terima Surat / Nomor Agenda Surat Keluar KUA",
            targetAudience = "Instansi Pemerintah, Desa, Ormas, dan Masyarakat",
            requirements = listOf(
                "Surat dinas resmi bertanda tangan dan berstempel instansi pemohon",
                "Menyerahkan minimal 1 rangkap naskah dinas untuk arsip KUA"
            ),
            procedures = listOf(
                "Penerimaan surat di loket persuratan PTSP",
                "Pencatatan dalam Buku Agenda Surat Masuk / Sistem Digital",
                "Disposisi Kepala KUA Biringbulu dan penerusan tindak lanjut ke petugas terkait"
            ),
            note = "Pelayanan cepat tanggap untuk seluruh korespondensi kedinasan wilayah Biringbulu.",
            sampleTemplateText = "BUKU EKSPEDISI SURAT MASUK/KELUAR\nNomor Agenda: ... Pengirim: ... Perihal: ..."
        ),
        KuaServiceItem(
            id = 41,
            category = KuaServiceCategory.KETATAUSAHAAN,
            numberInCategory = 2,
            title = "Pelayanan Kearsipan KUA",
            subtitle = "Pencarian kembali warkah lama, buku register nikah kuno, dan arsip dokumen bersejarah keagamaan",
            legalBasis = "UU No. 43 Tahun 2009 tentang Kearsipan & PMA Tata Kelola Kearsipan",
            processingTime = "1 - 2 Hari Kerja",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Surat Keterangan Hasil Pelacakan Arsip / Salinan Warkah",
            targetAudience = "Masyarakat yang membutuhkan data arsip peristiwa nikah masa lampau",
            requirements = listOf(
                "Surat Permohonan Penelusuran Arsip",
                "Menyebutkan perkiraan tahun peristiwa nikah dan nama kedua belah pihak serta orang tua",
                "Fotokopi KTP Pemohon (ahli waris / pihak bersangkutan)"
            ),
            procedures = listOf(
                "Arsiparis KUA melakukan penelusuran pada brankas arsip register kuno",
                "Pencocokan data warkah fisik dengan lembar buku akta nikah",
                "Penerbitan surat keterangan kepemilikan arsip resmi"
            ),
            note = "Membantu warga membuktikan data nasab dan status pernikahan orang tua terdahulu.",
            sampleTemplateText = "SURAT KETERANGAN PENELUSURAN ARSIP\nSetelah dilakukan pemeriksaan buku register nikah tahun ... ditemukan catatan atas nama..."
        ),

        // ==========================================
        // 7. FUNGSI TAMBAHAN / PENUGASAN MENTERI (2 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 42,
            category = KuaServiceCategory.FUNGSI_TAMBAHAN,
            numberInCategory = 1,
            title = "Deteksi dan Cegah Dini Konflik Sosial Berdimensi Keagamaan",
            subtitle = "Monitoring, pelaporan potensi kerawanan, dan mediasi kerukunan umat beragama bersama Forkopimcam",
            legalBasis = "PMA No. 34 Tahun 2016 tentang Organisasi dan Tata Kerja KUA & Program Moderasi Beragama Kemenag",
            processingTime = "Respons Cepat 1x24 Jam",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Laporan Early Warning System (EWS) & Berita Acara Rekonsiliasi Damai",
            targetAudience = "Tokoh Agama, Tokoh Masyarakat, Forum Kerukunan Umat se-Biringbulu",
            requirements = listOf(
                "Laporan / Informasi awal indikasi ketegangan keagamaan atau aliran menyimpang",
                "Identitas pelapor dijamin kerahasiaannya"
            ),
            procedures = listOf(
                "Penerimaan laporan deteksi dini oleh Tim Penyuluh / Penghulu KUA",
                "Koordinasi cepat dengan Polsek Biringbulu, Koramil, Camat, dan MUI Kecamatan",
                "Pendekatan dialog persuasif dan pembinaan moderasi beragama kepada kelompok terkait"
            ),
            note = "Menjaga keharmonisan dan persaudaraan masyarakat Biringbulu yang religius dan guyub.",
            sampleTemplateText = "LAPORAN DETEKSI DINI KERUKUNAN UMAT\nLokasi Desa: ... Indikasi Masalah: ... Langkah Pencegahan: ..."
        ),
        KuaServiceItem(
            id = 43,
            category = KuaServiceCategory.FUNGSI_TAMBAHAN,
            numberInCategory = 2,
            title = "Jaminan Produk Halal (Pendampingan PPH)",
            subtitle = "Pendampingan sertifikasi halal gratis (Sehati / Self Declare) bagi pelaku Usaha Mikro & Kecil (UMK) makanan/minuman",
            legalBasis = "UU No. 33 Tahun 2014 tentang Jaminan Produk Halal jo UU Cipta Kerja & BPJPH Kemenag",
            processingTime = "Pendampingan s.d. Terbit Sertifikat Halal BPJPH",
            cost = "Rp 0,- (Gratis Program Sehati BPJPH)",
            outputDocument = "Sertifikat Halal Resmi BPJPH RI & Label Halal Indonesia",
            targetAudience = "Pelaku Usaha Mikro & Kecil (UMK) di Kecamatan Biringbulu",
            requirements = listOf(
                "Memiliki NIB (Nomor Induk Berusaha) berbasis risiko",
                "Produk tidak berisiko tinggi (makanan/minuman olahan dari bahan halal murni)",
                "Memiliki KTP dan Nomor WhatsApp aktif",
                "Foto produk, proses produksi, dan daftar bahan yang digunakan"
            ),
            procedures = listOf(
                "Pelaku usaha menghubungi Pendamping Proses Produk Halal (P3H) di KUA Biringbulu",
                "P3H melakukan verifikasi bahan dan proses pembuatan di lokasi usaha",
                "Penginputan data ke aplikasi SIHALAL BPJPH",
                "Sidang Komite Fatwa MUI dan penerbitan Sertifikat Halal Digital"
            ),
            note = "Meningkatkan daya saing dan jaminan mutu produk olahan UMKM lokal Biringbulu.",
            sampleTemplateText = "FORMULIR PENDAMPINGAN SERTIFIKASI HALAL SELF-DECLARE\nNama Usaha: ... Jenis Produk: Keripik / Kue Tradisional / Kopi..."
        ),

        // ==========================================
        // 8. KONSULTASI SYARIAH (2 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 44,
            category = KuaServiceCategory.KONSULTASI_SYARIAH,
            numberInCategory = 1,
            title = "Konsultasi Hukum Islam (Ibadah, Waris Mawaris, Hukum Keluarga)",
            subtitle = "Layanan konsultasi fatwa fikih, pembagian harta warisan syariah (Faraid), ibadah praktis, dan muamalah",
            legalBasis = "Kompilasi Hukum Islam di Indonesia (Inpres No. 1/1991) & Fatwa Dewan Syariah Nasional MUI",
            processingTime = "Langsung saat konsultasi",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Lembar Fatwa Nasihat / Skema Pembagian Waris Faraid",
            targetAudience = "Masyarakat Umum, Keluarga Muslim, dan Calon Ahli Waris",
            requirements = listOf(
                "Daftar silsilah ahli waris (Suami/Istri, Anak Laki/Perempuan, Orang Tua, Saudara) - untuk waris",
                "Rincian harta peninggalan bersih setelah dipotong hutang/wasiat/biaya jenazah",
                "Surat Kematian pewaris"
            ),
            procedures = listOf(
                "Pemohon menyampaikan pokok masalah konsultasi kepada Penghulu/Penyuluh Syariah",
                "Kajian dalil Al-Quran, Hadits, dan Kompilasi Hukum Islam",
                "Penyusunan tabel hisab mawaris (ashabah, dzawil furudh) dan solusi musyawarah islah"
            ),
            note = "Mencegah sengketa keluarga dengan pembagian harta waris yang adil dan sesuai syariat.",
            sampleTemplateText = "SIMULASI HISAB WARIS FARAID KUA BIRINGBULU\nNama Pewaris: ... Nilai Harta Bersih: ... Rincian Bagian Ahli Waris: ..."
        ),
        KuaServiceItem(
            id = 45,
            category = KuaServiceCategory.KONSULTASI_SYARIAH,
            numberInCategory = 2,
            title = "Kalibrasi Arah Kiblat Masjid / Musala / Pemukiman",
            subtitle = "Pengukuran presisi arah kiblat menggunakan metode Rashdul Qiblah, theodolite / kompas falakiyah terkalibrasi",
            legalBasis = "Pedoman Pembinaan Syariah & Hisab Rukyat Kemenag RI",
            processingTime = "1 Hari Pengukuran Lapangan",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Berita Acara dan Sertifikat Arah Kiblat Resmi KUA Biringbulu",
            targetAudience = "Pengurus Takmir Masjid, Musala, Panitia Pembangunan, Warga",
            requirements = listOf(
                "Surat Permohonan Pengukuran Arah Kiblat dari Pengurus Takmir / Tokoh Masyarakat",
                "Lokasi tanah / bangunan masjid yang siap untuk ditarik garis shaf",
                "Kehadiran saksi takmir dan tokoh masyarakat pada saat pengukuran"
            ),
            procedures = listOf(
                "Penetapan jadwal pengukuran falakiyah oleh Tim Hisab Rukyat KUA Biringbulu",
                "Pengukuran azimut kiblat (Kordinat Biringbulu: ~292.5° dari Utara Sejati)",
                "Penandaan garis shaf permanen di lantai/tanah",
                "Penerbitan Berita Acara Kalibrasi Arah Kiblat bertanda tangan Kepala KUA"
            ),
            note = "Menjamin ketenangan dan keabsahan ibadah shalat jamaah menghadap tepat ke Ka'bah Baitullah.",
            sampleTemplateText = "BERITA ACARA PENGUKURAN ARAH KIBLAT\nNomor: B-.../Kua.21.06/BA.02/.../2026\nPada hari ini telah dilakukan kalibrasi arah kiblat Masjid..."
        ),

        // ==========================================
        // 9. PENERANGAN AGAMA ISLAM (3 Layanan)
        // ==========================================
        KuaServiceItem(
            id = 46,
            category = KuaServiceCategory.PENERANGAN_ISLAM,
            numberInCategory = 1,
            title = "Bimbingan dan Penyuluhan Keagamaan",
            subtitle = "Penyuluhan materi keislaman, bimbingan mualaf, pengentasan buta aksara Al-Quran di desa se-Biringbulu",
            legalBasis = "Kepdirjen Bimas Islam tentang Petunjuk Teknis Jabatan Fungsional Penyuluh Agama Islam",
            processingTime = "Sesuai Jadwal Rutin Penyuluh Desa",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Laporan Bimbingan Penyuluhan & Sertifikat Pembinaan",
            targetAudience = "Kelompok Binaan Desa, Majelis Taklim, Remaja Masjid, Mualaf",
            requirements = listOf(
                "Surat Permohonan Bimbingan / Khotib / Penceramah dari takmir atau kelompok warga",
                "Waktu dan tema kajian yang diinginkan (Akidah, Akhlak, Fikih, Moderasi)"
            ),
            procedures = listOf(
                "Penugasan Penyuluh Agama Islam PNS / PPPK / Non-PNS KUA Biringbulu ke desa terkait",
                "Pelaksanaan bimbingan dakwah interaktif dan tadarus Al-Quran",
                "Evaluasi perkembangan pemahaman keagamaan jamaah binaan"
            ),
            note = "Tersedia pendampingan khusus syahadatain dan sertifikat pengislaman bagi mualaf.",
            sampleTemplateText = "PERMOHONAN PENUGASAN PENYULUH AGAMA ISLAM\nUntuk mengisi pengajian rutin di Desa ... Tanggal: ... Materi: ..."
        ),
        KuaServiceItem(
            id = 47,
            category = KuaServiceCategory.PENERANGAN_ISLAM,
            numberInCategory = 2,
            title = "Sosialisasi dan Edukasi Produk Halal",
            subtitle = "Kampanye sadar halal, sosialisasi kantin halal madrasah/sekolah, dan literasi konsumen cerdas",
            legalBasis = "UU No. 33 Tahun 2014 & Keputusan Kepala BPJPH Kemenag",
            processingTime = "Berkala / Sesuai Undangan",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Materi Sosialisasi Halal & Rekomendasi Titik Kantin Halal",
            targetAudience = "Masyarakat, Pedagang Makanan, Sekolah, Pengelola Pasar Biringbulu",
            requirements = listOf(
                "Permohonan sosialisasi dari paguyuban pedagang, sekolah, atau aparat desa"
            ),
            procedures = listOf(
                "Penyuluh Halal KUA Biringbulu menyelenggarakan sesi edukasi pentingnya konsumsi halal & thayyib",
                "Simulasi identifikasi bahan kritis dan panduan pendaftaran label halal BPJPH",
                "Pembagian materi panduan produk halal kemenag"
            ),
            note = "Mendukung terwujudnya ekosistem halal di wilayah pedesaan Kabupaten Gowa.",
            sampleTemplateText = "PERMOHONAN SOSIALISASI PRODUK HALAL\nDitujukan kepada Koordinator Penyuluh Produk Halal KUA Biringbulu..."
        ),
        KuaServiceItem(
            id = 48,
            category = KuaServiceCategory.PENERANGAN_ISLAM,
            numberInCategory = 3,
            title = "Pencegahan Konflik Sosial Keagamaan",
            subtitle = "Sosialisasi moderasi beragama, penguatan wawasan kebangsaan, dan dialog lintas tokoh pemuda",
            legalBasis = "Perpres No. 58 Tahun 2023 tentang Penguatan Moderasi Beragama & PMA No. 34/2016",
            processingTime = "Program Terpadu Berkelanjutan",
            cost = "Rp 0,- (Gratis)",
            outputDocument = "Piagam Deklarasi Kerukunan Umat & Risalah Dialog Tokoh",
            targetAudience = "Pemuda, Karang Taruna, Tokoh Agama, Tokoh Adat se-Kecamatan Biringbulu",
            requirements = listOf(
                "Keikutsertaan perwakilan unsur pemuda, imam desa, dan tokoh masyarakat"
            ),
            procedures = listOf(
                "Penyelenggaraan Forum Dialog Kerukunan dan Moderasi Beragama tingkat kecamatan",
                "Diskusi pencegahan paham radikalisme, ujaran kebencian, dan fanatisme sempit",
                "Penandatanganan komitmen bersama menjaga stabilitas kamtibmas keagamaan di Biringbulu"
            ),
            note = "Meneguhkan nilai kearifan lokal 'Siri' Na Pacce' yang sejalan dengan persaudaraan ukhuwah Islamiyah.",
            sampleTemplateText = "NOTULENSI FORUM MODERASI BERAGAMA KUA BIRINGBULU\nTema: Menjaga Kerukunan Umat di Bumi Biringbulu Gowa..."
        )
    )

    fun getServiceById(id: Int): KuaServiceItem? {
        return allServices.find { it.id == id }
    }

    fun getServicesByCategory(category: KuaServiceCategory): List<KuaServiceItem> {
        return allServices.filter { it.category == category }
    }

    fun searchServices(query: String): List<KuaServiceItem> {
        if (query.isBlank()) return allServices
        val cleanQuery = query.trim().lowercase()
        return allServices.filter {
            it.title.lowercase().contains(cleanQuery) ||
            it.subtitle.lowercase().contains(cleanQuery) ||
            it.category.title.lowercase().contains(cleanQuery) ||
            it.outputDocument.lowercase().contains(cleanQuery) ||
            it.requirements.any { req -> req.lowercase().contains(cleanQuery) }
        }
    }
}
