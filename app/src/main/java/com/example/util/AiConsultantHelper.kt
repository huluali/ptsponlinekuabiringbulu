package com.example.util

import com.example.data.BiringbuluData
import com.example.data.KuaServiceData

object AiConsultantHelper {

    data class AiResponse(
        val title: String,
        val summary: String,
        val details: List<String>,
        val relatedServiceId: Int? = null,
        val suggestedQuestions: List<String>
    )

    fun answerQuery(query: String): AiResponse {
        val q = query.trim().lowercase()

        // 1. Syarat Nikah / Pendaftaran Nikah
        if (q.contains("syarat nikah") || q.contains("daftar nikah") || q.contains("mau nikah") || q.contains("berkas nikah") || q.contains("kawin")) {
            return AiResponse(
                title = "Panduan Pendaftaran & Syarat Nikah KUA Biringbulu",
                summary = "Pendaftaran nikah wajib dilakukan minimal 10 hari kerja sebelum hari akad nikah. Jika kurang dari 10 hari, wajib melampirkan Dispensasi Camat.",
                details = listOf(
                    "1. Surat Pengantar Nikah (Model N1-N4) dari Kantor Desa/Kelurahan setempat di Biringbulu.",
                    "2. Fotokopi KTP, KK, dan Akta Kelahiran/Ijazah calon pengantin pria dan wanita.",
                    "3. Fotokopi KTP orang tua/wali nikah dan 2 orang saksi akad.",
                    "4. Pasfoto latar belakang BIRU ukuran 2x3 (4 lbr) dan 4x6 (2 lbr) berpeci/berkerudung.",
                    "5. Surat Keterangan Pemeriksaan Kesehatan dari Puskesmas / Sertifikat Elsimil.",
                    "6. Biaya: GRATIS Rp 0,- jika akad di Balai Nikah KUA (Senin-Jumat jam kerja) atau Rp 600.000,- jika di luar KUA/hari libur via transfer Kas Negara."
                ),
                relatedServiceId = 1,
                suggestedQuestions = listOf(
                    "Berapa biaya nikah di luar KUA?",
                    "Bagaimana jika wali nikah berhalangan hadir?",
                    "Apa saja tahapan bimbingan pra-nikah?"
                )
            )
        }

        // 2. Biaya Nikah / Gratis / Bayar
        if (q.contains("biaya") || q.contains("bayar") || q.contains("gratis") || q.contains("tarif") || q.contains("600")) {
            return AiResponse(
                title = "Aturan Resmi Biaya Pelayanan Nikah (PP No. 59/2014)",
                summary = "Seluruh pelayanan di KUA berprinsip transparan dan akuntabel sesuai ketentuan PNBP Kementerian Agama RI.",
                details = listOf(
                    "• DI DALAM KANTOR KUA: Rp 0,- (GRATIS) pada hari kerja (Senin-Kamis 07.30-16.00 WITA, Jumat 07.30-16.30 WITA).",
                    "• DI LUAR KANTOR KUA / HARI LIBUR: Rp 600.000,- disetor langsung ke Kas Negara via Bank/Pos/SIMKAH (Kode Billing).",
                    "• BEBAS BIAYA KHUSUS: Warga tidak mampu dengan Surat Keterangan Tidak Mampu (SKTM) dari Desa tidak dikenakan biaya (Rp 0,-).",
                    "• PENTING: Petugas/Penghulu dilarang keras menerima uang tip, transport, atau bingkisan dalam bentuk apapun."
                ),
                relatedServiceId = 6,
                suggestedQuestions = listOf(
                    "Bagaimana cara pembayaran kode billing SIMKAH?",
                    "Apa syarat pengajuan bebas biaya SKTM?",
                    "Apakah ada biaya untuk legalisir buku nikah?"
                )
            )
        }

        // 3. Buku Nikah Hilang / Rusak / Duplikat
        if (q.contains("hilang") || q.contains("rusak") || q.contains("duplikat") || q.contains("ganti buku")) {
            return AiResponse(
                title = "Prosedur Penggantian Buku Nikah Hilang / Rusak",
                summary = "KUA Biringbulu menerbitkan Duplikat Buku Nikah resmi secara GRATIS tanpa pungutan biaya.",
                details = listOf(
                    "1. Surat Tanda Lapor Kehilangan dari Kepolisian (Polsek Biringbulu/Polres Gowa) jika hilang.",
                    "2. Buku Nikah fisik yang rusak (jika alasan penggantian karena rusak terbakar/robek/lapuk).",
                    "3. Fotokopi KTP dan Kartu Keluarga Suami & Istri.",
                    "4. Pasfoto 2x3 latar biru 2 lembar.",
                    "5. Mengisi Surat Pernyataan Kehilangan/Kerusakan bermeterai di loket PTSP KUA.",
                    "6. Waktu pengerjaan: 1 - 3 hari kerja setelah verifikasi register akta induk."
                ),
                relatedServiceId = 12,
                suggestedQuestions = listOf(
                    "Berapa lama proses terbit duplikat buku nikah?",
                    "Bagaimana jika nama di buku nikah salah ketik?",
                    "Apakah legalisir buku nikah bisa ditunggu?"
                )
            )
        }

        // 4. Wakaf / Tanah Wakaf / AIW / Nazhir
        if (q.contains("wakaf") || q.contains("aiw") || q.contains("apaw") || q.contains("tanah wakaf") || q.contains("nazhir") || q.contains("nazir")) {
            return AiResponse(
                title = "Tata Cara Pembuatan Akta Ikrar Wakaf (AIW) & Legalitas",
                summary = "Kepala KUA Biringbulu bertindak sebagai Pejabat Pembuat Akta Ikrar Wakaf (PPAIW) resmi untuk pengamanan aset keagamaan.",
                details = listOf(
                    "1. Surat Permohonan Ikrar Wakaf dari Wakif (pemilik tanah).",
                    "2. Sertifikat Hak Milik (SHM) / Surat Rincik / SPPT PBB asli tanah yang akan diwakafkan.",
                    "3. Surat Keterangan Bebas Sengketa dan Riwayat Tanah dari Kantor Desa.",
                    "4. Fotokopi KTP Wakif, Pengurus Nazhir (minimal 3 orang: Ketua, Sekretaris, Bendahara), dan 2 orang saksi.",
                    "5. Pengucapan ikrar wakaf di hadapan Kepala KUA selaku PPAIW.",
                    "6. Setelah AIW terbit, KUA mendampingi pengurusan Sertifikat Tanah Wakaf gratis ke Kantor Pertanahan (BPN) Gowa."
                ),
                relatedServiceId = 25,
                suggestedQuestions = listOf(
                    "Bagaimana jika wakif sudah meninggal dunia (APAIW)?",
                    "Apa syarat pergantian pengurus nazhir?",
                    "Bagaimana cara mendaftarkan tanah wakaf ke SIMZAT/SIWAK?"
                )
            )
        }

        // 5. Kemasjidan / SIMAS / ID Masjid
        if (q.contains("masjid") || q.contains("musala") || q.contains("musholla") || q.contains("simas") || q.contains("takmir") || q.contains("arah kiblat")) {
            return AiResponse(
                title = "Layanan Kemasjidan & Penerbitan ID SIMAS",
                summary = "Seluruh masjid dan musala di 11 Desa Biringbulu wajib memiliki Nomor Identitas Nasional SIMAS Kemenag.",
                details = listOf(
                    "1. Mengisi Formulir Profil Masjid (Nama, tipologi, luas tanah, luas bangunan, status tanah wakaf/hibah).",
                    "2. Melampirkan SK Susunan Pengurus Takmir Masjid yang disahkan Kepala Desa.",
                    "3. Foto bangunan masjid (Tampak depan, dalam, ruang mihrab, plang nama).",
                    "4. Titik koordinat GPS (dapat dibantu tim KUA).",
                    "5. Manfaat ID SIMAS: Syarat mutlak pengajuan bantuan hibah Kemenag/Pemda, sertifikasi arah kiblat, dan pembukaan rekening bank resmi takmir."
                ),
                relatedServiceId = 31,
                suggestedQuestions = listOf(
                    "Bagaimana cara mengajukan kalibrasi arah kiblat resmi?",
                    "Apa syarat perubahan musala menjadi masjid?",
                    "Bagaimana membuat rekomendasi bantuan masjid?"
                )
            )
        }

        // 6. Zakat / Pertanian / Nisab
        if (q.contains("zakat") || q.contains("nisab") || q.contains("jagung") || q.contains("padi") || q.contains("panen") || q.contains("maal") || q.contains("profesi")) {
            return AiResponse(
                title = "Bimbingan Perhitungan Zakat Syariah KUA Biringbulu",
                summary = "KUA Biringbulu memfasilitasi konsultasi zakat fitrah, zakat mal, zakat profesi, dan zakat pertanian komoditas lokal (jagung/padi).",
                details = listOf(
                    "• ZAKAT PERTANIAN (PADI/JAGUNG): Nisab 5 wasaq = 653 kg. Jika menggunakan pengairan tadah hujan tarif 10%, jika irigasi berbayar/pompa tarif 5%. Wajib dikeluarkan seketika saat panen.",
                    "• ZAKAT EMAS / MAL: Nisab 85 gram emas murni. Jika telah tersimpan 1 tahun (haul), wajib dikeluarkan 2,5%.",
                    "• ZAKAT PROFESI: Dihitung 2,5% dari pendapatan bersih bulanan jika setara nilai 85 gr emas/12 bulan (~Rp 9.200.000/bln).",
                    "• Penyaluran dapat melalui Unit Pengumpul Zakat (UPZ) KUA Biringbulu untuk diserahkan kepada 8 asnaf di desa setempat."
                ),
                relatedServiceId = 24,
                suggestedQuestions = listOf(
                    "Buka kalkulator zakat otomatis",
                    "Bagaimana cara penyaluran zakat via UPZ KUA?",
                    "Apakah biaya pupuk dan buruh tani mengurangi zakat pertanian?"
                )
            )
        }

        // 7. Sertifikasi Halal / UMKM
        if (q.contains("halal") || q.contains("umkm") || q.contains("sehati") || q.contains("bpjph") || q.contains("makanan") || q.contains("sertifikat halal")) {
            return AiResponse(
                title = "Program Sertifikasi Halal Gratis (SEHATI) di KUA Biringbulu",
                summary = "KUA Biringbulu menyediakan Pendamping Proses Produk Halal (P3H) untuk mendampingi pelaku usaha mikro dan kecil (UMK) hingga terbit sertifikat halal resmi.",
                details = listOf(
                    "1. Kriteria: Usaha mikro/kecil (makanan/minuman olahan seperti keripik, kopi, kue, bumbu) dengan bahan alami halal.",
                    "2. Syarat: Memiliki NIB (Nomor Induk Berusaha), KTP, dan nomor WhatsApp aktif.",
                    "3. Biaya: GRATIS 100% (Program Self Declare BPJPH Kemenag RI).",
                    "4. Pendamping P3H KUA akan mendatangi lokasi usaha untuk verifikasi bahan dan proses produksi higienis."
                ),
                relatedServiceId = 43,
                suggestedQuestions = listOf(
                    "Bagaimana cara membuat NIB bagi pedagang kecil?",
                    "Berapa lama sertifikat halal BPJPH terbit?",
                    "Apakah usaha katering bisa ikut program gratis?"
                )
            )
        }

        // 8. Konsultasi Waris / Mawaris / Faraid
        if (q.contains("waris") || q.contains("faraid") || q.contains("ahli waris") || q.contains("harta peninggalan") || q.contains("sengketa")) {
            return AiResponse(
                title = "Konsultasi Syariah Pembagian Harta Waris (Faraid)",
                summary = "Penghulu dan Tim Syariah KUA Biringbulu siap membantu hisab waris yang adil sesuai Kompilasi Hukum Islam dan fiqih mawaris.",
                details = listOf(
                    "1. Menginventarisasi seluruh harta peninggalan bersih setelah diselesaikan hutang piutang, biaya pemakaman jenazah, dan wasiat (maksimal 1/3).",
                    "2. Menentukan siapa saja ahli waris yang sah (suami/istri, anak laki-laki/perempuan, ayah, ibu).",
                    "3. Penghitungan bagian dzawil furudh (1/2, 1/4, 1/8, 2/3, 1/3, 1/6) dan ashabah sisa harta.",
                    "4. Mengutamakan musyawarah islah (damai) keluarga tanpa merugikan hak anak yatim atau kaum perempuan."
                ),
                relatedServiceId = 44,
                suggestedQuestions = listOf(
                    "Berapa bagian istri jika suami meninggal dan meninggalkan anak?",
                    "Bagaimana hukum anak angkat dalam waris Islam?",
                    "Jadwalkan konsultasi waris tatap muka di KUA"
                )
            )
        }

        // Default General Smart Response
        val matchingServices = KuaServiceData.searchServices(q)
        val firstMatch = matchingServices.firstOrNull()

        return AiResponse(
            title = if (firstMatch != null) "Informasi Layanan: ${firstMatch.title}" else "Asisten Pelayanan KUA Biringbulu",
            summary = if (firstMatch != null) firstMatch.subtitle else "KUA Biringbulu melayani 48 jenis layanan keagamaan secara profesional, transparan, dan berkeadilan untuk seluruh warga Kecamatan Biringbulu.",
            details = if (firstMatch != null) {
                listOf(
                    "Kategori: ${firstMatch.category.title}",
                    "Dasar Hukum: ${firstMatch.legalBasis}",
                    "Estimasi Waktu: ${firstMatch.processingTime}",
                    "Biaya: ${firstMatch.cost}",
                    "Output Dokumen: ${firstMatch.outputDocument}",
                    "Syarat Utama: " + firstMatch.requirements.take(3).joinToString("; ")
                )
            } else {
                listOf(
                    "KUA Biringbulu melayani 9 bidang utama:",
                    "1. Pelayanan Pernikahan (20 Layanan)",
                    "2. Bimbingan Perkawinan & Keluarga Sakinah (3 Layanan)",
                    "3. Zakat dan Wakaf (7 Layanan)",
                    "4. Kemasjidan & SIMAS (6 Layanan)",
                    "5. Data Keagamaan & Majelis Taklim (3 Layanan)",
                    "6. Ketatausahaan & Persuratan (2 Layanan)",
                    "7. Penugasan Menteri & Jaminan Halal (2 Layanan)",
                    "8. Konsultasi Syariah & Kalibrasi Kiblat (2 Layanan)",
                    "9. Penerangan Agama Islam (3 Layanan)"
                )
            },
            relatedServiceId = firstMatch?.id,
            suggestedQuestions = listOf(
                "Apa saja syarat pendaftaran nikah?",
                "Bagaimana cara cek biaya nikah resmi?",
                "Bagaimana cara membuat Akta Ikrar Wakaf (AIW)?",
                "Bagaimana cara pendaftaran ID SIMAS masjid?"
            )
        )
    }
}
