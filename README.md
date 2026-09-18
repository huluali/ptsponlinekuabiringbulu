# KUA Kecamatan Biringbulu - PTSP Digital Web Portal & Android App

Aplikasi Pelayanan Terpadu Satu Pintu (PTSP) Digital Kantor Urusan Agama (KUA) Kecamatan Biringbulu, Kementerian Agama Kabupaten Gowa, Sulawesi Selatan.

Proyek ini telah dikonfigurasi secara **Multi-Platform**:
1. **Versi Web**: Siap langsung di-deploy ke **Vercel** (`index.html`, `main.js`, `app_data.js`, `vercel.json`).
2. **Versi Android Mobile**: Berbasis Jetpack Compose & Room Database (`app/`).

---

## Cara Deploy ke Vercel (Gratis & Cepat)

Anda dapat membuat website ini online dalam 2 menit dengan nama domain gratis seperti:
👉 **`https://kua-biringbulu.vercel.app`**

### Langkah-langkah:
1. **Unduh Kode Proyek**:
   - Di AI Studio, klik menu titik tiga / setting di kanan atas lalu pilih **Export to ZIP** atau **Push to GitHub**.
2. **Hubungkan ke GitHub**:
   - Buka [github.com](https://github.com), buat repository baru (misal: `kua-biringbulu-web`).
   - Upload file proyek ini ke repository tersebut (khususnya file `index.html`, `main.js`, `app_data.js`, `vercel.json`).
3. **Deploy di Vercel**:
   - Buka [vercel.com](https://vercel.com) dan login (bisa login dengan akun GitHub).
   - Klik **"Add New..."** -> **"Project"**.
   - Pilih repository GitHub Anda (`kua-biringbulu-web`).
   - Klik tombol **"Deploy"**.
4. **Selesai!**
   - Vercel akan otomatis memberikan link web resmi publik (misal: `https://kua-biringbulu.vercel.app`) yang bisa diakses oleh warga dan staf KUA dari HP, laptop, dan komputer mana pun.

---

## Fitur-Fitur Utama Web Portal PTSP:

- **48 Standar Pelayanan KUA**:
  - Pelayanan Pernikahan (20 Layanan)
  - Bimbingan Perkawinan & Konseling BP4 (3 Layanan)
  - Zakat dan Wakaf / AIW / APAIW (7 Layanan)
  - Kemasjidan & Nomor ID SIMAS (6 Layanan)
  - Data Keagamaan & Ormas (3 Layanan)
  - Ketatausahaan KUA (2 Layanan)
  - Pendampingan Sertifikasi Halal UMK (2 Layanan)
  - Konsultasi Syariah & Fikih Waris (2 Layanan)
  - Penerangan Agama Islam (3 Layanan)
- **Formulir Pengajuan Online**: Masyarakat dapat mengisi data permohonan dan mengunggah berkas syarat.
- **Sistem Nomor Resi & Pelacakan Berkas**: Melacak status berkas (Diterima, Diproses, Selesai).
- **Data 11 Desa & Kelurahan Biringbulu**: Termasuk Kelurahan Lauwa dan Tonrorita lengkap dengan data imam desa dan masjid.
- **Panel Petugas Loket**: Petugas dapat memverifikasi berkas, mengubah status, dan memberikan catatan.
- **Falakiyah & Hisab Kiblat Biringbulu**: Koordinat geografis presisi (-5.4851, 119.8242) dan azimuth arah kiblat (292° 39').
