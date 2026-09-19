package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BiringbuluData
import com.example.data.KuaServiceData
import com.example.data.local.entity.IkmSurveyEntity
import com.example.model.KuaServiceCategory
import com.example.ui.theme.KuaGreenPrimary
import com.example.ui.theme.KuaGreenPrimaryDarker
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IkmSurveyScreen(
    surveys: List<IkmSurveyEntity>,
    onSubmitSurvey: (
        name: String,
        phone: String,
        service: String,
        village: String,
        overall: Int,
        req: Int,
        proc: Int,
        speed: Int,
        cost: Int,
        staff: Int,
        fac: Int,
        feedback: String,
        onSuccess: () -> Unit
    ) -> Unit,
    onBack: () -> Unit,
    initialServiceTitle: String? = null,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(if (initialServiceTitle != null) 1 else 0) }
    val context = LocalContext.current

    // Form fields & Interactive Service Picker state
    var respondentName by remember { mutableStateOf("") }
    var respondentPhone by remember { mutableStateOf("") }
    var serviceSearchQuery by remember { mutableStateOf("") }
    var selectedServiceCategory by remember { mutableStateOf<KuaServiceCategory?>(null) }
    var isServicePickerExpanded by remember { mutableStateOf(false) }

    val allServicesList = remember { KuaServiceData.allServices }
    val filteredServicesList = remember(serviceSearchQuery, selectedServiceCategory) {
        allServicesList.filter { item ->
            val matchesCategory = selectedServiceCategory == null || item.category == selectedServiceCategory
            val matchesQuery = serviceSearchQuery.isBlank() ||
                    item.title.contains(serviceSearchQuery.trim(), ignoreCase = true) ||
                    item.subtitle.contains(serviceSearchQuery.trim(), ignoreCase = true) ||
                    item.category.title.contains(serviceSearchQuery.trim(), ignoreCase = true) ||
                    item.category.shortName.contains(serviceSearchQuery.trim(), ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    var selectedService by remember {
        mutableStateOf(
            if (!initialServiceTitle.isNullOrBlank()) {
                val match = allServicesList.find {
                    it.title.contains(initialServiceTitle.take(10), ignoreCase = true) ||
                    initialServiceTitle.contains(it.title.take(10), ignoreCase = true)
                }
                match?.let { String.format(Locale.US, "%02d. %s", it.id, it.title) } ?: initialServiceTitle
            } else {
                "01. Pendaftaran Kehendak Nikah"
            }
        )
    }

    val availableVillages = remember {
        BiringbuluData.villages.map { it.name }
    }
    var selectedVillage by remember { mutableStateOf(availableVillages.firstOrNull() ?: "Tonrorita") }

    var overallRating by remember { mutableIntStateOf(5) }
    var ratingReq by remember { mutableIntStateOf(5) }
    var ratingProc by remember { mutableIntStateOf(5) }
    var ratingSpeed by remember { mutableIntStateOf(5) }
    var ratingCost by remember { mutableIntStateOf(5) }
    var ratingStaff by remember { mutableIntStateOf(5) }
    var ratingFac by remember { mutableIntStateOf(5) }
    var feedbackText by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }

    // Aggregate statistics
    val totalCount = surveys.size
    val averageOverall = if (totalCount > 0) surveys.map { it.overallRating }.average() else 4.95
    val ikmKonversi = (averageOverall / 5.0) * 100.0 // Skala 0-100 PermenPAN-RB

    val avgReq = if (totalCount > 0) surveys.map { it.ratingRequirements }.average() else 4.9
    val avgProc = if (totalCount > 0) surveys.map { it.ratingProcedure }.average() else 4.8
    val avgSpeed = if (totalCount > 0) surveys.map { it.ratingSpeed }.average() else 4.9
    val avgCost = if (totalCount > 0) surveys.map { it.ratingCost }.average() else 5.0
    val avgStaff = if (totalCount > 0) surveys.map { it.ratingStaff }.average() else 4.9
    val avgFac = if (totalCount > 0) surveys.map { it.ratingFacility }.average() else 4.8

    val starCount5 = surveys.count { it.overallRating == 5 }
    val starCount4 = surveys.count { it.overallRating == 4 }
    val starCount3 = surveys.count { it.overallRating == 3 }
    val starCount2 = surveys.count { it.overallRating == 2 }
    val starCount1 = surveys.count { it.overallRating == 1 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Survei IKM",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Indeks Kepuasan Masyarakat • Kemenag Kab. Gowa",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("ikm_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KuaGreenPrimary
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
        ) {
            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = KuaGreenPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = KuaGreenPrimary,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Analytics,
                                contentDescription = null,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Hasil & Statistik", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    },
                    modifier = Modifier.testTag("tab_ikm_stats")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.RateReview,
                                contentDescription = null,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Isi Survei IKM", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    },
                    modifier = Modifier.testTag("tab_ikm_form")
                )
            }

            if (selectedTab == 0) {
                // Tab 0: Results & Statistics
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // 1. Big Score Card
                    item {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color(0xFFFFFBEB), // Pastel Amber
                                                Color.White
                                            )
                                        )
                                    )
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color(0xFFFEF3C7))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Verified,
                                        contentDescription = null,
                                        tint = Color(0xFFB45309),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Zona Integritas & WBK Kemenag RI",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309)
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = String.format(Locale.US, "%.2f", averageOverall),
                                    fontSize = 46.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E293B)
                                )

                                // 5 Golden Stars Display
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFF59E0B),
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "MUTU PELAYANAN: A (SANGAT BAIK)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF059669)
                                )

                                Text(
                                    text = "Nilai Konversi IKM: ${String.format(Locale.US, "%.1f", ikmKonversi)} / 100 • $totalCount Responden",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = { selectedTab = 1 },
                                    colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("btn_open_survey_form")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.RateReview,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Beri Penilaian Layanan Saya",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    // 2. Star Distribution Card
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Distribusi Rating Bintang",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                val max = totalCount.coerceAtLeast(1).toFloat()
                                StarProgressRow(5, starCount5, starCount5 / max)
                                StarProgressRow(4, starCount4, starCount4 / max)
                                StarProgressRow(3, starCount3, starCount3 / max)
                                StarProgressRow(2, starCount2, starCount2 / max)
                                StarProgressRow(1, starCount1, starCount1 / max)
                            }
                        }
                    }

                    // 3. PermenPAN-RB 6 Unsur Pelayanan
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Security,
                                        contentDescription = null,
                                        tint = Color(0xFF059669),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Skor per Unsur Pelayanan IKM",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Text(
                                    text = "Berdasarkan PermenPAN-RB No. 14/2017 Pedoman SKM Pelayanan Publik",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B),
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                                )

                                IkmAspectRow("1. Persyaratan Pelayanan", avgReq, "Kejelasan & transparansi syarat berkas")
                                IkmAspectRow("2. Prosedur & Alur", avgProc, "Kemudahan loket & kepastian alur")
                                IkmAspectRow("3. Kecepatan Pelayanan", avgSpeed, "Kesesuaian durasi waktu pengerjaan")
                                IkmAspectRow("4. Biaya / Nol Pungli", avgCost, "Rp 0,- di Balai Nikah & tanpa pungli")
                                IkmAspectRow("5. Sikap Petugas (5S)", avgStaff, "Keramahan, kesopanan & integritas")
                                IkmAspectRow("6. Sarana & Ruang Tunggu", avgFac, "Kenyamanan & kebersihan fasilitas KUA")
                            }
                        }
                    }

                    // 4. Feed Testimoni / Ulasan Masyarakat
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Ulasan & Masukan Warga ($totalCount)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1E293B)
                            )
                        }
                    }

                    items(surveys) { survey ->
                        ReviewCard(survey = survey)
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            } else {
                // Tab 1: Form Pengisian Survei IKM
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Apresiasi & Info Card
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5EE)),
                            border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(KuaGreenPrimary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ThumbUp,
                                        contentDescription = null,
                                        tint = KuaGreenPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Apresiasi & Penilaian Pelayanan",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.5.sp,
                                        color = KuaGreenPrimaryDarker
                                    )
                                    Text(
                                        text = "Penilaian Anda membantu KUA Kecamatan Biringbulu menjaga integritas wilayah bebas korupsi dan pelayanan prima.",
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF065F46)
                                    )
                                }
                            }
                        }
                    }

                    // Identitas Responden
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "1. Data Responden",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = respondentName,
                                    onValueChange = { respondentName = it },
                                    label = { Text("Nama Responden (Boleh Anonim/Inisial)") },
                                    placeholder = { Text("Contoh: Ahmad Fauzi / Daeng Rapi") },
                                    leadingIcon = {
                                        Icon(Icons.Filled.Person, contentDescription = null, tint = Color(0xFF64748B))
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = KuaGreenPrimary,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ikm_input_name")
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = respondentPhone,
                                    onValueChange = { respondentPhone = it },
                                    label = { Text("Nomor HP / WhatsApp (Opsional)") },
                                    placeholder = { Text("08xx-xxxx-xxxx") },
                                    leadingIcon = {
                                        Icon(Icons.Filled.Phone, contentDescription = null, tint = Color(0xFF64748B))
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = KuaGreenPrimary,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ikm_input_phone")
                                )

                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Asal Desa / Kelurahan di Biringbulu:",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF334155)
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(availableVillages) { v ->
                                        val isSelected = selectedVillage == v
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = { selectedVillage = v },
                                            label = { Text(v, fontSize = 12.sp) },
                                            leadingIcon = if (isSelected) {
                                                { Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                            } else null,
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = KuaGreenPrimary,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 2. Layanan yang Diterima (Menarik & Tanpa Tanda Pagar)
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "2. Layanan yang Diterima",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.5.sp,
                                        color = Color(0xFF1E293B)
                                    )
                                    TextButton(
                                        onClick = { isServicePickerExpanded = !isServicePickerExpanded }
                                    ) {
                                        Text(
                                            text = if (isServicePickerExpanded) "Tutup Pilihan" else "Ganti Layanan",
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = KuaGreenPrimary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = if (isServicePickerExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                            contentDescription = null,
                                            tint = KuaGreenPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Selected Service Highlight Card
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                                    border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { isServicePickerExpanded = !isServicePickerExpanded }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(KuaGreenPrimary),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Verified,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Layanan Terpilih",
                                                fontSize = 10.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF047857)
                                            )
                                            Text(
                                                text = selectedService,
                                                fontSize = 13.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF065F46)
                                            )
                                        }
                                    }
                                }

                                // Expandable Selector with Search & Category Filters
                                AnimatedVisibility(visible = isServicePickerExpanded) {
                                    Column(modifier = Modifier.padding(top = 12.dp)) {
                                        // Search Input
                                        OutlinedTextField(
                                            value = serviceSearchQuery,
                                            onValueChange = { serviceSearchQuery = it },
                                            placeholder = { Text("Cari layanan (nikah, wakaf, kiblat)...", fontSize = 12.5.sp) },
                                            leadingIcon = {
                                                Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                                            },
                                            trailingIcon = if (serviceSearchQuery.isNotBlank()) {
                                                {
                                                    IconButton(onClick = { serviceSearchQuery = "" }) {
                                                        Icon(Icons.Filled.Clear, contentDescription = "Hapus", modifier = Modifier.size(16.dp))
                                                    }
                                                }
                                            } else null,
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = KuaGreenPrimary,
                                                unfocusedBorderColor = Color(0xFFCBD5E1)
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Category Chips
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            item {
                                                FilterChip(
                                                    selected = selectedServiceCategory == null,
                                                    onClick = { selectedServiceCategory = null },
                                                    label = { Text("Semua (48)", fontSize = 11.5.sp) },
                                                    colors = FilterChipDefaults.filterChipColors(
                                                        selectedContainerColor = KuaGreenPrimary,
                                                        selectedLabelColor = Color.White
                                                    )
                                                )
                                            }
                                            items(KuaServiceCategory.values()) { cat ->
                                                val isCatSelected = selectedServiceCategory == cat
                                                val count = allServicesList.count { it.category == cat }
                                                FilterChip(
                                                    selected = isCatSelected,
                                                    onClick = { selectedServiceCategory = if (isCatSelected) null else cat },
                                                    label = { Text("${cat.shortName} ($count)", fontSize = 11.5.sp) },
                                                    colors = FilterChipDefaults.filterChipColors(
                                                        selectedContainerColor = KuaGreenPrimary,
                                                        selectedLabelColor = Color.White
                                                    )
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Services List Items
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            filteredServicesList.take(15).forEach { item ->
                                                val itemFormatted = String.format(Locale.US, "%02d. %s", item.id, item.title)
                                                val isSelected = selectedService == itemFormatted || selectedService.endsWith(item.title)
                                                Surface(
                                                    onClick = {
                                                        selectedService = itemFormatted
                                                        isServicePickerExpanded = false
                                                    },
                                                    shape = RoundedCornerShape(10.dp),
                                                    color = if (isSelected) Color(0xFFDCFCE7) else Color(0xFFF8FAFC),
                                                    border = BorderStroke(1.dp, if (isSelected) KuaGreenPrimary else Color(0xFFE2E8F0)),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Row(
                                                        modifier = Modifier.padding(10.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(24.dp)
                                                                .clip(CircleShape)
                                                                .background(if (isSelected) KuaGreenPrimary else Color(0xFFCBD5E1)),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                text = String.format(Locale.US, "%02d", item.id),
                                                                color = if (isSelected) Color.White else Color(0xFF334155),
                                                                fontSize = 10.5.sp,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.width(10.dp))
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = item.title,
                                                                fontSize = 12.5.sp,
                                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                                color = if (isSelected) Color(0xFF047857) else Color(0xFF1E293B)
                                                            )
                                                            Text(
                                                                text = item.subtitle,
                                                                fontSize = 10.5.sp,
                                                                color = Color(0xFF64748B),
                                                                maxLines = 1
                                                            )
                                                        }
                                                        if (isSelected) {
                                                            Icon(
                                                                imageVector = Icons.Filled.CheckCircle,
                                                                contentDescription = null,
                                                                tint = KuaGreenPrimary,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Bintang Kepuasan Utama
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                            border = BorderStroke(1.5.dp, Color(0xFFFDE68A)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "3. Kepuasan Menyeluruh (Bintang Utama)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF92400E)
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                InteractiveStarRating(
                                    rating = overallRating,
                                    onRatingChanged = { overallRating = it },
                                    starSize = 36.dp,
                                    modifier = Modifier.testTag("ikm_main_stars")
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val label = when (overallRating) {
                                    1 -> "⭐ Sangat Tidak Puas (Pelayanan Buruk)"
                                    2 -> "⭐⭐ Kurang Puas (Banyak Kendala)"
                                    3 -> "⭐⭐⭐ Cukup Puas (Standar Rata-Rata)"
                                    4 -> "⭐⭐⭐⭐ Puas (Pelayanan Baik & Jelas)"
                                    else -> "⭐⭐⭐⭐⭐ Sangat Puas (Istimewa, Cepat & Ramah)!"
                                }
                                Text(
                                    text = label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (overallRating >= 4) Color(0xFF059669) else Color(0xFFB45309)
                                )
                            }
                        }
                    }

                    // Bintang 6 Aspek Mutu Pelayanan
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "4. Penilaian Rinci 6 Unsur Pelayanan",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = "Sentuh bintang 1 sampai 5 untuk setiap unsur berikut:",
                                    fontSize = 11.5.sp,
                                    color = Color(0xFF64748B),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                AspectStarInputRow(
                                    title = "Persyaratan Pelayanan",
                                    subtitle = "Syarat berkas jelas & tidak merepotkan",
                                    currentRating = ratingReq,
                                    onRatingChange = { ratingReq = it }
                                )
                                AspectStarInputRow(
                                    title = "Prosedur & Alur Pelayanan",
                                    subtitle = "Alur mudah dipahami & bebas birokrasi rumit",
                                    currentRating = ratingProc,
                                    onRatingChange = { ratingProc = it }
                                )
                                AspectStarInputRow(
                                    title = "Kecepatan Waktu Pelayanan",
                                    subtitle = "Penyelesaian berkas sesuai durasi janji layanan",
                                    currentRating = ratingSpeed,
                                    onRatingChange = { ratingSpeed = it }
                                )
                                AspectStarInputRow(
                                    title = "Biaya Layanan & Bebas Pungli",
                                    subtitle = "Rp 0,- di KUA & tidak ada pungli/gratifikasi",
                                    currentRating = ratingCost,
                                    onRatingChange = { ratingCost = it }
                                )
                                AspectStarInputRow(
                                    title = "Kompetensi & Keramahan Petugas",
                                    subtitle = "Sikap 5S (Senyum, Salam, Sapa, Sopan, Santun)",
                                    currentRating = ratingStaff,
                                    onRatingChange = { ratingStaff = it }
                                )
                                AspectStarInputRow(
                                    title = "Sarana & Prasarana Ruang Balai",
                                    subtitle = "Kebersihan, kenyamanan AC/kursi tunggu KUA",
                                    currentRating = ratingFac,
                                    onRatingChange = { ratingFac = it }
                                )
                            }
                        }
                    }

                    // Saran & Masukan
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "5. Masukan / Saran Perbaikan (Opsional)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = feedbackText,
                                    onValueChange = { feedbackText = it },
                                    placeholder = { Text("Tuliskan kritik, saran, atau ucapan apresiasi untuk pelayanan staf KUA Biringbulu...") },
                                    minLines = 3,
                                    maxLines = 6,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = KuaGreenPrimary,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ikm_input_feedback")
                                )
                            }
                        }
                    }

                    // Submit Button
                    item {
                        Button(
                            onClick = {
                                onSubmitSurvey(
                                    respondentName,
                                    respondentPhone,
                                    selectedService,
                                    selectedVillage,
                                    overallRating,
                                    ratingReq,
                                    ratingProc,
                                    ratingSpeed,
                                    ratingCost,
                                    ratingStaff,
                                    ratingFac,
                                    feedbackText
                                ) {
                                    showSuccessDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("btn_submit_ikm")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFDE047),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Kirim Penilaian IKM",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                selectedTab = 0
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDCFCE7)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF16A34A),
                        modifier = Modifier.size(34.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "Terima Kasih atas Penilaian Anda!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xFF1E293B)
                )
            },
            text = {
                Column {
                    Text(
                        text = "Survei Kepuasan Masyarakat (IKM) Anda telah berhasil tercatat di pangkalan data KUA Kecamatan Biringbulu.",
                        fontSize = 13.5.sp,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Layanan: $selectedService",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "Penilaian: $overallRating dari 5 Bintang ⭐",
                                fontSize = 12.sp,
                                color = Color(0xFFB45309),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Wilayah: Desa $selectedVillage",
                                fontSize = 11.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        selectedTab = 0 // Return to stats
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Lihat Hasil IKM", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun InteractiveStarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    starSize: androidx.compose.ui.unit.Dp = 30.dp,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 1..5) {
            val isFilled = i <= rating
            val scale by animateFloatAsState(targetValue = if (isFilled) 1.15f else 1.0f, label = "star_scale")
            IconButton(
                onClick = { onRatingChanged(i) },
                modifier = Modifier
                    .size(48.dp) // Accessibility min touch target
                    .scale(scale)
            ) {
                Icon(
                    imageVector = if (isFilled) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Bintang $i",
                    tint = if (isFilled) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}

@Composable
fun AspectStarInputRow(
    title: String,
    subtitle: String,
    currentRating: Int,
    onRatingChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (star in 1..5) {
                    val filled = star <= currentRating
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { onRatingChange(star) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (filled) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = null,
                            tint = if (filled) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        Divider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun StarProgressRow(stars: Int, count: Int, ratio: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "$stars",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569),
            modifier = Modifier.width(14.dp)
        )
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color(0xFFF59E0B),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { ratio.coerceIn(0f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(7.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFFF59E0B),
            trackColor = Color(0xFFF1F5F9)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$count",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF64748B),
            modifier = Modifier.width(26.dp)
        )
    }
}

@Composable
fun IkmAspectRow(title: String, score: Double, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B)
            )
            Text(
                text = desc,
                fontSize = 11.sp,
                color = Color(0xFF64748B)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = String.format(Locale.US, "%.1f", score),
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFF59E0B),
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
fun ReviewCard(survey: IkmSurveyEntity) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = survey.respondentName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Color(0xFF1E293B)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE2E8F0))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Desa ${survey.village}",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF475569)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = survey.serviceName,
                            fontSize = 11.sp,
                            color = KuaGreenPrimary,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }

                // Stars
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(survey.overallRating) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }

            if (survey.feedback.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF8FAFC))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "\"${survey.feedback}\"",
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = survey.formattedDate.ifBlank { "September 2026" },
                fontSize = 10.5.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}
