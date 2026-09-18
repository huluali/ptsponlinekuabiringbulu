package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.KuaServiceCategory

data class AppMenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val iconColor: Color,
    val iconBgColor: Color,
    val action: () -> Unit
)

data class MenuGroupSection(
    val categoryTitle: String,
    val categoryIcon: ImageVector,
    val headerBadgeColor: Color = Color(0xFFE0E7FF),
    val headerIconColor: Color = Color(0xFF3730A3),
    val items: List<AppMenuItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullMenuGridScreen(
    onBack: () -> Unit,
    onNavigateToServices: (KuaServiceCategory?) -> Unit,
    onNavigateToServiceDetail: (Int) -> Unit,
    onNavigateToTracking: (String) -> Unit,
    onNavigateToSmartSyariah: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    // Define the menu sections exactly matching the user request screenshot style
    val menuSections = remember {
        listOf(
            // Section 1: Kepegawaian & Sistem Kemenag (Exactly as in screenshot)
            MenuGroupSection(
                categoryTitle = "Kepegawaian",
                categoryIcon = Icons.Filled.Badge,
                headerBadgeColor = Color(0xFFEFF6FF), // Soft Blue
                headerIconColor = Color(0xFF2563EB),
                items = listOf(
                    AppMenuItem(
                        id = "sipakatau",
                        title = "SIPAKATAU",
                        icon = Icons.Filled.Assignment,
                        iconColor = Color(0xFFB71C1C), // Deep Red
                        iconBgColor = Color(0xFFFFEBEE), // Pastel Red
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "absensi",
                        title = "Absensi\nKegiatan",
                        icon = Icons.Filled.CalendarMonth,
                        iconColor = Color(0xFFD97706), // Amber/Orange
                        iconBgColor = Color(0xFFFEF3C7), // Pastel Amber
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "tukin_lp",
                        title = "Tukin/LP",
                        icon = Icons.Filled.Receipt,
                        iconColor = Color(0xFF059669), // Green
                        iconBgColor = Color(0xFFD1FAE5), // Pastel Green
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "simpro",
                        title = "SIMPRO",
                        icon = Icons.Filled.MenuBook,
                        iconColor = Color(0xFF7C3AED), // Purple
                        iconBgColor = Color(0xFFEDE9FE), // Pastel Purple
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "simantik",
                        title = "SIMANTIK",
                        icon = Icons.Filled.Terminal,
                        iconColor = Color(0xFFE11D48), // Rose
                        iconBgColor = Color(0xFFFFE4E6), // Pastel Rose
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "simpeg5",
                        title = "SIMPEG 5",
                        icon = Icons.Filled.Inbox,
                        iconColor = Color(0xFFD97706), // Orange
                        iconBgColor = Color(0xFFFEF3C7), // Pastel Orange
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "sicuti",
                        title = "Si Cuti",
                        icon = Icons.Filled.Flight,
                        iconColor = Color(0xFF0284C7), // Sky Blue
                        iconBgColor = Color(0xFFE0F2FE), // Pastel Sky
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "sipakainge",
                        title = "SIPAKAINGE",
                        icon = Icons.Filled.MilitaryTech,
                        iconColor = Color(0xFF059669), // Emerald
                        iconBgColor = Color(0xFFDCFCE7), // Pastel Emerald
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "hrms",
                        title = "HRMS",
                        icon = Icons.Filled.Person,
                        iconColor = Color(0xFF4F46E5), // Indigo
                        iconBgColor = Color(0xFFEEF2FF), // Pastel Indigo
                        action = { onNavigateToProfile() }
                    )
                )
            ),

            // Section 2: Pelayanan Pernikahan & Keluarga Sakinah
            MenuGroupSection(
                categoryTitle = "Pernikahan & Keluarga",
                categoryIcon = Icons.Filled.Favorite,
                headerBadgeColor = Color(0xFFFDF2F8), // Soft Pink
                headerIconColor = Color(0xFFDB2777),
                items = listOf(
                    AppMenuItem(
                        id = "simkah",
                        title = "SIMKAH\nDigital",
                        icon = Icons.Filled.Favorite,
                        iconColor = Color(0xFFE11D48),
                        iconBgColor = Color(0xFFFFE4E6),
                        action = { onNavigateToServiceDetail(1) } // Pendaftaran Nikah
                    ),
                    AppMenuItem(
                        id = "bimwin",
                        title = "Bimwin\nCatin",
                        icon = Icons.Filled.Diversity1,
                        iconColor = Color(0xFF7C3AED),
                        iconBgColor = Color(0xFFEDE9FE),
                        action = { onNavigateToServiceDetail(21) }
                    ),
                    AppMenuItem(
                        id = "biaya_nikah",
                        title = "Biaya Nikah\nRp 0,- Balai",
                        icon = Icons.Filled.Calculate,
                        iconColor = Color(0xFF059669),
                        iconBgColor = Color(0xFFD1FAE5),
                        action = { onNavigateToSmartSyariah(2) }
                    ),
                    AppMenuItem(
                        id = "duplikat_buku",
                        title = "Duplikat\nBuku Nikah",
                        icon = Icons.Filled.CardMembership,
                        iconColor = Color(0xFFD97706),
                        iconBgColor = Color(0xFFFEF3C7),
                        action = { onNavigateToServiceDetail(14) }
                    ),
                    AppMenuItem(
                        id = "surat_rekom",
                        title = "Rekomendasi\nNikah",
                        icon = Icons.Filled.Assignment,
                        iconColor = Color(0xFF0284C7),
                        iconBgColor = Color(0xFFE0F2FE),
                        action = { onNavigateToServiceDetail(13) }
                    ),
                    AppMenuItem(
                        id = "konseling",
                        title = "Konseling\nBP4",
                        icon = Icons.Filled.Diversity1,
                        iconColor = Color(0xFFB71C1C),
                        iconBgColor = Color(0xFFFFEBEE),
                        action = { onNavigateToServiceDetail(23) }
                    )
                )
            ),

            // Section 3: Kemasjidan, Zakat & Wakaf
            MenuGroupSection(
                categoryTitle = "Kemasjidan, Zakat & Wakaf",
                categoryIcon = Icons.Filled.Mosque,
                headerBadgeColor = Color(0xFFECFDF5), // Soft Mint
                headerIconColor = Color(0xFF059669),
                items = listOf(
                    AppMenuItem(
                        id = "simas",
                        title = "SIMAS\nMasjid",
                        icon = Icons.Filled.Mosque,
                        iconColor = Color(0xFF059669),
                        iconBgColor = Color(0xFFD1FAE5),
                        action = { onNavigateToServiceDetail(31) }
                    ),
                    AppMenuItem(
                        id = "siwak",
                        title = "SIWAK\nAkta AIW",
                        icon = Icons.Filled.AccountBalance,
                        iconColor = Color(0xFFD97706),
                        iconBgColor = Color(0xFFFEF3C7),
                        action = { onNavigateToServiceDetail(25) }
                    ),
                    AppMenuItem(
                        id = "kiblat",
                        title = "Arah Kiblat\nPresisi",
                        icon = Icons.Filled.CompassCalibration,
                        iconColor = Color(0xFF0284C7),
                        iconBgColor = Color(0xFFE0F2FE),
                        action = { onNavigateToSmartSyariah(0) }
                    ),
                    AppMenuItem(
                        id = "zakat",
                        title = "Kalkulator\nZakat Mal",
                        icon = Icons.Filled.VolunteerActivism,
                        iconColor = Color(0xFF7C3AED),
                        iconBgColor = Color(0xFFEDE9FE),
                        action = { onNavigateToSmartSyariah(1) }
                    ),
                    AppMenuItem(
                        id = "halal",
                        title = "Sertifikasi\nHalal",
                        icon = Icons.Filled.Verified,
                        iconColor = Color(0xFF059669),
                        iconBgColor = Color(0xFFDCFCE7),
                        action = { onNavigateToServiceDetail(43) }
                    ),
                    AppMenuItem(
                        id = "lacak",
                        title = "Lacak\nBerkas",
                        icon = Icons.Filled.QrCodeScanner,
                        iconColor = Color(0xFFB71C1C),
                        iconBgColor = Color(0xFFFFEBEE),
                        action = { onNavigateToTracking("") }
                    )
                )
            ),

            // Section 4: Layanan Publik & Syariah
            MenuGroupSection(
                categoryTitle = "Layanan Syariah & Wilayah",
                categoryIcon = Icons.Filled.HomeRepairService,
                headerBadgeColor = Color(0xFFFFFBEB),
                headerIconColor = Color(0xFFD97706),
                items = listOf(
                    AppMenuItem(
                        id = "syariah_ai",
                        title = "Tanya AI\nSyariah",
                        icon = Icons.Filled.SmartToy,
                        iconColor = Color(0xFF7C3AED),
                        iconBgColor = Color(0xFFEDE9FE),
                        action = { onNavigateToSmartSyariah(3) }
                    ),
                    AppMenuItem(
                        id = "desa_biringbulu",
                        title = "Profil 11\nDesa",
                        icon = Icons.Filled.LocationOn,
                        iconColor = Color(0xFFE11D48),
                        iconBgColor = Color(0xFFFFE4E6),
                        action = { onNavigateToProfile() }
                    ),
                    AppMenuItem(
                        id = "penyuluh",
                        title = "Penyuluh\nAgama",
                        icon = Icons.Filled.RecordVoiceOver,
                        iconColor = Color(0xFF0284C7),
                        iconBgColor = Color(0xFFE0F2FE),
                        action = { onNavigateToServices(KuaServiceCategory.PENERANGAN_ISLAM) }
                    ),
                    AppMenuItem(
                        id = "katalog_48",
                        title = "Semua 48\nLayanan",
                        icon = Icons.Filled.FactCheck,
                        iconColor = Color(0xFFB71C1C),
                        iconBgColor = Color(0xFFFFEBEE),
                        action = { onNavigateToServices(null) }
                    )
                )
            )
        )
    }

    // Filter by query if user types in search
    val filteredSections = remember(searchQuery, menuSections) {
        if (searchQuery.isBlank()) {
            menuSections
        } else {
            val q = searchQuery.trim().lowercase()
            menuSections.mapNotNull { section ->
                val matchingItems = section.items.filter { it.title.lowercase().contains(q) || section.categoryTitle.lowercase().contains(q) }
                if (matchingItems.isNotEmpty()) {
                    section.copy(items = matchingItems)
                } else null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu Lengkap",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        ),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back_menu_lengkap")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB71C1C), // Deep Red like the screenshot
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8FAFC),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("full_menu_grid_screen"),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Search Bar (Rounded style with greyish fill exactly like screenshot)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Cari layanan...",
                                fontSize = 15.sp,
                                color = Color(0xFF94A3B8)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Cari",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Hapus",
                                        tint = Color(0xFF64748B)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp), // Pill shaped like screenshot
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF1F5F9),
                            unfocusedContainerColor = Color(0xFFF1F5F9),
                            focusedBorderColor = Color(0xFFCBD5E1),
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("menu_search_input")
                    )
                }
            }

            // Render each category container
            items(filteredSections) { section ->
                MenuSectionCard(section = section)
            }
        }
    }
}

@Composable
fun MenuSectionCard(
    section: MenuGroupSection,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp), // Rounded white card container like screenshot
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0).copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Category Header with Badge Icon & Service Count
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = section.headerBadgeColor,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = section.categoryIcon,
                            contentDescription = null,
                            tint = section.headerIconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = section.categoryTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "${section.items.size} layanan",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3-Column Grid for Items (Just like screenshot: 3 items per row)
            val chunkedItems = section.items.chunked(3)
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                chunkedItems.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        for (i in 0..2) {
                            if (i < rowItems.size) {
                                val item = rowItems[i]
                                Box(modifier = Modifier.weight(1f)) {
                                    GridMenuItem(item = item)
                                }
                            } else {
                                // Spacer to maintain 3-column symmetry
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridMenuItem(
    item: AppMenuItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = item.action)
            .padding(vertical = 4.dp, horizontal = 2.dp)
            .testTag("menu_item_${item.id}")
    ) {
        // Rounded square container with pastel background and centered icon
        Surface(
            shape = RoundedCornerShape(20.dp), // Soft curved square as in screenshot
            color = item.iconBgColor,
            modifier = Modifier.size(64.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.iconColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Item Title below icon
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 15.sp
            ),
            color = Color(0xFF334155),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
