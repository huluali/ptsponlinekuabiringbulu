package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.KuaServiceCategory
import com.example.ui.theme.KuaCardBorderLight
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import com.example.ui.theme.KuaGreenPrimaryDarker
import com.example.ui.theme.KuaGreenPrimaryLight

fun getCategoryIcon(category: KuaServiceCategory): ImageVector {
    return when (category) {
        KuaServiceCategory.PELAYANAN_PERNIKAHAN -> Icons.Filled.Favorite
        KuaServiceCategory.BIMBINGAN_PERKAWINAN -> Icons.Filled.Diversity1
        KuaServiceCategory.ZAKAT_DAN_WAKAF -> Icons.Filled.AccountBalance
        KuaServiceCategory.KEMASJIDAN -> Icons.Filled.Mosque
        KuaServiceCategory.DATA_KEAGAMAAN -> Icons.Filled.MenuBook
        KuaServiceCategory.KETATAUSAHAAN -> Icons.Filled.Inventory
        KuaServiceCategory.FUNGSI_TAMBAHAN -> Icons.Filled.Verified
        KuaServiceCategory.KONSULTASI_SYARIAH -> Icons.Filled.HelpOutline
        KuaServiceCategory.PENERANGAN_ISLAM -> Icons.Filled.RecordVoiceOver
    }
}

@Composable
fun KuaHeaderBadge(
    title: String = "KUA KECAMATAN BIRINGBULU",
    subtitle: String = "Kemenag Kab. Gowa • PTSP Digital 48 Layanan Resmi",
    customLogoUri: String? = null,
    onSettingsClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            KuaGreenPrimaryDarker,
                            KuaGreenPrimary,
                            Color(0xFF0F766E)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Official Kemenag Logo Badge (Customizable)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
                    modifier = Modifier.size(46.dp)
                ) {
                    if (!customLogoUri.isNullOrBlank()) {
                        AsyncImage(
                            model = customLogoUri,
                            contentDescription = "Logo Kementerian Agama RI",
                            contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    } else {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_kemenag_logo),
                            contentDescription = "Logo Kementerian Agama RI",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = Color(0xFFFFD54F),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "KEMENAG",
                                color = Color(0xFF003823),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                                fontSize = 9.sp
                            )
                        }
                    }
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.90f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 11.sp
                    )
                }

                if (onSettingsClick != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                            .testTag("header_btn_settings")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountBalance,
                            contentDescription = "Pengaturan Kepegawaian",
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCategoryChip(
    category: KuaServiceCategory?,
    isSelected: Boolean,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFE2E8F0)
        ),
        shadowElevation = if (isSelected) 2.dp else 0.5.dp,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("category_chip_${category?.categoryId ?: "all"}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (category != null) {
                Icon(
                    imageVector = getCategoryIcon(category),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                ),
                fontSize = 12.5.sp
            )
        }
    }
}

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label, borderColor) = when (status) {
        "TERKIRIM" -> Quadruple(Color(0xFFE0F2FE), Color(0xFF0369A1), "Permohonan Diterima", Color(0xFFBAE6FD))
        "VERIFIKASI_BERKAS" -> Quadruple(Color(0xFFFEF3C7), Color(0xFFB45309), "Verifikasi Berkas", Color(0xFFFDE68A))
        "JADWAL_PEMERIKSAAN" -> Quadruple(Color(0xFFEDE9FE), Color(0xFF6D28D9), "Jadwal Pemeriksaan", Color(0xFFDDD6FE))
        "SELESAI" -> Quadruple(Color(0xFFDCFCE7), Color(0xFF15803D), "Selesai / Siap Ambil", Color(0xFFBBF7D0))
        "DITOLAK" -> Quadruple(Color(0xFFFEE2E2), Color(0xFFB91C1C), "Perlu Perbaikan", Color(0xFFFECACA))
        else -> Quadruple(Color(0xFFF1F5F9), Color(0xFF475569), status, Color(0xFFE2E8F0))
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(textColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = textColor,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                fontSize = 11.sp
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun DigitalQrPassMock(
    trackingCode: String,
    serviceTitle: String,
    applicantName: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = null,
                    tint = KuaGreenPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "KARTU BUKTI PENDAFTARAN RESMI",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "KUA KECAMATAN BIRINGBULU - KAB. GOWA",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Simulated Modern QR Matrix with Border
            Box(
                modifier = Modifier
                    .size(136.dp)
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(116.dp)) {
                    val gridSize = 9
                    val cellSize = size.width / gridSize
                    for (row in 0 until gridSize) {
                        for (col in 0 until gridSize) {
                            val isCorner = (row < 3 && col < 3) || (row < 3 && col >= gridSize - 3) || (row >= gridSize - 3 && col < 3)
                            val isBorder = isCorner && (row == 0 || row == 2 || col == 0 || col == 2 ||
                                    row == gridSize - 1 || row == gridSize - 3 || col == gridSize - 1 || col == gridSize - 3 ||
                                    (row == 1 && (col == 0 || col == 2 || col == gridSize - 1 || col == gridSize - 3)) ||
                                    (row == gridSize - 2 && (col == 0 || col == 2)))
                            val isCenterDot = (row == 1 && (col == 1 || col == gridSize - 2)) || (row == gridSize - 2 && col == 1)
                            val isPseudoData = (row + col * 3 + trackingCode.hashCode()) % 3 == 0

                            if (isBorder || isCenterDot || isPseudoData) {
                                drawRect(
                                    color = Color(0xFF0F172A),
                                    topLeft = Offset(col * cellSize, row * cellSize),
                                    size = Size(cellSize * 0.9f, cellSize * 0.9f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Text(
                    text = trackingCode,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = applicantName,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = serviceTitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

