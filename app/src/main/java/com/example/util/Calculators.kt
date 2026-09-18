package com.example.util

import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    private val rupiahFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID")).apply {
        maximumFractionDigits = 0
    }

    fun formatRupiah(amount: Double): String {
        return rupiahFormat.format(amount).replace("Rp", "Rp ")
    }

    fun formatNumber(amount: Double): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)
    }
}

object ZakatCalculator {
    const val DEFAULT_GOLD_PRICE_PER_GRAM = 1350000.0 // Estimasi harga emas per gram
    const val NISAB_GOLD_GRAM = 85.0
    const val NISAB_AGRICULTURE_KG = 653.0 // 5 wasaq gabah / jagung kering

    data class ZakatMalResult(
        val totalAssets: Double,
        val nisabAmount: Double,
        val isWajibZakat: Boolean,
        val zakatAmount: Double,
        val explanation: String
    )

    data class ZakatProfesiResult(
        val monthlyIncome: Double,
        val otherIncome: Double,
        val monthlyNeeds: Double,
        val netIncome: Double,
        val monthlyNisab: Double,
        val isWajibZakat: Boolean,
        val zakatAmount: Double,
        val explanation: String
    )

    data class ZakatPertanianResult(
        val harvestYieldKg: Double,
        val pricePerKg: Double,
        val totalValue: Double,
        val irrigationType: String, // "ALAMI_10" atau "IRIGASI_5"
        val isWajibZakat: Boolean,
        val zakatKg: Double,
        val zakatRupiah: Double,
        val explanation: String
    )

    fun calculateZakatMal(
        cashAndSavings: Double,
        goldAndJewelry: Double,
        investments: Double,
        debtsDue: Double,
        goldPricePerGram: Double = DEFAULT_GOLD_PRICE_PER_GRAM
    ): ZakatMalResult {
        val totalAssets = (cashAndSavings + goldAndJewelry + investments) - debtsDue
        val effectiveAssets = maxOf(0.0, totalAssets)
        val nisabAmount = NISAB_GOLD_GRAM * goldPricePerGram
        val isWajib = effectiveAssets >= nisabAmount
        val zakatAmount = if (isWajib) effectiveAssets * 0.025 else 0.0

        val explanation = if (isWajib) {
            "Total harta bersih (${FormatUtils.formatRupiah(effectiveAssets)}) telah melampaui nisab 85 gr emas (${FormatUtils.formatRupiah(nisabAmount)}). Wajib dikeluarkan zakat mal sebesar 2,5%."
        } else {
            "Total harta bersih (${FormatUtils.formatRupiah(effectiveAssets)}) belum mencapai batas nisab 85 gr emas (${FormatUtils.formatRupiah(nisabAmount)}). Dianjurkan memperbanyak infak/sedekah."
        }

        return ZakatMalResult(effectiveAssets, nisabAmount, isWajib, zakatAmount, explanation)
    }

    fun calculateZakatProfesi(
        monthlySalary: Double,
        additionalIncome: Double,
        basicNeeds: Double,
        goldPricePerGram: Double = DEFAULT_GOLD_PRICE_PER_GRAM
    ): ZakatProfesiResult {
        val netIncome = (monthlySalary + additionalIncome) - basicNeeds
        val effectiveNet = maxOf(0.0, netIncome)
        val monthlyNisab = (NISAB_GOLD_GRAM * goldPricePerGram) / 12.0
        val isWajib = effectiveNet >= monthlyNisab
        val zakatAmount = if (isWajib) effectiveNet * 0.025 else 0.0

        val explanation = if (isWajib) {
            "Penghasilan bersih per bulan (${FormatUtils.formatRupiah(effectiveNet)}) telah memenuhi nisab bulanan (${FormatUtils.formatRupiah(monthlyNisab)}). Wajib ditunaikan 2,5% setiap menerima gaji/honor."
        } else {
            "Penghasilan bersih (${FormatUtils.formatRupiah(effectiveNet)}) belum mencapai nisab bulanan setara emas (${FormatUtils.formatRupiah(monthlyNisab)})."
        }

        return ZakatProfesiResult(monthlySalary, additionalIncome, basicNeeds, effectiveNet, monthlyNisab, isWajib, zakatAmount, explanation)
    }

    fun calculateZakatPertanian(
        yieldKg: Double,
        pricePerKg: Double,
        isUsingPaidIrrigation: Boolean
    ): ZakatPertanianResult {
        val totalValue = yieldKg * pricePerKg
        val isWajib = yieldKg >= NISAB_AGRICULTURE_KG
        val rate = if (isUsingPaidIrrigation) 0.05 else 0.10
        val ratePercentage = if (isUsingPaidIrrigation) "5% (Pengairan Irigasi/Pompa Berbayar)" else "10% (Tadah Hujan / Alami)"
        val zakatKg = if (isWajib) yieldKg * rate else 0.0
        val zakatRupiah = zakatKg * pricePerKg

        val explanation = if (isWajib) {
            "Hasil panen ($yieldKg kg) telah melebihi nisab 5 wasaq (653 kg). Kadar zakat yang wajib dikeluarkan adalah $ratePercentage seketika saat panen tanpa menunggu haul 1 tahun."
        } else {
            "Hasil panen ($yieldKg kg) belum mencapai nisab 5 wasaq (653 kg gabah/jagung kering). Tetap disunnahkan mengeluarkan sedekah sebagian hasil bumi."
        }

        return ZakatPertanianResult(
            harvestYieldKg = yieldKg,
            pricePerKg = pricePerKg,
            totalValue = totalValue,
            irrigationType = ratePercentage,
            isWajibZakat = isWajib,
            zakatKg = zakatKg,
            zakatRupiah = zakatRupiah,
            explanation = explanation
        )
    }
}

object PnbpCalculator {
    data class MarriageFeeResult(
        val location: String,
        val dayType: String,
        val totalCost: Double,
        val paymentMethod: String,
        val requiredDocuments: List<String>,
        val tips: String
    )

    fun calculateMarriageCost(
        isAtKuaOffice: Boolean,
        isWorkHours: Boolean,
        hasSktm: Boolean // Surat Keterangan Tidak Mampu
    ): MarriageFeeResult {
        if (hasSktm) {
            return MarriageFeeResult(
                location = if (isAtKuaOffice) "Balai Nikah KUA Biringbulu" else "Luar Balai Nikah (Rumah/Masjid)",
                dayType = "Bebas Biaya Khusus (Fasilitas Negara)",
                totalCost = 0.0,
                paymentMethod = "Rp 0,- (GRATIS) dengan melampirkan SKTM resmi dari Kepala Desa di Biringbulu",
                requiredDocuments = listOf(
                    "Surat Keterangan Tidak Mampu (SKTM) asli dari Kepala Desa",
                    "Rekomendasi dari Camat Biringbulu",
                    "Berkas N1 s.d N4 lengkap",
                    "KTP & Kartu Keluarga Terdaftar DTKS/Kemensos"
                ),
                tips = "Sesuai PP No. 59/2014 Pasal 4 ayat (2), warga tidak mampu berhak mendapatkan layanan nikah 0 rupiah baik di dalam maupun di luar KUA."
            )
        }

        if (isAtKuaOffice && isWorkHours) {
            return MarriageFeeResult(
                location = "Balai Nikah KUA Biringbulu",
                dayType = "Hari Kerja (Senin - Jumat, Jam 07.30 - 16.00 WITA)",
                totalCost = 0.0,
                paymentMethod = "Rp 0,- (GRATIS / Bebas Biaya PNBP)",
                requiredDocuments = listOf(
                    "Berkas Model N1, N4, N5 dari Kantor Desa",
                    "Fotokopi KTP, KK, Akta Lahir/Ijazah Calon Mempelai",
                    "Pasfoto latar biru 2x3 (4 lbr) dan 4x6 (2 lbr)",
                    "Surat Pemeriksaan Kesehatan Puskesmas"
                ),
                tips = "Layanan di Balai Nikah KUA dijamin 100% GRATIS dan bebas dari segala bentuk pungli atau biaya transportasi."
            )
        }

        return MarriageFeeResult(
            location = if (isAtKuaOffice) "Balai Nikah KUA (Hari Libur)" else "Luar Balai Nikah (Rumah Mempelai / Masjid Desa)",
            dayType = if (isAtKuaOffice) "Sabtu / Minggu / Hari Libur Nasional" else "Luar Balai Nikah (Kapan Saja)",
            totalCost = 600000.0,
            paymentMethod = "Setor Tunai/Transfer ke Kas Negara via Kode Billing SIMKAH (Bank Mandiri, BRI, BNI, BSI, Kantor Pos, Tokopedia)",
            requiredDocuments = listOf(
                "Berkas Model N1 - N5 lengkap dari Desa",
                "Bukti Cetak Lembar Tagihan Kode Billing SIMKAH Kemenag",
                "Bukti Resi Bayar Asli (Validasi NTPN Kas Negara) Rp 600.000,-",
                "Surat Pernyataan Persetujuan Akad di Luar KUA"
            ),
            tips = "Biaya Rp 600.000 disetorkan langsung ke Kas Negara secara non-tunai. Penghulu KUA dilarang menerima uang secara langsung di lokasi akad."
        )
    }
}
