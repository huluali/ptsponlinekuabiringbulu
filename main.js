// Main Application Logic for KUA Biringbulu Web Portal
let currentPage = 'home';
let selectedCategory = 'ALL';
let currentSearch = '';

// LocalStorage Helper for Applications
function getStoredApplications() {
  try {
    const raw = localStorage.getItem('kua_biringbulu_apps');
    if (!raw) {
      // Data percontohan awal agar halaman lacak dan admin langsung terisi
      const initial = [
        {
          id: 'BB-2026-98124',
          serviceId: 1,
          serviceTitle: 'Pendaftaran Kehendak Nikah',
          applicantName: 'Muhammad Fadly S.',
          nik: '7306041205980001',
          phone: '081244556677',
          village: 'Kelurahan Lauwa',
          address: 'Lingkungan Lauwa Selatan RT 02 / RW 01',
          notes: 'Rencana akad di Balai Nikah KUA Biringbulu hari Senin pagi',
          status: 'PROSES', // 'PENDING', 'PROSES', 'SELESAI', 'DITOLAK'
          createdAt: new Date(Date.now() - 3600000 * 24 * 2).toISOString(),
          officerNotes: 'Berkas N1-N4 lengkap, siap pemeriksaan fisik catin & wali.',
          uploadedDocs: [
            { name: 'Surat N1-N4 Kelurahan Lauwa.pdf', size: '1.2 MB' },
            { name: 'KTP & KK Catin.pdf', size: '850 KB' }
          ]
        },
        {
          id: 'BB-2026-51209',
          serviceId: 26,
          serviceTitle: 'Penerbitan ID Masjid / Musala (SIMAS)',
          applicantName: 'H. Syamsuddin Dg. Ngawing',
          nik: '7306041908650002',
          phone: '081355667788',
          village: 'Kelurahan Lauwa',
          address: 'Masjid Jami Nurul Falah Lauwa',
          notes: 'Permohonan update nomor ID SIMAS dan verifikasi tipologi masjid',
          status: 'SELESAI',
          createdAt: new Date(Date.now() - 3600000 * 24 * 5).toISOString(),
          officerNotes: 'ID SIMAS resmi telah terbit: 01.4.26.06.01.000015. Sertifikat siap diambil di kantor.',
          uploadedDocs: [
            { name: 'SK Pengurus Takmir & Foto Masjid.pdf', size: '2.4 MB' }
          ]
        }
      ];
      localStorage.setItem('kua_biringbulu_apps', JSON.stringify(initial));
      return initial;
    }
    return JSON.parse(raw);
  } catch (e) {
    return [];
  }
}

function saveApplications(apps) {
  localStorage.setItem('kua_biringbulu_apps', JSON.stringify(apps));
}

// IKM Survey Storage Management
function getStoredIkmSurveys() {
  try {
    const raw = localStorage.getItem('kua_biringbulu_ikm');
    if (!raw) {
      const initial = [
        {
          id: 1,
          name: "H. Baso Dg. Sitaba",
          phone: "0812-4112-xxxx",
          service: "Pendaftaran Nikah (SIMKAH)",
          village: "Tonrorita",
          overall: 5,
          requirements: 5,
          procedure: 5,
          speed: 5,
          cost: 5,
          staff: 5,
          facility: 5,
          feedback: "Pelayanan nikah di Balai KUA sangat memuaskan, benar-benar Rp 0,- tanpa biaya tambahan sepeser pun. Penghulu dan staf sangat ramah dan sopan.",
          date: "16 September 2026, 10:15"
        },
        {
          id: 2,
          name: "Nurhaeni, S.Pd.",
          phone: "0852-9844-xxxx",
          service: "Surat Rekomendasi Nikah (N10)",
          village: "Kelurahan Lauwa",
          overall: 5,
          requirements: 5,
          procedure: 5,
          speed: 5,
          cost: 5,
          staff: 5,
          facility: 4,
          feedback: "Proses rekomendasi nikah luar daerah sangat cepat, berkas diverifikasi secara digital dan langsung selesai dalam 15 menit. Luar biasa PTSP KUA Biringbulu!",
          date: "14 September 2026, 11:30"
        },
        {
          id: 3,
          name: "Dg. Mangngassai",
          phone: "0813-5520-xxxx",
          service: "Akta Ikrar Wakaf (AIW)",
          village: "Baturappe",
          overall: 5,
          requirements: 5,
          procedure: 5,
          speed: 4,
          cost: 5,
          staff: 5,
          facility: 5,
          feedback: "Pengurusan akta wakaf tanah masjid dibimbing dengan sangat sabar oleh PPAIW KUA. Tidak ada pungutan liar, semuanya transparan dan jelas.",
          date: "11 September 2026, 09:45"
        },
        {
          id: 4,
          name: "Rahmat Hidayat (Catin)",
          phone: "0821-8733-xxxx",
          service: "Bimbingan Catin (Bimwin)",
          village: "Pencong",
          overall: 5,
          requirements: 5,
          procedure: 5,
          speed: 5,
          cost: 5,
          staff: 5,
          facility: 5,
          feedback: "Materi bimbingan perkawinan sangat bermanfaat bagi bekal rumah tangga kami. Fasilitator dan narasumber menyampaikan materi dengan interaktif dan menyenangkan.",
          date: "09 September 2026, 14:00"
        },
        {
          id: 5,
          name: "Ust. Syarifuddin",
          phone: "0813-4299-xxxx",
          service: "Penerbitan ID SIMAS Masjid",
          village: "Berutallasa",
          overall: 5,
          requirements: 5,
          procedure: 4,
          speed: 5,
          cost: 5,
          staff: 5,
          facility: 4,
          feedback: "Sertifikat ID SIMAS Nasional langsung terbit dan data masjid langsung sinkron ke Kemenag Pusat. Pelayanan sangat responsif dan amanah.",
          date: "06 September 2026, 10:20"
        }
      ];
      localStorage.setItem('kua_biringbulu_ikm', JSON.stringify(initial));
      return initial;
    }
    return JSON.parse(raw);
  } catch (e) {
    return [];
  }
}

function saveIkmSurveys(surveys) {
  localStorage.setItem('kua_biringbulu_ikm', JSON.stringify(surveys));
}

// Navigation Handler
function navigateTo(page, param) {
  currentPage = page;
  window.scrollTo({ top: 0, behavior: 'smooth' });

  // Update Nav Button States
  document.querySelectorAll('.nav-btn').forEach(btn => {
    if (btn.dataset.page === page) {
      btn.classList.add('bg-emerald-800/90', 'text-white');
      btn.classList.remove('text-emerald-200');
    } else {
      btn.classList.remove('bg-emerald-800/90', 'text-white');
      btn.classList.add('text-emerald-200');
    }
  });

  const content = document.getElementById('appContent');
  if (!content) return;

  switch (page) {
    case 'home':
      renderHomePage(content);
      break;
    case 'services':
      renderServicesPage(content, param);
      break;
    case 'track':
      renderTrackingPage(content, param);
      break;
    case 'syariah':
      renderSyariahPage(content);
      break;
    case 'ikm':
      renderIkmPage(content, param);
      break;
    case 'profile':
      renderProfilePage(content);
      break;
    case 'admin':
      renderAdminPage(content);
      break;
    default:
      renderHomePage(content);
  }

  // Refresh icons
  if (window.lucide) {
    window.lucide.createIcons();
  }
}

function toggleMobileMenu() {
  const menu = document.getElementById('mobileMenu');
  if (menu) {
    menu.classList.toggle('hidden');
  }
}

// ==========================================
// 1. BERANDA (HOME PAGE)
// ==========================================
function renderHomePage(container) {
  const allServices = window.KUA_SERVICES || [];
  const categories = [
    { key: 'PELAYANAN_PERNIKAHAN', name: 'Pernikahan', count: 20, icon: 'heart', desc: 'Daftar Nikah, Duplikat, Legalisasi & Dispensasi' },
    { key: 'BIMBINGAN_PERKAWINAN', name: 'Bimwin & Konseling', count: 3, icon: 'users', desc: 'Bimwin Catin, Keluarga Sakinah & BP4' },
    { key: 'ZAKAT_DAN_WAKAF', name: 'Zakat & Wakaf', count: 7, icon: 'gift', desc: 'Akta Ikrar Wakaf (AIW), SIWAK & Nazhir' },
    { key: 'KEMASJIDAN', name: 'Kemasjidan (SIMAS)', count: 6, icon: 'landmark', desc: 'Nomor ID SIMAS & Rekomendasi Bantuan' },
    { key: 'KONSULTASI_SYARIAH', name: 'Syariah & Kiblat', count: 2, icon: 'compass', desc: 'Konsultasi Fikih Waris & Kalibrasi Kiblat' },
    { key: 'FUNGSI_TAMBAHAN', name: 'Produk Halal', count: 2, icon: 'badge-check', desc: 'Pendampingan Sertifikasi Halal UMK Gratis' }
  ];

  container.innerHTML = `
    <!-- Hero Banner -->
    <div class="relative overflow-hidden rounded-3xl bg-gradient-to-br from-emerald-900 via-emerald-800 to-teal-900 text-white shadow-2xl p-8 sm:p-12 mb-10 border border-emerald-700/50">
      <div class="absolute -right-20 -bottom-20 w-80 h-80 bg-emerald-500/10 rounded-full blur-3xl pointer-events-none"></div>
      <div class="max-w-3xl relative z-10">
        <div class="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-emerald-700/60 border border-emerald-500/30 text-emerald-200 text-xs font-semibold uppercase tracking-wider mb-4">
          <span class="w-2 h-2 rounded-full bg-amber-400"></span>
          Pelayanan Terpadu Satu Pintu (PTSP) Online
        </div>
        <h2 class="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight leading-tight mb-4">
          Layanan KUA Biringbulu Kini Lebih Cepat, Terbuka, & Bebas Pungli
        </h2>
        <p class="text-emerald-100/90 text-sm sm:text-base leading-relaxed mb-8">
          Akses 48 Standar Pelayanan KUA Kecamatan Biringbulu dari rumah. Ajukan permohonan nikah, kemasjidan SIMAS, legalitas tanah wakaf, sertifikat halal, hingga konsultasi syariah.
        </p>

        <!-- Quick Action Buttons -->
        <div class="flex flex-wrap items-center gap-3">
          <button onclick="navigateTo('services')" class="px-6 py-3.5 rounded-2xl bg-amber-400 text-emerald-950 font-bold text-sm shadow-lg hover:bg-amber-300 transition flex items-center gap-2 transform active:scale-95">
            <i data-lucide="layers" class="w-4 h-4"></i> Lihat 48 Layanan PTSP
          </button>
          <button onclick="navigateTo('track')" class="px-6 py-3.5 rounded-2xl bg-emerald-700/80 hover:bg-emerald-700 text-white font-semibold text-sm border border-emerald-500/40 transition flex items-center gap-2">
            <i data-lucide="search" class="w-4 h-4"></i> Lacak Status Berkas
          </button>
        </div>
      </div>
    </div>

    <!-- Quick Search Bar -->
    <div class="bg-white rounded-2xl shadow-md border border-slate-200/80 p-4 mb-10 flex flex-col md:flex-row items-center gap-4">
      <div class="relative flex-grow w-full">
        <i data-lucide="search" class="w-5 h-5 absolute left-4 top-3.5 text-slate-400"></i>
        <input type="text" id="quickSearchInput" placeholder="Cari layanan (misal: Kehendak Nikah, Rekomendasi, ID SIMAS, Wakaf, Duplikat)..." 
          class="w-full pl-12 pr-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-600 focus:border-transparent text-sm"
          onkeypress="if(event.key === 'Enter') handleQuickSearch()">
      </div>
      <button onclick="handleQuickSearch()" class="w-full md:w-auto px-6 py-3 rounded-xl bg-emerald-700 text-white font-semibold text-sm hover:bg-emerald-800 transition flex items-center justify-center gap-2">
        <i data-lucide="arrow-right" class="w-4 h-4"></i> Temukan Layanan
      </button>
    </div>

    <!-- 6 Kategori Unggulan -->
    <div class="mb-12">
      <div class="flex justify-between items-end mb-6">
        <div>
          <h3 class="text-xl font-bold text-slate-900">Kategori Pelayanan PTSP</h3>
          <p class="text-xs text-slate-500 mt-1">Pilih rumpun layanan sesuai kebutuhan permohonan Anda</p>
        </div>
        <button onclick="navigateTo('services')" class="text-xs font-semibold text-emerald-700 hover:text-emerald-800 flex items-center gap-1">
          Lihat Semua 48 Layanan <i data-lucide="chevron-right" class="w-4 h-4"></i>
        </button>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        ${categories.map(cat => `
          <div onclick="navigateTo('services', '${cat.key}')" class="group bg-white rounded-2xl p-6 border border-slate-200/80 shadow-sm hover:shadow-md hover:border-emerald-500/50 transition cursor-pointer flex flex-col justify-between">
            <div>
              <div class="w-12 h-12 rounded-xl bg-emerald-50 text-emerald-700 flex items-center justify-center mb-4 group-hover:bg-emerald-700 group-hover:text-white transition">
                <i data-lucide="${cat.icon}" class="w-6 h-6"></i>
              </div>
              <div class="flex items-center justify-between gap-2 mb-1">
                <h4 class="font-bold text-slate-800 group-hover:text-emerald-700 transition">${cat.name}</h4>
                <span class="text-[11px] font-bold px-2.5 py-0.5 rounded-full bg-slate-100 text-slate-600">${cat.count} Layanan</span>
              </div>
              <p class="text-xs text-slate-500 leading-relaxed mb-4">${cat.desc}</p>
            </div>
            <div class="text-xs font-semibold text-emerald-700 flex items-center gap-1 pt-3 border-t border-slate-100">
              Ajukan Permohonan <i data-lucide="arrow-right" class="w-3.5 h-3.5 group-hover:translate-x-1 transition"></i>
            </div>
          </div>
        `).join('')}
      </div>
    </div>

    <!-- Survei IKM Berbintang Banner -->
    <div class="mb-12 bg-gradient-to-r from-red-950 via-rose-900 to-amber-950 rounded-3xl p-6 sm:p-8 text-white shadow-xl border border-rose-800/50 relative overflow-hidden">
      <div class="absolute -right-16 -top-16 w-60 h-60 bg-amber-500/10 rounded-full blur-2xl pointer-events-none"></div>
      <div class="relative z-10 flex flex-col lg:flex-row items-center justify-between gap-6">
        <div class="max-w-2xl">
          <div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-rose-800/80 border border-rose-600/40 text-amber-200 text-xs font-bold uppercase tracking-wider mb-3">
            <i data-lucide="star" class="w-3.5 h-3.5 fill-amber-300 text-amber-300"></i>
            Indeks Kepuasan Masyarakat (IKM) Resmi
          </div>
          <h3 class="text-2xl sm:text-3xl font-extrabold leading-tight text-white mb-2">
            Survei IKM Berbintang KUA Biringbulu
          </h3>
          <p class="text-xs sm:text-sm text-rose-100/90 leading-relaxed mb-4">
            Bantu kami menjaga komitmen Wilayah Bebas dari Korupsi (WBK) dan pelayanan prima. Berikan penilaian bintang dan ulasan objektif atas 48 standar pelayanan KUA Kecamatan Biringbulu.
          </p>
          <div class="flex flex-wrap items-center gap-4 text-xs font-semibold text-rose-200">
            <span class="flex items-center gap-1.5 bg-black/25 px-3 py-1.5 rounded-xl border border-white/10">
              <span class="text-amber-400 font-bold">⭐ 4.96 / 5.0</span> (Mutu A: Sangat Baik)
            </span>
            <span class="flex items-center gap-1.5 bg-black/25 px-3 py-1.5 rounded-xl border border-white/10">
              <i data-lucide="shield-check" class="w-4 h-4 text-emerald-400"></i> Standar PermenPAN-RB No. 14/2017
            </span>
          </div>
        </div>
        <div class="flex flex-col sm:flex-row lg:flex-col gap-3 w-full lg:w-auto shrink-0">
          <button onclick="navigateTo('ikm', { activeTab: 1 })" class="px-6 py-3.5 rounded-2xl bg-amber-400 hover:bg-amber-300 text-emerald-950 font-bold text-sm shadow-lg transition flex items-center justify-center gap-2 transform active:scale-95">
            <i data-lucide="pencil" class="w-4 h-4"></i> Isi Survei IKM Sekarang
          </button>
          <button onclick="navigateTo('ikm', { activeTab: 0 })" class="px-6 py-3.5 rounded-2xl bg-white/10 hover:bg-white/20 text-white font-semibold text-sm border border-white/20 transition flex items-center justify-center gap-2">
            <i data-lucide="bar-chart-3" class="w-4 h-4"></i> Lihat Hasil & Statistik
          </button>
        </div>
      </div>
    </div>

    <!-- Maklumat & Biaya Resmi -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-12">
      <!-- Biaya Nikah Transparan -->
      <div class="bg-gradient-to-br from-white to-emerald-50/50 rounded-2xl p-6 border border-emerald-200 shadow-sm">
        <div class="flex items-center gap-3 mb-4">
          <div class="w-10 h-10 rounded-xl bg-emerald-600 text-white flex items-center justify-center">
            <i data-lucide="shield-alert" class="w-5 h-5"></i>
          </div>
          <div>
            <h4 class="font-bold text-slate-900">Transparansi Biaya Nikah (PMA 20/2019)</h4>
            <p class="text-xs text-slate-500">Kemenag berkomitmen bebas gratifikasi dan pungli</p>
          </div>
        </div>
        <div class="space-y-3">
          <div class="bg-white p-4 rounded-xl border border-slate-200 flex justify-between items-center">
            <div>
              <p class="text-xs font-bold text-slate-800">Nikah di Balai Nikah KUA</p>
              <p class="text-[11px] text-slate-500">Pada hari dan jam kerja dinas</p>
            </div>
            <span class="px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 font-extrabold text-xs">Rp 0,- (GRATIS)</span>
          </div>
          <div class="bg-white p-4 rounded-xl border border-slate-200 flex justify-between items-center">
            <div>
              <p class="text-xs font-bold text-slate-800">Nikah di Luar KUA / Hari Libur</p>
              <p class="text-[11px] text-slate-500">Disetor langsung ke Kas Negara via SIMKAH</p>
            </div>
            <span class="px-3 py-1 rounded-full bg-amber-100 text-amber-900 font-extrabold text-xs">Rp 600.000,- (PNBP)</span>
          </div>
        </div>
      </div>

      <!-- Hotline & Bantuan Cepat -->
      <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm flex flex-col justify-between">
        <div>
          <div class="flex items-center gap-3 mb-4">
            <div class="w-10 h-10 rounded-xl bg-teal-600 text-white flex items-center justify-center">
              <i data-lucide="message-square" class="w-5 h-5"></i>
            </div>
            <div>
              <h4 class="font-bold text-slate-900">Bantuan & Konsultasi Online</h4>
              <p class="text-xs text-slate-500">Hubungi petugas PTSP KUA Biringbulu</p>
            </div>
          </div>
          <p class="text-xs text-slate-600 leading-relaxed mb-4">
            Butuh bantuan pengisian formulir, info berkas calon pengantin, atau konsultasi zakat & waris? Petugas kami siap melayani Anda.
          </p>
        </div>
        <div class="flex flex-wrap gap-2">
          <a href="https://wa.me/6281241193634" target="_blank" class="flex-grow text-center py-2.5 px-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-semibold text-xs transition flex items-center justify-center gap-2">
            <i data-lucide="phone" class="w-4 h-4"></i> Hubungi WhatsApp Petugas
          </a>
          <button onclick="navigateTo('profile')" class="py-2.5 px-4 rounded-xl bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold text-xs transition">
            Daftar Petugas & Desa
          </button>
        </div>
      </div>
    </div>
  `;
}

function handleQuickSearch() {
  const input = document.getElementById('quickSearchInput');
  if (input && input.value.trim()) {
    currentSearch = input.value.trim();
    navigateTo('services');
  }
}

// ==========================================
// 2. DAFTAR 48 LAYANAN (SERVICES PAGE)
// ==========================================
function renderServicesPage(container, targetCategory) {
  if (targetCategory) {
    selectedCategory = targetCategory;
  }
  const allServices = window.KUA_SERVICES || [];

  const categoryNames = {
    'ALL': 'Semua (48 Layanan)',
    'PELAYANAN_PERNIKAHAN': 'Pernikahan (20)',
    'BIMBINGAN_PERKAWINAN': 'Bimwin & Konseling (3)',
    'ZAKAT_DAN_WAKAF': 'Zakat & Wakaf (7)',
    'KEMASJIDAN': 'Kemasjidan SIMAS (6)',
    'DATA_KEAGAMAAN': 'Data Keagamaan (3)',
    'KETATAUSAHAAN': 'Tata Usaha (2)',
    'FUNGSI_TAMBAHAN': 'Produk Halal & Konflik (2)',
    'KONSULTASI_SYARIAH': 'Syariah & Kiblat (2)',
    'PENERANGAN_ISLAM': 'Penerangan Agama (3)'
  };

  // Filter Data
  let filtered = allServices.filter(s => {
    const matchCat = selectedCategory === 'ALL' || s.category === selectedCategory;
    const matchQuery = !currentSearch || (
      s.title.toLowerCase().includes(currentSearch.toLowerCase()) ||
      s.subtitle.toLowerCase().includes(currentSearch.toLowerCase()) ||
      s.outputDocument.toLowerCase().includes(currentSearch.toLowerCase()) ||
      (s.requirements && s.requirements.some(r => r.toLowerCase().includes(currentSearch.toLowerCase())))
    );
    return matchCat && matchQuery;
  });

  container.innerHTML = `
    <div class="mb-8">
      <h2 class="text-2xl font-extrabold text-slate-900">Daftar 48 Standar Layanan PTSP KUA</h2>
      <p class="text-xs text-slate-500 mt-1">Pilih salah satu layanan untuk melihat rincian syarat, alur prosedur, dasar hukum, dan mengirim pengajuan formulir secara online.</p>
    </div>

    <!-- Filter & Search Controls -->
    <div class="bg-white rounded-2xl p-4 shadow-sm border border-slate-200 mb-8 space-y-4">
      <div class="relative">
        <i data-lucide="search" class="w-4 h-4 absolute left-4 top-3.5 text-slate-400"></i>
        <input type="text" id="servicesSearchInput" value="${currentSearch}" placeholder="Ketik kata kunci layanan, syarat dokumen, atau berkas..."
          class="w-full pl-11 pr-4 py-2.5 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-600 text-xs"
          oninput="onSearchServices(this.value)">
      </div>

      <!-- Categories Filter Tabs -->
      <div class="flex items-center gap-2 overflow-x-auto custom-scrollbar pb-2">
        ${Object.keys(categoryNames).map(catKey => `
          <button onclick="filterCategory('${catKey}')" class="px-3.5 py-1.5 rounded-xl text-xs font-semibold whitespace-nowrap transition ${selectedCategory === catKey ? 'bg-emerald-800 text-white shadow-sm' : 'bg-slate-100 text-slate-600 hover:bg-slate-200'}">
            ${categoryNames[catKey]}
          </button>
        `).join('')}
      </div>
    </div>

    <!-- Result List -->
    <div class="flex justify-between items-center mb-4">
      <span class="text-xs font-semibold text-slate-500">Menampilkan ${filtered.length} dari 48 Layanan</span>
      ${currentSearch ? `<button onclick="clearSearch()" class="text-xs text-red-600 hover:underline">Hapus Pencarian</button>` : ''}
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
      ${filtered.map(s => `
        <div class="bg-white rounded-2xl p-5 border border-slate-200/90 shadow-sm hover:shadow-md hover:border-emerald-500/40 transition flex flex-col justify-between">
          <div>
            <div class="flex items-center justify-between gap-2 mb-2">
              <span class="inline-flex items-center px-2 py-0.5 rounded text-[10px] font-bold bg-emerald-50 text-emerald-700 border border-emerald-200/60">
                #${s.id}
              </span>
              <span class="text-[10px] font-medium text-slate-500 flex items-center gap-1">
                <i data-lucide="clock" class="w-3 h-3 text-slate-400"></i> ${s.processingTime || '1 Hari Kerja'}
              </span>
            </div>
            <h4 class="font-bold text-slate-900 text-sm leading-snug mb-1.5 hover:text-emerald-700 transition cursor-pointer" onclick="openServiceDetail(${s.id})">
              ${s.title}
            </h4>
            <p class="text-xs text-slate-500 leading-relaxed line-clamp-2 mb-4">${s.subtitle}</p>

            <div class="bg-slate-50 rounded-xl p-3 border border-slate-100 text-[11px] text-slate-600 mb-4 space-y-1">
              <div class="flex items-center gap-1.5">
                <span class="font-semibold text-slate-700">Biaya:</span>
                <span class="text-emerald-700 font-bold">${s.cost}</span>
              </div>
              <div class="flex items-center gap-1.5">
                <span class="font-semibold text-slate-700">Output:</span>
                <span class="truncate">${s.outputDocument}</span>
              </div>
            </div>
          </div>

          <div class="flex items-center gap-2 pt-3 border-t border-slate-100">
            <button onclick="openServiceDetail(${s.id})" class="flex-1 py-2 rounded-xl bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold text-xs transition">
              Detail & Syarat
            </button>
            <button onclick="openApplicationForm(${s.id})" class="flex-1 py-2 rounded-xl bg-emerald-700 hover:bg-emerald-800 text-white font-semibold text-xs transition flex items-center justify-center gap-1 shadow-sm">
              <i data-lucide="send" class="w-3 h-3"></i> Ajukan
            </button>
          </div>
        </div>
      `).join('')}
    </div>
  `;
}

function filterCategory(cat) {
  selectedCategory = cat;
  renderServicesPage(document.getElementById('appContent'));
  if (window.lucide) window.lucide.createIcons();
}

function onSearchServices(val) {
  currentSearch = val;
  renderServicesPage(document.getElementById('appContent'));
  if (window.lucide) window.lucide.createIcons();
}

function clearSearch() {
  currentSearch = '';
  renderServicesPage(document.getElementById('appContent'));
  if (window.lucide) window.lucide.createIcons();
}

// Modal Detail Layanan
function openServiceDetail(serviceId) {
  const service = (window.KUA_SERVICES || []).find(s => s.id === serviceId);
  if (!service) return;

  const modal = document.getElementById('serviceModal');
  const header = document.getElementById('modalHeader');
  const body = document.getElementById('modalBody');

  header.innerHTML = `
    <div>
      <span class="text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded bg-emerald-800/80 text-emerald-200">
        Layanan #${service.id}
      </span>
      <h3 class="text-xl font-bold mt-1 text-white">${service.title}</h3>
      <p class="text-xs text-emerald-200 mt-0.5">${service.subtitle}</p>
    </div>
    <button onclick="closeModal()" class="text-emerald-300 hover:text-white p-1 rounded-lg">
      <i data-lucide="x" class="w-6 h-6"></i>
    </button>
  `;

  body.innerHTML = `
    <!-- Ringkasan Info -->
    <div class="grid grid-cols-2 sm:grid-cols-4 gap-3 bg-slate-50 p-4 rounded-2xl border border-slate-200 text-xs">
      <div>
        <span class="text-slate-400 block text-[10px] uppercase font-bold">Biaya Resmi</span>
        <span class="font-bold text-emerald-700">${service.cost}</span>
      </div>
      <div>
        <span class="text-slate-400 block text-[10px] uppercase font-bold">Waktu Proses</span>
        <span class="font-bold text-slate-800">${service.processingTime}</span>
      </div>
      <div class="col-span-2">
        <span class="text-slate-400 block text-[10px] uppercase font-bold">Dokumen Output</span>
        <span class="font-bold text-slate-800">${service.outputDocument}</span>
      </div>
    </div>

    <!-- Dasar Hukum -->
    <div class="p-3 bg-amber-50/70 border border-amber-200 rounded-xl text-xs text-amber-900">
      <strong class="block mb-0.5">Dasar Hukum:</strong>
      ${service.legalBasis || 'Peraturan Menteri Agama No. 20 Tahun 2019'}
    </div>

    <!-- Syarat Dokumen -->
    <div>
      <h4 class="font-bold text-slate-900 text-sm mb-2 flex items-center gap-2">
        <i data-lucide="check-square" class="w-4 h-4 text-emerald-700"></i> Persyaratan Dokumen
      </h4>
      <ul class="space-y-1.5 text-xs text-slate-600 bg-slate-50 p-4 rounded-2xl border border-slate-100">
        ${(service.requirements || []).map(r => `
          <li class="flex items-start gap-2">
            <span class="text-emerald-700 font-bold">•</span>
            <span>${r}</span>
          </li>
        `).join('')}
      </ul>
    </div>

    <!-- Alur Prosedur -->
    <div>
      <h4 class="font-bold text-slate-900 text-sm mb-2 flex items-center gap-2">
        <i data-lucide="list-ordered" class="w-4 h-4 text-emerald-700"></i> Alur & Prosedur Pelayanan
      </h4>
      <ol class="space-y-2 text-xs text-slate-600">
        ${(service.procedures || []).map((p, idx) => `
          <li class="flex items-start gap-2.5">
            <span class="w-5 h-5 rounded-full bg-emerald-100 text-emerald-800 font-bold text-[10px] flex items-center justify-center shrink-0 mt-0.5">${idx + 1}</span>
            <span>${p}</span>
          </li>
        `).join('')}
      </ol>
    </div>

    <!-- Tombol Aksi -->
    <div class="pt-4 flex flex-wrap items-center justify-between gap-3 border-t border-slate-200">
      <button onclick="closeModal(); navigateTo('ikm', { prefilledService: '${service.title}', activeTab: 1 })" class="px-4 py-2.5 rounded-xl bg-amber-50 hover:bg-amber-100 text-amber-900 border border-amber-300 font-bold text-xs transition flex items-center gap-1.5">
        <i data-lucide="star" class="w-4 h-4 text-amber-500 fill-amber-400"></i> Beri Nilai Layanan (IKM)
      </button>
      <div class="flex items-center gap-2">
        <button onclick="closeModal()" class="px-5 py-2.5 rounded-xl bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold text-xs transition">
          Tutup
        </button>
        <button onclick="closeModal(); openApplicationForm(${service.id})" class="px-6 py-2.5 rounded-xl bg-emerald-700 hover:bg-emerald-800 text-white font-bold text-xs shadow-md transition flex items-center gap-1.5">
          <i data-lucide="send" class="w-4 h-4"></i> Ajukan Permohonan Ini
        </button>
      </div>
    </div>
  `;

  modal.classList.remove('hidden');
  if (window.lucide) window.lucide.createIcons();
}

// Modal Form Pengajuan Online
function openApplicationForm(serviceId) {
  const service = (window.KUA_SERVICES || []).find(s => s.id === serviceId);
  if (!service) return;

  const modal = document.getElementById('serviceModal');
  const header = document.getElementById('modalHeader');
  const body = document.getElementById('modalBody');

  const villages = (window.KUA_VILLAGES || []).map(v => v.name);

  header.innerHTML = `
    <div>
      <span class="text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded bg-emerald-800 text-emerald-200">Formulir PTSP Online</span>
      <h3 class="text-xl font-bold mt-1 text-white">Ajukan: ${service.title}</h3>
      <p class="text-xs text-emerald-200 mt-0.5">Isi data identitas diri dan unggah berkas dokumen persyaratan</p>
    </div>
    <button onclick="closeModal()" class="text-emerald-300 hover:text-white p-1 rounded-lg">
      <i data-lucide="x" class="w-6 h-6"></i>
    </button>
  `;

  body.innerHTML = `
    <form id="applyForm" onsubmit="handleFormSubmit(event, ${service.id})" class="space-y-4 text-xs">
      
      <div>
        <label class="block font-bold text-slate-700 mb-1">Nama Lengkap Pemohon *</label>
        <input type="text" id="formApplicantName" required placeholder="Contoh: Muhammad Fadly" 
          class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none">
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
        <div>
          <label class="block font-bold text-slate-700 mb-1">Nomor Induk Kependudukan (NIK) *</label>
          <input type="text" id="formNik" required maxlength="16" placeholder="16 digit NIK sesuai KTP" 
            class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none">
        </div>
        <div>
          <label class="block font-bold text-slate-700 mb-1">Nomor WhatsApp Aktif *</label>
          <input type="tel" id="formPhone" required placeholder="Contoh: 081244556677" 
            class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none">
        </div>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
        <div>
          <label class="block font-bold text-slate-700 mb-1">Desa / Kelurahan Domisili (Biringbulu) *</label>
          <select id="formVillage" required class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none bg-white">
            <option value="">-- Pilih Wilayah --</option>
            ${villages.map(v => `<option value="${v}">${v}</option>`).join('')}
          </select>
        </div>
        <div>
          <label class="block font-bold text-slate-700 mb-1">Alamat Lengkap / Dusun / RT / RW *</label>
          <input type="text" id="formAddress" required placeholder="Dusun / Kampung / Jalan" 
            class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none">
        </div>
      </div>

      <div>
        <label class="block font-bold text-slate-700 mb-1">Catatan / Keterangan Tambahan</label>
        <textarea id="formNotes" rows="2" placeholder="Rincian permohonan, tanggal akad, atau nomor HP kerabat jika ada..." 
          class="w-full px-3.5 py-2 rounded-xl border border-slate-300 focus:ring-2 focus:ring-emerald-600 focus:outline-none"></textarea>
      </div>

      <!-- Upload Dokumen -->
      <div class="p-4 rounded-2xl bg-slate-50 border border-slate-200">
        <label class="block font-bold text-slate-800 mb-1">Unggah Berkas Persyaratan (PDF / Foto KTP / Surat Desa)</label>
        <p class="text-[11px] text-slate-500 mb-3">Pilih satu atau beberapa berkas dokumen pendukung Anda.</p>
        <input type="file" id="formFiles" multiple class="block w-full text-xs text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-xl file:border-0 file:text-xs file:font-semibold file:bg-emerald-100 file:text-emerald-800 hover:file:bg-emerald-200">
      </div>

      <div class="pt-3 flex items-center justify-end gap-3 border-t border-slate-200">
        <button type="button" onclick="closeModal()" class="px-5 py-2.5 rounded-xl bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold transition">
          Batal
        </button>
        <button type="submit" class="px-6 py-2.5 rounded-xl bg-emerald-700 hover:bg-emerald-800 text-white font-bold shadow-md transition flex items-center gap-1.5">
          <i data-lucide="check-circle-2" class="w-4 h-4"></i> Kirim Permohonan & Dapatkan Resi
        </button>
      </div>
    </form>
  `;

  modal.classList.remove('hidden');
  if (window.lucide) window.lucide.createIcons();
}

function handleFormSubmit(event, serviceId) {
  event.preventDefault();
  const service = (window.KUA_SERVICES || []).find(s => s.id === serviceId);

  const name = document.getElementById('formApplicantName').value.trim();
  const nik = document.getElementById('formNik').value.trim();
  const phone = document.getElementById('formPhone').value.trim();
  const village = document.getElementById('formVillage').value;
  const address = document.getElementById('formAddress').value.trim();
  const notes = document.getElementById('formNotes').value.trim();
  const filesInput = document.getElementById('formFiles');

  // Dokumen terunggah
  const uploadedDocs = [];
  if (filesInput && filesInput.files.length > 0) {
    for (let i = 0; i < filesInput.files.length; i++) {
      const f = filesInput.files[i];
      uploadedDocs.push({
        name: f.name,
        size: (f.size / 1024 > 1024) ? (f.size / (1024 * 1024)).toFixed(1) + ' MB' : Math.round(f.size / 1024) + ' KB'
      });
    }
  }

  // Buat nomor resi unik KUA Biringbulu
  const randomNum = Math.floor(10000 + Math.random() * 90000);
  const resiId = `BB-2026-${randomNum}`;

  const newApp = {
    id: resiId,
    serviceId: service.id,
    serviceTitle: service.title,
    applicantName: name,
    nik: nik,
    phone: phone,
    village: village,
    address: address,
    notes: notes,
    status: 'PENDING',
    createdAt: new Date().toISOString(),
    officerNotes: 'Menunggu verifikasi berkas oleh Petugas Loket PTSP KUA Biringbulu.',
    uploadedDocs: uploadedDocs
  };

  const apps = getStoredApplications();
  apps.unshift(newApp);
  saveApplications(apps);

  closeModal();
  // Alihkan langsung ke halaman pelacakan dengan resi baru
  navigateTo('track', resiId);
}

function closeModal() {
  const modal = document.getElementById('serviceModal');
  if (modal) modal.classList.add('hidden');
}

// ==========================================
// 3. LACAK BERKAS (TRACKING PAGE)
// ==========================================
function renderTrackingPage(container, autoSearchResi) {
  const apps = getStoredApplications();

  container.innerHTML = `
    <div class="max-w-3xl mx-auto">
      <div class="text-center mb-8">
        <h2 class="text-2xl font-extrabold text-slate-900">Lacak Status Berkas Permohonan PTSP</h2>
        <p class="text-xs text-slate-500 mt-1">Masukkan Nomor Resi Registrasi Anda (Contoh: BB-2026-98124) atau Nomor NIK KTP</p>
      </div>

      <!-- Search Box -->
      <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-4 mb-8 flex flex-col sm:flex-row gap-3">
        <div class="relative flex-grow">
          <i data-lucide="search" class="w-5 h-5 absolute left-4 top-3.5 text-slate-400"></i>
          <input type="text" id="trackInput" value="${autoSearchResi || ''}" placeholder="Masukkan Nomor Resi atau NIK..." 
            class="w-full pl-12 pr-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-600 text-sm font-semibold">
        </div>
        <button onclick="searchTracking()" class="px-6 py-3 rounded-xl bg-emerald-700 hover:bg-emerald-800 text-white font-bold text-sm transition flex items-center justify-center gap-2 shadow-sm">
          <i data-lucide="search" class="w-4 h-4"></i> Lacak Sekarang
        </button>
      </div>

      <div id="trackingResultArea">
        <!-- Hasil pelacakan -->
      </div>
    </div>
  `;

  if (autoSearchResi) {
    searchTracking(autoSearchResi);
  }
}

function searchTracking(queryOverride) {
  const input = document.getElementById('trackInput');
  const q = (queryOverride || (input ? input.value : '')).trim().toLowerCase();
  const area = document.getElementById('trackingResultArea');
  if (!area) return;

  const apps = getStoredApplications();
  const results = apps.filter(a => a.id.toLowerCase() === q || a.nik === q || a.phone.includes(q));

  if (results.length === 0) {
    area.innerHTML = `
      <div class="bg-white rounded-2xl p-8 text-center border border-slate-200 shadow-sm">
        <div class="w-12 h-12 rounded-full bg-slate-100 text-slate-400 flex items-center justify-center mx-auto mb-3">
          <i data-lucide="file-question" class="w-6 h-6"></i>
        </div>
        <h4 class="font-bold text-slate-800 text-sm mb-1">Nomor Resi Tidak Ditemukan</h4>
        <p class="text-xs text-slate-500 max-w-sm mx-auto mb-4">
          Pastikan nomor resi yang Anda masukkan sudah sesuai dengan tanda bukti pendaftaran KUA Biringbulu.
        </p>
      </div>
    `;
  } else {
    area.innerHTML = `
      <div class="space-y-4">
        ${results.map(r => {
          let statusBadge = '';
          let statusStep = 1;
          if (r.status === 'PENDING') {
            statusBadge = `<span class="px-3 py-1 rounded-full bg-amber-100 text-amber-900 font-bold text-xs">Menunggu Verifikasi</span>`;
            statusStep = 1;
          } else if (r.status === 'PROSES') {
            statusBadge = `<span class="px-3 py-1 rounded-full bg-blue-100 text-blue-900 font-bold text-xs">Sedang Diproses</span>`;
            statusStep = 2;
          } else if (r.status === 'SELESAI') {
            statusBadge = `<span class="px-3 py-1 rounded-full bg-emerald-100 text-emerald-900 font-bold text-xs">Selesai / Terbit</span>`;
            statusStep = 3;
          } else {
            statusBadge = `<span class="px-3 py-1 rounded-full bg-red-100 text-red-900 font-bold text-xs">Ditolak / Kurang Syarat</span>`;
            statusStep = 0;
          }

          return `
            <div class="bg-white rounded-3xl p-6 border border-slate-200 shadow-md">
              <div class="flex flex-wrap justify-between items-start gap-4 pb-4 border-b border-slate-100">
                <div>
                  <span class="text-[10px] font-bold text-slate-400 uppercase tracking-wider">Nomor Resi Registrasi</span>
                  <h3 class="text-xl font-extrabold text-emerald-800">${r.id}</h3>
                  <p class="text-xs font-semibold text-slate-800 mt-1">${r.serviceTitle}</p>
                </div>
                <div>${statusBadge}</div>
              </div>

              <!-- Progress Steps -->
              <div class="py-6">
                <div class="flex items-center justify-between text-xs font-medium text-slate-500 relative">
                  <div class="flex flex-col items-center">
                    <div class="w-8 h-8 rounded-full ${statusStep >= 1 ? 'bg-emerald-700 text-white' : 'bg-slate-200 text-slate-600'} flex items-center justify-center font-bold text-xs mb-1 z-10">1</div>
                    <span>Diterima</span>
                  </div>
                  <div class="flex-grow h-1 ${statusStep >= 2 ? 'bg-emerald-600' : 'bg-slate-200'} -mt-4"></div>
                  <div class="flex flex-col items-center">
                    <div class="w-8 h-8 rounded-full ${statusStep >= 2 ? 'bg-emerald-700 text-white' : 'bg-slate-200 text-slate-600'} flex items-center justify-center font-bold text-xs mb-1 z-10">2</div>
                    <span>Verifikasi Berkas</span>
                  </div>
                  <div class="flex-grow h-1 ${statusStep >= 3 ? 'bg-emerald-600' : 'bg-slate-200'} -mt-4"></div>
                  <div class="flex flex-col items-center">
                    <div class="w-8 h-8 rounded-full ${statusStep >= 3 ? 'bg-emerald-700 text-white' : 'bg-slate-200 text-slate-600'} flex items-center justify-center font-bold text-xs mb-1 z-10">3</div>
                    <span>Selesai / Terbit</span>
                  </div>
                </div>
              </div>

              <!-- Detail Data Pemohon -->
              <div class="bg-slate-50 rounded-2xl p-4 text-xs space-y-2 mb-4 border border-slate-100">
                <div class="grid grid-cols-1 sm:grid-cols-2 gap-2">
                  <div><span class="text-slate-500">Nama Pemohon:</span> <strong class="text-slate-800">${r.applicantName}</strong></div>
                  <div><span class="text-slate-500">Wilayah:</span> <strong class="text-emerald-700">${r.village}</strong></div>
                  <div><span class="text-slate-500">NIK:</span> <strong class="text-slate-800">${r.nik}</strong></div>
                  <div><span class="text-slate-500">No. WhatsApp:</span> <strong class="text-slate-800">${r.phone}</strong></div>
                </div>
                ${r.address ? `<div><span class="text-slate-500">Alamat:</span> ${r.address}</div>` : ''}
                ${r.notes ? `<div><span class="text-slate-500">Catatan Pemohon:</span> ${r.notes}</div>` : ''}
              </div>

              <!-- Catatan Petugas -->
              <div class="bg-emerald-50/70 border border-emerald-200 rounded-2xl p-4 text-xs">
                <div class="flex items-center gap-2 text-emerald-900 font-bold mb-1">
                  <i data-lucide="info" class="w-4 h-4 text-emerald-700"></i> Catatan Petugas Loket KUA:
                </div>
                <p class="text-slate-700">${r.officerNotes || 'Berkas Anda sedang dalam proses penelaahan tim KUA Biringbulu.'}</p>
              </div>

              ${r.uploadedDocs && r.uploadedDocs.length > 0 ? `
                <div class="mt-4 pt-4 border-t border-slate-100">
                  <span class="text-[11px] font-bold text-slate-600 block mb-2">Berkas Dokumen Terlampir:</span>
                  <div class="flex flex-wrap gap-2">
                    ${r.uploadedDocs.map(d => `
                      <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-xl bg-slate-100 text-slate-700 text-xs border border-slate-200">
                        <i data-lucide="file-text" class="w-3.5 h-3.5 text-emerald-700"></i> ${d.name} (${d.size})
                      </span>
                    `).join('')}
                  </div>
                </div>
              ` : ''}

              <!-- Beri Penilaian IKM -->
              <div class="mt-4 pt-3 border-t border-slate-100 flex flex-wrap justify-between items-center gap-2">
                <span class="text-xs text-slate-500">Puas dengan pelayanan kami? Bantu evaluasi mutu KUA.</span>
                <button onclick="navigateTo('ikm', { prefilledService: '${r.serviceTitle.replace(/'/g, "\\'")}', applicantName: '${r.applicantName.replace(/'/g, "\\'")}', village: '${r.village}', phone: '${r.phone}', activeTab: 1 })" class="px-3.5 py-1.5 rounded-xl bg-amber-50 hover:bg-amber-100 border border-amber-300 text-amber-900 font-bold text-xs transition flex items-center gap-1.5 shadow-sm">
                  <i data-lucide="star" class="w-3.5 h-3.5 text-amber-500 fill-amber-400"></i> Beri Penilaian Bintang IKM
                </button>
              </div>
            </div>
          `;
        }).join('')}
      </div>
    `;
  }

  if (window.lucide) window.lucide.createIcons();
}

// ==========================================
// 4. KONSULTASI SYARIAH & KIBLAT
// ==========================================
function renderSyariahPage(container) {
  container.innerHTML = `
    <div class="max-w-4xl mx-auto space-y-8">
      <div>
        <h2 class="text-2xl font-extrabold text-slate-900">Konsultasi Syariah & Kalibrasi Arah Kiblat</h2>
        <p class="text-xs text-slate-500 mt-1">Layanan bimbingan hukum waris Islam, hisab kiblat masjid, dan konsultasi keluarga sakinah KUA Kec. Biringbulu.</p>
      </div>

      <!-- Card Kalibrasi Kiblat Biringbulu -->
      <div class="bg-gradient-to-br from-emerald-900 to-teal-900 text-white rounded-3xl p-6 sm:p-8 shadow-xl border border-emerald-700/60">
        <div class="flex flex-col md:flex-row justify-between items-center gap-6">
          <div>
            <span class="px-2.5 py-1 rounded-full bg-emerald-800 text-emerald-200 text-[10px] font-bold uppercase tracking-wider">Hisab & Falakiyah Resmi</span>
            <h3 class="text-2xl font-bold mt-2">Data Hisab Arah Kiblat Kec. Biringbulu</h3>
            <p class="text-xs text-emerald-200/90 mt-1 max-w-xl">
              Dihitung berdasarkan koordinat geografis Kantor KUA Kecamatan Biringbulu Kabupaten Gowa (-5.4851° LS, 119.8242° BT).
            </p>
            <div class="grid grid-cols-2 sm:grid-cols-3 gap-4 mt-6">
              <div class="bg-white/10 rounded-2xl p-3 border border-white/10">
                <span class="text-[10px] text-emerald-300 block">Azimuth Kiblat</span>
                <span class="text-xl font-extrabold text-amber-300">292° 39'</span>
                <span class="text-[10px] text-emerald-200 block">(22.65° dari Barat ke Utara)</span>
              </div>
              <div class="bg-white/10 rounded-2xl p-3 border border-white/10">
                <span class="text-[10px] text-emerald-300 block">Jarak ke Ka'bah</span>
                <span class="text-xl font-extrabold text-white">± 9.245 km</span>
                <span class="text-[10px] text-emerald-200 block">Gowa ke Makkah Al-Mukarramah</span>
              </div>
              <div class="bg-white/10 rounded-2xl p-3 border border-white/10 col-span-2 sm:col-span-1">
                <span class="text-[10px] text-emerald-300 block">Layanan Kalibrasi</span>
                <span class="text-base font-bold text-white">Gratis (Rp 0,-)</span>
                <span class="text-[10px] text-emerald-200 block">Pengukuran theodolite</span>
              </div>
            </div>
          </div>
          <div class="text-center bg-white/10 p-6 rounded-3xl border border-white/10 w-full md:w-auto">
            <i data-lucide="compass" class="w-16 h-16 text-amber-300 mx-auto mb-2 animate-spin-slow"></i>
            <button onclick="openApplicationForm(44)" class="w-full px-5 py-2.5 rounded-xl bg-amber-400 text-emerald-950 font-bold text-xs shadow-md hover:bg-amber-300 transition">
              Mohon Kalibrasi Masjid
            </button>
          </div>
        </div>
      </div>

      <!-- Tanya Jawab Syariah & Konsultasi Interaktif -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm">
          <h4 class="font-bold text-slate-900 text-base mb-2 flex items-center gap-2">
            <i data-lucide="scale" class="w-5 h-5 text-emerald-700"></i> Konsultasi Fikih Waris (Mawaris)
          </h4>
          <p class="text-xs text-slate-500 mb-4">
            KUA Biringbulu menyediakan layanan konsultasi perhitungan faraidh pembagian harta waris secara syariat Islam dan musyawarah kekeluargaan.
          </p>
          <div class="space-y-2 text-xs text-slate-600 mb-6 bg-slate-50 p-4 rounded-xl">
            <p>✓ Bebas biaya konsultasi (Rp 0,-)</p>
            <p>✓ Dibimbing langsung oleh Penyuluh Agama Islam & Penghulu</p>
            <p>✓ Mengedepankan kemaslahatan dan kerukunan keluarga</p>
          </div>
          <a href="https://wa.me/6281241193634?text=Halo%20KUA%20Biringbulu,%20saya%20ingin%20konsultasi%20mengenai%20waris%20syariah" target="_blank"
            class="inline-flex items-center gap-2 px-4 py-2.5 rounded-xl bg-emerald-700 text-white font-semibold text-xs hover:bg-emerald-800 transition">
            <i data-lucide="message-circle" class="w-4 h-4"></i> Konsultasi via WhatsApp
          </a>
        </div>

        <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm">
          <h4 class="font-bold text-slate-900 text-base mb-2 flex items-center gap-2">
            <i data-lucide="heart-handshake" class="w-5 h-5 text-emerald-700"></i> Mediasi Keluarga & BP4
          </h4>
          <p class="text-xs text-slate-500 mb-4">
            Badan Penasihatan, Pembinaan, dan Pelestarian Perkawinan (BP4) KUA Biringbulu siap membantu mediasi sengketa rumah tangga secara privat.
          </p>
          <div class="space-y-2 text-xs text-slate-600 mb-6 bg-slate-50 p-4 rounded-xl">
            <p>✓ Menjaga kerahasiaan penuh masalah rumah tangga</p>
            <p>✓ Pendekatan syar'i, psikologis, dan kearifan budaya Bugis-Makassar</p>
            <p>✓ Fasilitas konseling tatap muka di ruang mediasi KUA</p>
          </div>
          <button onclick="openApplicationForm(23)" class="inline-flex items-center gap-2 px-4 py-2.5 rounded-xl bg-slate-800 text-white font-semibold text-xs hover:bg-slate-900 transition">
            <i data-lucide="calendar" class="w-4 h-4"></i> Ajukan Jadwal Konseling BP4
          </button>
        </div>
      </div>
    </div>
  `;
}

// ==========================================
// 5. PROFIL KUA & WILAYAH DESA
// ==========================================
function renderProfilePage(container) {
  const staff = window.KUA_STAFF || [];
  const villages = window.KUA_VILLAGES || [];

  container.innerHTML = `
    <div class="space-y-10">
      <div>
        <h2 class="text-2xl font-extrabold text-slate-900">Profil Kantor & Wilayah Kerja KUA Biringbulu</h2>
        <p class="text-xs text-slate-500 mt-1">Kementerian Agama Kabupaten Gowa, Provinsi Sulawesi Selatan.</p>
      </div>

      <!-- Ringkasan Profil Kantor -->
      <div class="bg-white rounded-3xl p-6 sm:p-8 border border-slate-200 shadow-sm">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="md:col-span-2 space-y-3">
            <span class="px-2.5 py-1 rounded bg-emerald-100 text-emerald-800 text-[10px] font-bold uppercase">Profil Instansi</span>
            <h3 class="text-xl font-bold text-slate-900">KUA Kecamatan Biringbulu</h3>
            <p class="text-xs text-slate-600 leading-relaxed">
              Kantor Urusan Agama (KUA) Kecamatan Biringbulu bertugas melaksanakan tugas pelayanan, bimbingan, pencatatan nikah dan rujuk, pengembangan zakat dan wakaf, hisab rukyat, bimbingan kemasjidan, serta pembinaan kerukunan umat beragama di wilayah Kecamatan Biringbulu, Kabupaten Gowa.
            </p>
            <div class="pt-2 text-xs text-slate-500 space-y-1">
              <p><strong>Alamat:</strong> Jl. Poros Malakaji - Biringbulu, Kab. Gowa, Sulsel 92174</p>
              <p><strong>Waktu Pelayanan:</strong> Senin - Kamis (07.30 - 16.00 WITA) | Jumat (07.30 - 16.30 WITA)</p>
            </div>
          </div>
          <div class="bg-emerald-50 rounded-2xl p-5 border border-emerald-100 flex flex-col justify-center text-center">
            <span class="text-xs font-bold text-emerald-800">Wilayah Binaan Resmi</span>
            <span class="text-4xl font-extrabold text-emerald-700 my-2">11</span>
            <span class="text-xs text-slate-600 font-medium">Desa & Kelurahan di Biringbulu</span>
          </div>
        </div>
      </div>

      <!-- Banner Survei IKM Berbintang -->
      <div onclick="navigateTo('ikm')" class="cursor-pointer bg-gradient-to-r from-amber-50 to-orange-50 border border-amber-200/90 rounded-2xl p-5 shadow-sm hover:shadow-md transition flex items-center justify-between gap-4">
        <div class="flex items-center gap-4">
          <div class="w-12 h-12 rounded-2xl bg-amber-500/20 text-amber-600 flex items-center justify-center shrink-0">
            <i data-lucide="star" class="w-6 h-6 fill-amber-500 text-amber-500"></i>
          </div>
          <div>
            <h4 class="font-bold text-amber-950 text-base">Survei IKM Berbintang</h4>
            <p class="text-xs text-amber-800/90 mt-0.5">Laporan & evaluasi kepuasan masyarakat KUA Biringbulu berdasarkan PermenPAN-RB No. 14/2017</p>
          </div>
        </div>
        <button class="px-4 py-2 rounded-xl bg-amber-500 text-white font-bold text-xs hover:bg-amber-600 transition flex items-center gap-1.5 shrink-0 shadow-sm">
          <span>Buka Survei</span>
          <i data-lucide="chevron-right" class="w-4 h-4"></i>
        </button>
      </div>

      <!-- Struktur Pegawai & Pejabat -->
      <div>
        <h3 class="text-lg font-bold text-slate-900 mb-4 flex items-center gap-2">
          <i data-lucide="users" class="w-5 h-5 text-emerald-700"></i> Struktur Pegawai & Pejabat KUA
        </h3>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          ${staff.map(s => `
            <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm flex flex-col justify-between">
              <div>
                <div class="w-10 h-10 rounded-xl bg-emerald-700 text-white flex items-center justify-center font-bold text-sm mb-3">
                  ${s.name.split(' ').map(n => n[0]).slice(0, 2).join('')}
                </div>
                <h4 class="font-bold text-slate-900 text-sm leading-tight">${s.name}</h4>
                <p class="text-[11px] font-semibold text-emerald-700 mt-1">${s.title}</p>
                <p class="text-[10px] text-slate-400 font-mono mt-0.5">NIP: ${s.nip}</p>
                <p class="text-xs text-slate-500 mt-3 leading-relaxed">${s.role}</p>
              </div>
              <div class="mt-4 pt-3 border-t border-slate-100 text-[11px] text-emerald-800 font-medium flex items-center gap-1">
                <span class="w-2 h-2 rounded-full bg-emerald-500"></span> ${s.status.split('-')[0]}
              </div>
            </div>
          `).join('')}
        </div>
      </div>

      <!-- Data 11 Desa & Kelurahan Biringbulu -->
      <div>
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-bold text-slate-900 flex items-center gap-2">
            <i data-lucide="map" class="w-5 h-5 text-emerald-700"></i> Data 11 Desa & Kelurahan Biringbulu
          </h3>
          <span class="text-xs font-semibold text-emerald-700">Termasuk Kelurahan Lauwa & Tonrorita</span>
        </div>

        <div class="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
          <div class="overflow-x-auto">
            <table class="w-full text-left text-xs">
              <thead class="bg-slate-50 text-slate-600 uppercase text-[10px] tracking-wider border-b border-slate-200 font-bold">
                <tr>
                  <th class="px-4 py-3">Nama Wilayah</th>
                  <th class="px-4 py-3">Status</th>
                  <th class="px-4 py-3">Imam Desa / Kelurahan</th>
                  <th class="px-4 py-3">Penyuluh Pendamping</th>
                  <th class="px-4 py-3 text-center">Masjid</th>
                  <th class="px-4 py-3 text-center">Tanah Wakaf</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-slate-100">
                ${villages.map(v => `
                  <tr class="hover:bg-slate-50/80 transition">
                    <td class="px-4 py-3.5 font-bold text-slate-800">${v.name}</td>
                    <td class="px-4 py-3.5">
                      <span class="px-2 py-0.5 rounded text-[10px] font-bold ${v.isKelurahan ? 'bg-amber-100 text-amber-800' : 'bg-slate-100 text-slate-700'}">
                        ${v.isKelurahan ? 'Kelurahan' : 'Desa'}
                      </span>
                    </td>
                    <td class="px-4 py-3.5 text-slate-600">${v.imamDesa}</td>
                    <td class="px-4 py-3.5 text-slate-600">${v.penyuluh}</td>
                    <td class="px-4 py-3.5 text-center font-semibold text-emerald-700">${v.mosques}</td>
                    <td class="px-4 py-3.5 text-center font-semibold text-emerald-700">${v.wakaf}</td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  `;
}

// ==========================================
// 6. DASHBOARD PETUGAS KUA (ADMIN VERIFIKASI)
// ==========================================
function renderAdminPage(container) {
  const apps = getStoredApplications();

  container.innerHTML = `
    <div class="space-y-6">
      <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 bg-emerald-950 text-white p-6 rounded-3xl shadow-lg border border-emerald-800">
        <div>
          <span class="text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded bg-emerald-800 text-emerald-200">Panel Petugas PTSP</span>
          <h2 class="text-2xl font-extrabold mt-1">Verifikasi Permohonan Masuk</h2>
          <p class="text-xs text-emerald-300 mt-0.5">Kelola berkas warga Biringbulu, ubah status, dan perbarui nomor resi.</p>
        </div>
        <div class="flex items-center gap-2">
          <span class="px-3 py-1.5 rounded-xl bg-white/10 text-xs font-semibold">Total: ${apps.length} Berkas</span>
        </div>
      </div>

      <!-- Daftar Permohonan Masuk -->
      <div class="space-y-4">
        ${apps.length === 0 ? `
          <div class="bg-white rounded-2xl p-8 text-center border border-slate-200">
            <p class="text-xs text-slate-500">Belum ada permohonan masuk saat ini.</p>
          </div>
        ` : apps.map(app => `
          <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm">
            <div class="flex flex-wrap justify-between items-start gap-2 mb-3">
              <div>
                <span class="font-mono font-bold text-emerald-800 text-xs">${app.id}</span>
                <h4 class="font-bold text-slate-900 text-sm mt-0.5">${app.serviceTitle}</h4>
                <p class="text-xs text-slate-500">Pemohon: <strong>${app.applicantName}</strong> (${app.village}) • Telp: ${app.phone}</p>
              </div>
              <div class="flex items-center gap-2">
                <select onchange="updateAppStatus('${app.id}', this.value)" class="text-xs font-bold px-3 py-1.5 rounded-xl border border-slate-300 focus:outline-none bg-white">
                  <option value="PENDING" ${app.status === 'PENDING' ? 'selected' : ''}>Menunggu</option>
                  <option value="PROSES" ${app.status === 'PROSES' ? 'selected' : ''}>Diproses</option>
                  <option value="SELESAI" ${app.status === 'SELESAI' ? 'selected' : ''}>Selesai</option>
                  <option value="DITOLAK" ${app.status === 'DITOLAK' ? 'selected' : ''}>Ditolak</option>
                </select>
              </div>
            </div>

            <div class="bg-slate-50 p-3 rounded-xl text-xs space-y-1 mb-3 text-slate-600">
              <p><strong>NIK:</strong> ${app.nik} | <strong>Alamat:</strong> ${app.address || '-'}</p>
              ${app.notes ? `<p><strong>Catatan Pemohon:</strong> ${app.notes}</p>` : ''}
              ${app.uploadedDocs && app.uploadedDocs.length > 0 ? `
                <div class="pt-2">
                  <strong class="text-slate-700 block mb-1">Berkas Terlampir:</strong>
                  <div class="flex flex-wrap gap-2">
                    ${app.uploadedDocs.map(d => `
                      <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded bg-white border border-slate-200 text-[11px] text-emerald-800">
                        <i data-lucide="file-check" class="w-3 h-3"></i> ${d.name} (${d.size})
                      </span>
                    `).join('')}
                  </div>
                </div>
              ` : ''}
            </div>

            <!-- Edit Catatan Petugas -->
            <div class="flex items-center gap-2">
              <input type="text" id="noteInput_${app.id}" value="${app.officerNotes || ''}" placeholder="Tulis catatan petugas / info kelengkapan..."
                class="flex-grow px-3 py-1.5 rounded-xl border border-slate-200 text-xs focus:ring-1 focus:ring-emerald-600 focus:outline-none">
              <button onclick="saveOfficerNote('${app.id}')" class="px-3.5 py-1.5 rounded-xl bg-emerald-700 hover:bg-emerald-800 text-white font-semibold text-xs transition">
                Simpan Catatan
              </button>
            </div>
          </div>
        `).join('')}
      </div>
    </div>
  `;
}

function updateAppStatus(appId, newStatus) {
  const apps = getStoredApplications();
  const target = apps.find(a => a.id === appId);
  if (target) {
    target.status = newStatus;
    saveApplications(apps);
    renderAdminPage(document.getElementById('appContent'));
    if (window.lucide) window.lucide.createIcons();
  }
}

function saveOfficerNote(appId) {
  const input = document.getElementById(`noteInput_${appId}`);
  if (!input) return;
  const note = input.value.trim();

  const apps = getStoredApplications();
  const target = apps.find(a => a.id === appId);
  if (target) {
    target.officerNotes = note;
    saveApplications(apps);
    alert('Catatan petugas berhasil diperbarui!');
  }
}

// ==========================================
// 7. SURVEI IKM BERBINTANG (INDEKS KEPUASAN MASYARAKAT)
// ==========================================
let ikmActiveTab = 0; // 0: Statistik & Ulasan, 1: Formulir Survei
let ikmFormRatings = {
  overall: 5,
  req: 5,
  proc: 5,
  speed: 5,
  cost: 5,
  staff: 5,
  facility: 5
};
let ikmPrefillData = null;

function renderIkmPage(container, options = {}) {
  if (options && options.activeTab !== undefined) {
    ikmActiveTab = options.activeTab;
  }
  if (options && (options.prefilledService || options.applicantName || options.village || options.phone)) {
    ikmPrefillData = options;
  }

  const surveys = getStoredIkmSurveys();
  const totalCount = surveys.length;
  const avgOverall = totalCount > 0 ? (surveys.reduce((sum, s) => sum + s.overall, 0) / totalCount) : 4.96;
  const ikmKonversi = (avgOverall / 5.0) * 100.0;

  // Unsur Pelayanan Rata-rata
  const avgReq = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.requirements || s.overall), 0) / totalCount) : 5.0;
  const avgProc = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.procedure || s.overall), 0) / totalCount) : 4.8;
  const avgSpeed = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.speed || s.overall), 0) / totalCount) : 4.8;
  const avgCost = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.cost || s.overall), 0) / totalCount) : 5.0;
  const avgStaff = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.staff || s.overall), 0) / totalCount) : 5.0;
  const avgFac = totalCount > 0 ? (surveys.reduce((sum, s) => sum + (s.facility || s.overall), 0) / totalCount) : 4.8;

  // Star Distribution
  const star5 = surveys.filter(s => s.overall === 5).length;
  const star4 = surveys.filter(s => s.overall === 4).length;
  const star3 = surveys.filter(s => s.overall === 3).length;
  const star2 = surveys.filter(s => s.overall === 2).length;
  const star1 = surveys.filter(s => s.overall === 1).length;

  container.innerHTML = `
    <div class="max-w-4xl mx-auto space-y-6">
      
      <!-- Header Banner (Pangadakkang Maroon & Gold) -->
      <div class="bg-gradient-to-r from-red-950 via-rose-900 to-amber-950 text-white p-6 sm:p-8 rounded-3xl shadow-xl border border-rose-800/60 relative overflow-hidden">
        <div class="absolute -right-12 -top-12 w-48 h-48 bg-amber-400/10 rounded-full blur-2xl pointer-events-none"></div>
        <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 relative z-10">
          <div>
            <div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-rose-800/80 border border-rose-600/40 text-amber-200 text-xs font-bold uppercase tracking-wider mb-2">
              <i data-lucide="shield-check" class="w-3.5 h-3.5 text-amber-300"></i> Kemenag Kab. Gowa • Zona Integritas WBK
            </div>
            <h2 class="text-2xl sm:text-3xl font-extrabold tracking-tight">Survei IKM Berbintang</h2>
            <p class="text-xs sm:text-sm text-rose-200 mt-1 max-w-xl">
              Indeks Kepuasan Masyarakat atas 48 Standar Layanan Kantor Urusan Agama (KUA) Kec. Biringbulu berdasarkan PermenPAN-RB No. 14 Tahun 2017.
            </p>
          </div>
          <div class="text-right bg-white/10 px-5 py-3 rounded-2xl border border-white/15 backdrop-blur-sm shrink-0">
            <span class="text-[10px] text-amber-300 uppercase font-bold block">Indeks IKM Resmi</span>
            <span class="text-2xl font-black text-amber-300">${ikmKonversi.toFixed(1)}</span>
            <span class="text-[11px] text-rose-200 block font-semibold">Predikat: A (Sangat Baik)</span>
          </div>
        </div>
      </div>

      <!-- Tab Switcher -->
      <div class="bg-white rounded-2xl p-1.5 border border-slate-200 shadow-sm flex items-center gap-1">
        <button onclick="setIkmTab(0)" class="flex-1 py-2.5 rounded-xl font-bold text-xs sm:text-sm transition flex items-center justify-center gap-2 ${ikmActiveTab === 0 ? 'bg-rose-900 text-white shadow-sm' : 'text-slate-600 hover:bg-slate-100'}">
          <i data-lucide="bar-chart-3" class="w-4 h-4"></i> Hasil & Statistik
        </button>
        <button onclick="setIkmTab(1)" class="flex-1 py-2.5 rounded-xl font-bold text-xs sm:text-sm transition flex items-center justify-center gap-2 ${ikmActiveTab === 1 ? 'bg-rose-900 text-white shadow-sm' : 'text-slate-600 hover:bg-slate-100'}">
          <i data-lucide="pencil" class="w-4 h-4"></i> Isi Survei IKM
        </button>
      </div>

      <!-- Tab 0: Hasil & Statistik -->
      <div id="ikmTabStats" class="${ikmActiveTab === 0 ? 'block' : 'hidden'} space-y-6">
        
        <!-- Big Score Card -->
        <div class="bg-gradient-to-b from-amber-50/60 to-white rounded-3xl p-6 sm:p-8 border border-slate-200 shadow-sm text-center">
          <div class="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-amber-100 text-amber-900 text-xs font-bold border border-amber-200/80 mb-4">
            <i data-lucide="check-circle" class="w-3.5 h-3.5 text-amber-700"></i> Zona Integritas & WBK Kemenag RI
          </div>
          
          <div class="text-5xl sm:text-6xl font-black text-slate-900 tracking-tight my-2">
            ${avgOverall.toFixed(2)}
          </div>
          
          <div class="flex items-center justify-center gap-1.5 my-3 text-amber-400">
            ${[1, 2, 3, 4, 5].map(() => `<svg class="w-7 h-7 fill-amber-400 text-amber-400" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>`).join('')}
          </div>

          <h3 class="text-base sm:text-lg font-extrabold text-emerald-700 uppercase tracking-wide">
            MUTU PELAYANAN: A (SANGAT BAIK)
          </h3>
          <p class="text-xs text-slate-500 mt-1">
            Nilai Konversi IKM: <strong>${ikmKonversi.toFixed(1)} / 100</strong> • <strong>${totalCount} Responden</strong> Warga Biringbulu
          </p>

          <div class="mt-6 max-w-sm mx-auto">
            <button onclick="setIkmTab(1)" class="w-full py-3.5 px-6 rounded-2xl bg-rose-900 hover:bg-rose-800 text-white font-bold text-sm shadow-md transition flex items-center justify-center gap-2">
              <i data-lucide="pencil" class="w-4 h-4"></i> Beri Penilaian Layanan Saya
            </button>
          </div>
        </div>

        <!-- Distribusi Bintang -->
        <div class="bg-white rounded-3xl p-6 border border-slate-200 shadow-sm">
          <h4 class="font-bold text-slate-900 text-sm mb-4 flex items-center gap-2">
            <i data-lucide="star" class="w-4 h-4 text-amber-500 fill-amber-400"></i> Distribusi Rating Bintang
          </h4>
          <div class="space-y-2.5 text-xs">
            ${[
              { stars: 5, count: star5 },
              { stars: 4, count: star4 },
              { stars: 3, count: star3 },
              { stars: 2, count: star2 },
              { stars: 1, count: star1 }
            ].map(item => {
              const pct = totalCount > 0 ? Math.round((item.count / totalCount) * 100) : 0;
              return `
                <div class="flex items-center gap-3">
                  <span class="w-12 font-bold text-slate-700 flex items-center gap-1">
                    ${item.stars} <svg class="w-3.5 h-3.5 fill-amber-400 text-amber-400 inline" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>
                  </span>
                  <div class="flex-grow bg-slate-100 rounded-full h-3 overflow-hidden">
                    <div class="bg-amber-400 h-full rounded-full transition-all duration-500" style="width: ${pct}%"></div>
                  </div>
                  <span class="w-16 text-right font-semibold text-slate-500">${item.count} (${pct}%)</span>
                </div>
              `;
            }).join('')}
          </div>
        </div>

        <!-- 6 Unsur Pelayanan PermenPAN-RB -->
        <div class="bg-white rounded-3xl p-6 border border-slate-200 shadow-sm">
          <div class="flex items-center gap-2 mb-1">
            <i data-lucide="shield" class="w-5 h-5 text-emerald-700"></i>
            <h4 class="font-bold text-slate-900 text-sm">Skor per Unsur Pelayanan IKM</h4>
          </div>
          <p class="text-[11px] text-slate-500 mb-5">Berdasarkan PermenPAN-RB No. 14/2017 Pedoman SKM Pelayanan Publik</p>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            ${[
              { num: '1. Persyaratan Pelayanan', score: avgReq, desc: 'Kejelasan & transparansi syarat berkas' },
              { num: '2. Prosedur & Alur', score: avgProc, desc: 'Kemudahan loket & kepastian alur' },
              { num: '3. Kecepatan Pelayanan', score: avgSpeed, desc: 'Kesesuaian durasi waktu pengerjaan' },
              { num: '4. Biaya / Nol Pungli', score: avgCost, desc: 'Rp 0,- di Balai Nikah & tanpa pungli' },
              { num: '5. Sikap Petugas (5S)', score: avgStaff, desc: 'Keramahan, kesopanan & integritas' },
              { num: '6. Sarana & Ruang Tunggu', score: avgFac, desc: 'Kenyamanan & kebersihan fasilitas KUA' }
            ].map(unsur => `
              <div class="p-4 rounded-2xl bg-slate-50 border border-slate-100 flex items-center justify-between">
                <div>
                  <h5 class="font-bold text-slate-800 text-xs">${unsur.num}</h5>
                  <p class="text-[11px] text-slate-500 mt-0.5">${unsur.desc}</p>
                </div>
                <div class="text-right shrink-0 ml-3">
                  <span class="font-black text-slate-800 text-sm flex items-center gap-1">
                    ${unsur.score.toFixed(1)} <svg class="w-4 h-4 fill-amber-400 text-amber-400" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>
                  </span>
                  <span class="text-[10px] font-bold text-emerald-700">Sangat Baik</span>
                </div>
              </div>
            `).join('')}
          </div>
        </div>

        <!-- Ulasan & Masukan Warga -->
        <div>
          <h4 class="font-bold text-slate-900 text-base mb-4 flex items-center gap-2">
            <i data-lucide="message-square" class="w-4 h-4 text-rose-800"></i> Ulasan & Masukan Warga (${totalCount})
          </h4>
          <div class="space-y-4">
            ${surveys.map(s => `
              <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm hover:border-rose-300 transition">
                <div class="flex flex-wrap justify-between items-start gap-2 mb-2">
                  <div class="flex items-center gap-3">
                    <div class="w-10 h-10 rounded-full bg-rose-100 text-rose-900 font-bold text-xs flex items-center justify-center shrink-0">
                      ${s.name.split(' ').map(w => w[0]).slice(0, 2).join('')}
                    </div>
                    <div>
                      <h5 class="font-bold text-slate-800 text-xs">${s.name}</h5>
                      <span class="text-[11px] text-emerald-700 font-semibold">${s.village} • ${s.service}</span>
                    </div>
                  </div>
                  <div class="text-right">
                    <div class="flex items-center gap-0.5 text-amber-400 justify-end">
                      ${[1, 2, 3, 4, 5].map(i => `
                        <svg class="w-4 h-4 ${i <= s.overall ? 'fill-amber-400 text-amber-400' : 'text-slate-200'}" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>
                      `).join('')}
                    </div>
                    <span class="text-[10px] text-slate-400 mt-0.5 block">${s.date}</span>
                  </div>
                </div>
                <p class="text-xs text-slate-600 leading-relaxed bg-slate-50 p-3 rounded-xl border border-slate-100 mt-2">
                  "${s.feedback}"
                </p>
              </div>
            `).join('')}
          </div>
        </div>

      </div>

      <!-- Tab 1: Formulir Survei IKM -->
      <div id="ikmTabForm" class="${ikmActiveTab === 1 ? 'block' : 'hidden'} space-y-6">
        
        <!-- Apresiasi Card -->
        <div class="bg-red-50/70 border border-red-200 rounded-2xl p-4 text-xs flex items-center gap-3 text-red-950">
          <div class="w-10 h-10 rounded-xl bg-red-100 text-red-800 flex items-center justify-center shrink-0">
            <i data-lucide="thumbs-up" class="w-5 h-5"></i>
          </div>
          <div>
            <h5 class="font-bold text-red-900">Apresiasi & Penilaian Pelayanan</h5>
            <p class="text-slate-600 text-[11px] mt-0.5">Penilaian Anda membantu KUA Kecamatan Biringbulu menjaga integritas wilayah bebas korupsi dan pelayanan prima.</p>
          </div>
        </div>

        <form id="ikmForm" onsubmit="handleIkmSubmit(event)" class="space-y-6">
          
          <!-- 1. Identitas Responden -->
          <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm space-y-4">
            <h4 class="font-bold text-slate-900 text-sm">1. Data Responden</h4>
            
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label class="block font-bold text-slate-700 text-xs mb-1">Nama Lengkap (Boleh Anonim/Inisial) *</label>
                <input type="text" id="ikmName" required value="${ikmPrefillData ? (ikmPrefillData.applicantName || '') : ''}" placeholder="Contoh: Muhammad Fadly / Dg. Rapi"
                  class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-rose-800 focus:outline-none text-xs">
              </div>
              <div>
                <label class="block font-bold text-slate-700 text-xs mb-1">Nomor WhatsApp / HP (Opsional)</label>
                <input type="tel" id="ikmPhone" value="${ikmPrefillData ? (ikmPrefillData.phone || '') : ''}" placeholder="08xx-xxxx-xxxx"
                  class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-rose-800 focus:outline-none text-xs">
              </div>
            </div>

            <div>
              <label class="block font-bold text-slate-700 text-xs mb-1">Asal Desa / Kelurahan di Biringbulu *</label>
              <select id="ikmVillage" required class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-rose-800 focus:outline-none text-xs bg-white">
                <option value="">-- Pilih Wilayah Domisili --</option>
                ${(window.KUA_VILLAGES || []).map(v => `
                  <option value="${v.name}" ${ikmPrefillData && ikmPrefillData.village === v.name ? 'selected' : ''}>${v.name} (${v.isKelurahan ? 'Kelurahan' : 'Desa'})</option>
                `).join('')}
              </select>
            </div>
          </div>

          <!-- 2. Layanan yang Dinilai -->
          <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm space-y-4">
            <h4 class="font-bold text-slate-900 text-sm">2. Layanan yang Diterima</h4>
            <div>
              <label class="block font-bold text-slate-700 text-xs mb-1">Pilih Standar Layanan KUA Biringbulu *</label>
              <select id="ikmService" required class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-rose-800 focus:outline-none text-xs bg-white">
                <option value="">-- Pilih Layanan PTSP --</option>
                ${(window.KUA_SERVICES || []).map(s => `
                  <option value="${s.title}" ${ikmPrefillData && (ikmPrefillData.prefilledService === s.title || (ikmPrefillData.prefilledService && s.title.includes(ikmPrefillData.prefilledService))) ? 'selected' : ''}>
                    #${s.id} - ${s.title}
                  </option>
                `).join('')}
              </select>
            </div>
          </div>

          <!-- 3. Kepuasan Menyeluruh (Bintang Utama) -->
          <div class="bg-gradient-to-br from-amber-50/70 to-white rounded-2xl p-6 border border-amber-200 shadow-sm text-center">
            <h4 class="font-bold text-amber-950 text-sm mb-1">3. Kepuasan Menyeluruh (Bintang Utama)</h4>
            <p class="text-xs text-amber-800/80 mb-4">Sentuh bintang 1 sampai 5 sesuai kepuasan Anda</p>
            
            <div class="flex items-center justify-center gap-2 mb-2" id="ikmMainStars">
              ${[1, 2, 3, 4, 5].map(starNum => `
                <button type="button" onclick="setIkmRating('overall', ${starNum})" class="p-1 focus:outline-none transform hover:scale-110 transition">
                  <svg id="star_overall_${starNum}" class="w-9 h-9 fill-amber-400 text-amber-400" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>
                </button>
              `).join('')}
            </div>

            <div id="ikmOverallLabel" class="text-xs font-bold text-emerald-700">
              ⭐⭐⭐⭐⭐ Sangat Puas (Istimewa, Cepat & Ramah)!
            </div>
          </div>

          <!-- 4. Penilaian Rinci 6 Unsur Pelayanan -->
          <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm space-y-5">
            <div>
              <h4 class="font-bold text-slate-900 text-sm">4. Penilaian Rinci 6 Unsur Pelayanan</h4>
              <p class="text-xs text-slate-500 mt-0.5">Sentuh bintang 1 sampai 5 untuk setiap unsur berikut:</p>
            </div>

            ${[
              { key: 'req', title: 'Persyaratan Pelayanan', desc: 'Syarat berkas jelas & tidak merepotkan' },
              { key: 'proc', title: 'Prosedur & Alur Pelayanan', desc: 'Alur mudah dipahami & bebas birokrasi rumit' },
              { key: 'speed', title: 'Kecepatan Waktu Pelayanan', desc: 'Penyelesaian berkas sesuai durasi janji layanan' },
              { key: 'cost', title: 'Biaya Layanan & Bebas Pungli', desc: 'Rp 0,- di KUA & tidak ada pungli/gratifikasi' },
              { key: 'staff', title: 'Kompetensi & Keramahan Petugas', desc: 'Sikap 5S (Senyum, Salam, Sapa, Sopan, Santun)' },
              { key: 'facility', title: 'Sarana & Prasarana Ruang Balai', desc: 'Kebersihan, kenyamanan AC/kursi tunggu KUA' }
            ].map(item => `
              <div class="p-4 rounded-xl bg-slate-50 border border-slate-100 flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                <div>
                  <h5 class="font-bold text-slate-800 text-xs">${item.title}</h5>
                  <p class="text-[11px] text-slate-500">${item.desc}</p>
                </div>
                <div class="flex items-center gap-1">
                  ${[1, 2, 3, 4, 5].map(n => `
                    <button type="button" onclick="setIkmRating('${item.key}', ${n})" class="p-0.5 focus:outline-none transform hover:scale-110 transition">
                      <svg id="star_${item.key}_${n}" class="w-6 h-6 fill-amber-400 text-amber-400" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/></svg>
                    </button>
                  `).join('')}
                </div>
              </div>
            `).join('')}
          </div>

          <!-- 5. Masukan / Saran Perbaikan -->
          <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm space-y-3">
            <h4 class="font-bold text-slate-900 text-sm">5. Masukan / Saran Perbaikan (Opsional)</h4>
            <textarea id="ikmFeedback" rows="3" placeholder="Tuliskan pengalaman, kritik yang membangun, atau saran untuk kemajuan layanan KUA Biringbulu..."
              class="w-full px-3.5 py-2.5 rounded-xl border border-slate-300 focus:ring-2 focus:ring-rose-800 focus:outline-none text-xs"></textarea>
          </div>

          <!-- Submit Button -->
          <div class="flex items-center justify-end gap-3 pt-2">
            <button type="button" onclick="setIkmTab(0)" class="px-5 py-2.5 rounded-xl bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold text-xs transition">
              Batal
            </button>
            <button type="submit" class="px-7 py-3 rounded-xl bg-rose-900 hover:bg-rose-800 text-white font-bold text-xs shadow-md transition flex items-center gap-2">
              <i data-lucide="check-circle" class="w-4 h-4"></i> Kirim Penilaian IKM Berbintang
            </button>
          </div>

        </form>

      </div>

    </div>
  `;

  if (window.lucide) window.lucide.createIcons();
}

function setIkmTab(tabIdx) {
  ikmActiveTab = tabIdx;
  const stats = document.getElementById('ikmTabStats');
  const form = document.getElementById('ikmTabForm');
  if (stats && form) {
    if (tabIdx === 0) {
      stats.classList.remove('hidden');
      form.classList.add('hidden');
    } else {
      stats.classList.add('hidden');
      form.classList.remove('hidden');
    }
  }
  renderIkmPage(document.getElementById('appContent'));
  if (window.lucide) window.lucide.createIcons();
}

function setIkmRating(key, val) {
  ikmFormRatings[key] = val;
  for (let i = 1; i <= 5; i++) {
    const el = document.getElementById(`star_${key}_${i}`);
    if (el) {
      if (i <= val) {
        el.setAttribute('class', 'w-6 h-6 fill-amber-400 text-amber-400');
        if (key === 'overall') el.setAttribute('class', 'w-9 h-9 fill-amber-400 text-amber-400');
      } else {
        el.setAttribute('class', 'w-6 h-6 text-slate-200 fill-slate-200');
        if (key === 'overall') el.setAttribute('class', 'w-9 h-9 text-slate-200 fill-slate-200');
      }
    }
  }

  if (key === 'overall') {
    const labelEl = document.getElementById('ikmOverallLabel');
    if (labelEl) {
      const labels = {
        1: "⭐ Sangat Tidak Puas (Pelayanan Buruk)",
        2: "⭐⭐ Kurang Puas (Banyak Kendala)",
        3: "⭐⭐⭐ Cukup Puas (Standar Rata-Rata)",
        4: "⭐⭐⭐⭐ Puas (Pelayanan Baik & Jelas)",
        5: "⭐⭐⭐⭐⭐ Sangat Puas (Istimewa, Cepat & Ramah)!"
      };
      labelEl.innerText = labels[val] || labels[5];
      labelEl.className = val >= 4 ? 'text-xs font-bold text-emerald-700' : 'text-xs font-bold text-amber-700';
    }
  }
}

function handleIkmSubmit(event) {
  event.preventDefault();
  const name = document.getElementById('ikmName').value.trim();
  const phone = document.getElementById('ikmPhone').value.trim();
  const village = document.getElementById('ikmVillage').value;
  const service = document.getElementById('ikmService').value;
  const feedback = document.getElementById('ikmFeedback').value.trim() || 'Pelayanan di KUA Biringbulu sangat baik, transparan, dan sesuai standar.';

  const now = new Date();
  const dateFormatted = `${now.getDate()} September ${now.getFullYear()}, ${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;

  const newSurvey = {
    id: Date.now(),
    name: name,
    phone: phone,
    village: village,
    service: service,
    overall: ikmFormRatings.overall,
    requirements: ikmFormRatings.req,
    procedure: ikmFormRatings.proc,
    speed: ikmFormRatings.speed,
    cost: ikmFormRatings.cost,
    staff: ikmFormRatings.staff,
    facility: ikmFormRatings.facility,
    feedback: feedback,
    date: dateFormatted
  };

  const surveys = getStoredIkmSurveys();
  surveys.unshift(newSurvey);
  saveIkmSurveys(surveys);

  alert(`Alhamdulillah! Terima kasih Bapak/Ibu ${name}. Penilaian Survei IKM Anda telah tercatat demi mewujudkan KUA Biringbulu yang Bebas Pungli dan Melayani.`);
  
  // Bersihkan form & buka tab hasil
  ikmActiveTab = 0;
  ikmPrefillData = null;
  renderIkmPage(document.getElementById('appContent'));
  if (window.lucide) window.lucide.createIcons();
}

// Global Initialization
document.addEventListener('DOMContentLoaded', () => {
  const mobileBtn = document.getElementById('mobileMenuBtn');
  if (mobileBtn) {
    mobileBtn.addEventListener('click', toggleMobileMenu);
  }

  // Load awal
  navigateTo('home');
});

