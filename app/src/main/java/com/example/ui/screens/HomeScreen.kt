package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BiringbuluData
import com.example.data.KuaServiceData
import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.KuaCardBorderLight
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import com.example.ui.theme.KuaGreenPrimaryDarker
import com.example.ui.theme.KuaGreenPrimaryLight

@Composable
fun HomeScreen(
    onNavigateToServices: (KuaServiceCategory?) -> Unit,
    onNavigateToServiceDetail: (Int) -> Unit,
    onNavigateToTracking: (String) -> Unit,
    onNavigateToSmartSyariah: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quickTrackingCode by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val popularServices = remember {
        listOf(
            KuaServiceData.getServiceById(1), // Pendaftaran Nikah
            KuaServiceData.getServiceById(21), // Bimwin Catin
            KuaServiceData.getServiceById(25), // AIW Wakaf
            KuaServiceData.getServiceById(31), // ID SIMAS Masjid
            KuaServiceData.getServiceById(43), // Sertifikat Halal
            KuaServiceData.getServiceById(45)  // Kalibrasi Arah Kiblat
        ).filterNotNull()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        // 1. Hero Banner with Image & Office Title
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_kua_hero),
                    contentDescription = "KUA Biringbulu Hero",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    KuaGreenPrimaryDarker.copy(alpha = 0.94f)
                                )
                            )
                        )
                )

                // Overlay Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        color = Color(0xFFFFD54F),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "PTSP DIGITAL RESMI KEMENAG",
                            color = Color(0xFF003823),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "KUA KECAMATAN BIRINGBULU",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "Kementerian Agama Kabupaten Gowa • 48 Standar Layanan Publik",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // 2. Quick Key Stats Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatItem(number = "48", label = "Jenis Layanan", color = KuaGreenPrimary)
                    Divider(modifier = Modifier.height(36.dp).width(1.dp), color = Color(0xFFE2E8F0))
                    StatItem(number = "Rp 0,-", label = "Nikah di Balai", color = KuaGoldSecondary)
                    Divider(modifier = Modifier.height(36.dp).width(1.dp), color = Color(0xFFE2E8F0))
                    StatItem(number = "11", label = "Desa / Wilayah", color = Color(0xFF0284C7))
                }
            }
        }

        // 2.5 Quick Kepegawaian & Governance Card Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onNavigateToProfile)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.AccountBalance,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Profil KUA & Tata Kelola Kantor",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Struktur Aparatur, Kepala KUA, Penghulu & Wilayah Desa/Kelurahan",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.5.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Buka Profil KUA",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 3. Quick Tracking Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(KuaGreenPrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.QrCodeScanner,
                                contentDescription = null,
                                tint = KuaGreenPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Lacak Permohonan Berkas Layanan",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Masukkan Kode Tracking (misal: KB-2608-4821) untuk verifikasi progres",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.5.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = quickTrackingCode,
                            onValueChange = { quickTrackingCode = it },
                            placeholder = { Text("Contoh: KB-2608-...", fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("quick_tracking_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = KuaGreenPrimary,
                                unfocusedBorderColor = Color(0xFFCBD5E1)
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                focusManager.clearFocus()
                                if (quickTrackingCode.isNotBlank()) {
                                    onNavigateToTracking(quickTrackingCode)
                                }
                            })
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                if (quickTrackingCode.isNotBlank()) {
                                    onNavigateToTracking(quickTrackingCode)
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.height(52.dp).testTag("quick_tracking_btn")
                        ) {
                            Text("Lacak", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 4. Fast Action Feature Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Menu & Layanan Unggulan",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionGridItem(
                        title = "48 Layanan KUA",
                        desc = "Katalog & Syarat",
                        icon = Icons.Filled.Verified,
                        iconColor = KuaGreenPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToServices(null) },
                        testTag = "action_all_services"
                    )
                    ActionGridItem(
                        title = "Simulasi Biaya Nikah",
                        desc = "KUA vs Luar KUA",
                        icon = Icons.Filled.Calculate,
                        iconColor = KuaGoldSecondary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToSmartSyariah(2) },
                        testTag = "action_marriage_sim"
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionGridItem(
                        title = "Arah Kiblat",
                        desc = "Kompas Presisi",
                        icon = Icons.Filled.CompassCalibration,
                        iconColor = Color(0xFF0284C7),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToSmartSyariah(0) },
                        testTag = "action_qibla"
                    )
                    ActionGridItem(
                        title = "Kalkulator Zakat",
                        desc = "Mal, Profesi, Jagung",
                        icon = Icons.Filled.AccountBalance,
                        iconColor = Color(0xFF059669),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToSmartSyariah(1) },
                        testTag = "action_zakat"
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionGridItem(
                        title = "Tanya Penghulu AI",
                        desc = "Konsultasi Syariah",
                        icon = Icons.Filled.SmartToy,
                        iconColor = Color(0xFF7C3AED),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToSmartSyariah(3) },
                        testTag = "action_ai_consult"
                    )
                    ActionGridItem(
                        title = "Profil 11 Desa",
                        desc = "Imam & Penyuluh",
                        icon = Icons.Filled.LocationOn,
                        iconColor = Color(0xFFE11D48),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToProfile,
                        testTag = "action_profile"
                    )
                }
            }
        }

        // 5. 9 Kategori Layanan Slider
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "9 Bidang Layanan KUA",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    TextButton(onClick = { onNavigateToServices(null) }) {
                        Text("Lihat Semua", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(KuaServiceCategory.values()) { category ->
                        CategoryCardItem(
                            category = category,
                            onClick = { onNavigateToServices(category) }
                        )
                    }
                }
            }
        }

        // 6. Layanan Paling Sering Dicari Warga
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
                Text(
                    text = "Layanan Populer & Pendaftaran Online",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Daftar mandiri dari rumah dengan kelengkapan berkas",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                popularServices.forEach { service ->
                    PopularServiceCard(
                        service = service,
                        onClick = { onNavigateToServiceDetail(service.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // 7. Maklumat Pelayanan Kemenag
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = KuaGoldSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Maklumat Pelayanan Bebas Pungli",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"Dengan ini kami menyatakan sanggup menyelenggarakan pelayanan sesuai standar pelayanan yang telah ditetapkan dan apabila tidak menepati janji ini, kami siap menerima sanksi sesuai perundang-undangan.\"",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Kepala KUA Kecamatan Biringbulu - Kabupaten Gowa",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
        )
    }
}

@Composable
fun ActionGridItem(
    title: String,
    desc: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 13.sp
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun CategoryCardItem(
    category: KuaServiceCategory,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .width(155.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getCategoryIcon(category),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = category.shortName,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 13.sp
            )
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun PopularServiceCard(
    service: KuaServiceItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("popular_service_${service.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#${service.id}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = service.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                    ) {
                        Text(
                            text = service.cost.split("/").firstOrNull() ?: service.cost,
                            color = Color(0xFF15803D),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Detail",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
