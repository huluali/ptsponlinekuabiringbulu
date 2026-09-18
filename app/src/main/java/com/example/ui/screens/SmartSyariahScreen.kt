package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BiringbuluData
import com.example.data.local.entity.ConsultationEntity
import com.example.util.AiConsultantHelper
import com.example.util.FormatUtils
import com.example.util.PnbpCalculator
import com.example.util.ZakatCalculator
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SmartSyariahScreen(
    initialSubTab: Int = 0,
    deviceAzimuth: Float,
    qiblaBearing: Float,
    onNavigateToServiceDetail: (Int) -> Unit,
    // AI Consultant props
    aiInputText: String,
    onAiInputTextChange: (String) -> Unit,
    aiCurrentResponse: AiConsultantHelper.AiResponse?,
    isAiThinking: Boolean,
    onAskAi: (String) -> Unit,
    consultationHistory: List<ConsultationEntity>,
    onDeleteConsultation: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf(initialSubTab) }
    val tabTitles = listOf("Kompas Kiblat", "Kalkulator Zakat", "Simulasi Nikah", "Tanya AI Syariah")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("smart_syariah_screen")
    ) {
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[activeSubTab]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 3.dp
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = activeSubTab == index,
                    onClick = { activeSubTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (activeSubTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                )
            }
        }

        when (activeSubTab) {
            0 -> QiblaCompassView(
                deviceAzimuth = deviceAzimuth,
                qiblaBearing = qiblaBearing,
                onRequestCalibration = { onNavigateToServiceDetail(45) }
            )
            1 -> ZakatCalculatorView()
            2 -> MarriageFeeSimulationView(
                onApplyMarriage = { onNavigateToServiceDetail(1) }
            )
            3 -> AiConsultantView(
                inputText = aiInputText,
                onInputChange = onAiInputTextChange,
                currentResponse = aiCurrentResponse,
                isThinking = isAiThinking,
                onAsk = onAskAi,
                history = consultationHistory,
                onDeleteHistory = onDeleteConsultation,
                onNavigateToService = onNavigateToServiceDetail
            )
        }
    }
}

// -------------------------------------------------------------
// 1. KOMPAS KIBLAT VIEW
// -------------------------------------------------------------
@Composable
fun QiblaCompassView(
    deviceAzimuth: Float,
    qiblaBearing: Float,
    onRequestCalibration: () -> Unit
) {
    val delta = (qiblaBearing - deviceAzimuth + 360) % 360
    val isAligned = abs(delta) < 4 || abs(delta - 360) < 4

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PENENTU ARAH KIBLAT BIRINGBULU",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Azimuth Ka'bah: ${String.format("%.2f", qiblaBearing)}° Barat Laut (NW)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Koordinat: Kec. Biringbulu, Gowa (${BiringbuluData.COORDINATE_LAT}, ${BiringbuluData.COORDINATE_LNG})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Animated / Sensor Driven Dial
        item {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF002717))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(236.dp)) {
                    val radius = size.minDimension / 2f
                    val centerOffset = Offset(size.width / 2f, size.height / 2f)

                    // Draw Dial Ring & Degrees
                    drawCircle(
                        color = Color(0xFF0A4D30),
                        radius = radius,
                        style = Stroke(width = 6.dp.toPx())
                    )

                    // Draw 8 Compass Cardinal ticks
                    for (i in 0 until 360 step 30) {
                        val angleRad = Math.toRadians((i - 90).toDouble())
                        val tickLen = if (i % 90 == 0) 14.dp.toPx() else 8.dp.toPx()
                        val startX = centerOffset.x + (radius - tickLen) * cos(angleRad).toFloat()
                        val startY = centerOffset.y + (radius - tickLen) * sin(angleRad).toFloat()
                        val endX = centerOffset.x + radius * cos(angleRad).toFloat()
                        val endY = centerOffset.y + radius * sin(angleRad).toFloat()

                        drawLine(
                            color = if (i % 90 == 0) KuaGoldSecondary else Color.White.copy(alpha = 0.4f),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = if (i % 90 == 0) 3.dp.toPx() else 1.5.dp.toPx()
                        )
                    }

                    // Rotate needle according to device azimuth
                    rotate(degrees = -deviceAzimuth, pivot = centerOffset) {
                        // North needle (Red/Gold)
                        val northPath = Path().apply {
                            moveTo(centerOffset.x, centerOffset.y - radius + 20.dp.toPx())
                            lineTo(centerOffset.x - 12.dp.toPx(), centerOffset.y)
                            lineTo(centerOffset.x + 12.dp.toPx(), centerOffset.y)
                            close()
                        }
                        drawPath(northPath, color = Color(0xFFE53935))

                        // South needle (Silver)
                        val southPath = Path().apply {
                            moveTo(centerOffset.x, centerOffset.y + radius - 20.dp.toPx())
                            lineTo(centerOffset.x - 12.dp.toPx(), centerOffset.y)
                            lineTo(centerOffset.x + 12.dp.toPx(), centerOffset.y)
                            close()
                        }
                        drawPath(southPath, color = Color(0xFFB0BEC5))
                    }

                    // Draw Qibla Arrow target
                    val qiblaAngleRad = Math.toRadians((qiblaBearing - deviceAzimuth - 90).toDouble())
                    val qiblaX = centerOffset.x + (radius - 28.dp.toPx()) * cos(qiblaAngleRad).toFloat()
                    val qiblaY = centerOffset.y + (radius - 28.dp.toPx()) * sin(qiblaAngleRad).toFloat()

                    drawCircle(
                        color = KuaGoldSecondary,
                        radius = 12.dp.toPx(),
                        center = Offset(qiblaX, qiblaY)
                    )
                }

                // Center Indicator
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(if (isAligned) Color(0xFF2E7D32) else Color(0xFF0A4D30)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mosque,
                        contentDescription = "Ka'bah",
                        tint = KuaGoldSecondary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        item {
            Surface(
                color = if (isAligned) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Navigation,
                        contentDescription = null,
                        tint = if (isAligned) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(delta)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isAligned) "Arah Kiblat Tepat! (Alhamdulillah)" else "Posisikan jarum ke arah ikon Masjid",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isAligned) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Arah ponsel saat ini: ${String.format("%.1f", deviceAzimuth)}° | Target Kiblat: ${String.format("%.1f", qiblaBearing)}°",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Layanan Kalibrasi Arah Kiblat Resmi KUA",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Untuk pembangunan masjid baru, renovasi musala, atau verifikasi arah saf di 11 Desa Biringbulu menggunakan alat ukur theodolite / kompas falak Kemenag.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onRequestCalibration,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Filled.CompassCalibration, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ajukan Pengukuran / Kalibrasi Kiblat (#45)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. KALKULATOR ZAKAT VIEW
// -------------------------------------------------------------
@Composable
fun ZakatCalculatorView() {
    var selectedZakatType by remember { mutableStateOf(0) } // 0: Maal, 1: Profesi, 2: Pertanian (Padi/Jagung)

    // Maal Inputs
    var cashStr by remember { mutableStateOf("120000000") }
    var goldStr by remember { mutableStateOf("0") }
    var debtStr by remember { mutableStateOf("0") }

    // Profesi Inputs
    var salaryStr by remember { mutableStateOf("10000000") }
    var additionalSalaryStr by remember { mutableStateOf("0") }
    var needsStr by remember { mutableStateOf("0") }

    // Pertanian Inputs
    var yieldKgStr by remember { mutableStateOf("1500") }
    var pricePerKgStr by remember { mutableStateOf("5000") }
    var isPaidIrrigation by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Tab Selector for Zakat Type
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Zakat Mal / Emas", "Zakat Profesi", "Zakat Jagung / Padi").forEachIndexed { index, label ->
                    FilterChip(
                        selected = selectedZakatType == index,
                        onClick = { selectedZakatType = index },
                        label = { Text(label, fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        when (selectedZakatType) {
            0 -> {
                // ZAKAT MAAL / EMAS
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Perhitungan Zakat Harta (Maal / Emas)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Nisab: 85 gram emas murni x Rp 1.350.000 = Rp 114.750.000,- (Haul 1 Tahun, Tarif 2,5%)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = cashStr,
                                onValueChange = { cashStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Tabungan / Kas / Deposito (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = goldStr,
                                onValueChange = { goldStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Nilai Emas / Logam Mulia Tersimpan (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = debtStr,
                                onValueChange = { debtStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Hutang Jatuh Tempo Yang Mengurangi Harta (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                item {
                    val cash = cashStr.toDoubleOrNull() ?: 0.0
                    val gold = goldStr.toDoubleOrNull() ?: 0.0
                    val debt = debtStr.toDoubleOrNull() ?: 0.0
                    val result = ZakatCalculator.calculateZakatMal(cash, gold, 0.0, debt)

                    ZakatResultCard(
                        isWajib = result.isWajibZakat,
                        zakatAmountRupiah = FormatUtils.formatRupiah(result.zakatAmount),
                        explanation = result.explanation,
                        categoryTitle = "Zakat Maal"
                    )
                }
            }

            1 -> {
                // ZAKAT PROFESI
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Perhitungan Zakat Penghasilan / Profesi",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Nisab: Setara 85 gr emas/tahun (~Rp 9.562.500/bulan). Dikeluarkan setiap menerima gaji (Tarif 2,5%).",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = salaryStr,
                                onValueChange = { salaryStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Gaji Pokok Bulanan (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = additionalSalaryStr,
                                onValueChange = { additionalSalaryStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Tunjangan / Penghasilan Lain Bulanan (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = needsStr,
                                onValueChange = { needsStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Kebutuhan Pokok / Cicilan Mendesak (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                item {
                    val salary = salaryStr.toDoubleOrNull() ?: 0.0
                    val addSalary = additionalSalaryStr.toDoubleOrNull() ?: 0.0
                    val needs = needsStr.toDoubleOrNull() ?: 0.0
                    val result = ZakatCalculator.calculateZakatProfesi(salary, addSalary, needs)

                    ZakatResultCard(
                        isWajib = result.isWajibZakat,
                        zakatAmountRupiah = FormatUtils.formatRupiah(result.zakatAmount),
                        explanation = result.explanation,
                        categoryTitle = "Zakat Profesi Bulanan"
                    )
                }
            }

            2 -> {
                // ZAKAT PERTANIAN (JAGUNG / PADI BIRINGBULU)
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Zakat Pertanian (Jagung & Padi Biringbulu)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Nisab: 5 Wasaq = 653 kg gabah/jagung pipil kering. Wajib ditunaikan seketika saat panen.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = yieldKgStr,
                                onValueChange = { yieldKgStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Total Hasil Panen (Kilogram)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = pricePerKgStr,
                                onValueChange = { pricePerKgStr = it.filter { c -> c.isDigit() } },
                                label = { Text("Harga Pasar per Kg (Rp)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isPaidIrrigation) "Irigasi Berbayar / Pompa (Tarif 5%)" else "Tadah Hujan / Alami (Tarif 10%)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = "Ubah switch jika menggunakan pompa air/biaya irigasi",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = isPaidIrrigation,
                                    onCheckedChange = { isPaidIrrigation = it }
                                )
                            }
                        }
                    }
                }

                item {
                    val yieldKg = yieldKgStr.toDoubleOrNull() ?: 0.0
                    val priceKg = pricePerKgStr.toDoubleOrNull() ?: 0.0
                    val result = ZakatCalculator.calculateZakatPertanian(yieldKg, priceKg, isPaidIrrigation)

                    ZakatResultCard(
                        isWajib = result.isWajibZakat,
                        zakatAmountRupiah = "${String.format("%.1f", result.zakatKg)} Kg (${FormatUtils.formatRupiah(result.zakatRupiah)})",
                        explanation = result.explanation,
                        categoryTitle = "Zakat Hasil Panen (${result.irrigationType})"
                    )
                }
            }
        }
    }
}

@Composable
fun ZakatResultCard(
    isWajib: Boolean,
    zakatAmountRupiah: String,
    explanation: String,
    categoryTitle: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWajib) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryTitle,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isWajib) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    color = if (isWajib) Color(0xFF2E7D32) else Color(0xFF757575),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = if (isWajib) "WAJIB ZAKAT" else "BELUM WAJIB",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        fontSize = 10.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isWajib) zakatAmountRupiah else "Rp 0,-",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = if (isWajib) Color(0xFF1B5E20) else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = explanation,
                style = MaterialTheme.typography.bodySmall,
                color = if (isWajib) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Penyaluran zakat dapat ditunaikan melalui UPZ KUA Biringbulu untuk disalurkan kepada 8 asnaf di wilayah desa Biringbulu.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -------------------------------------------------------------
// 3. SIMULASI BIAYA & PNBP NIKAH VIEW
// -------------------------------------------------------------
@Composable
fun MarriageFeeSimulationView(
    onApplyMarriage: () -> Unit
) {
    var isAtKua by remember { mutableStateOf(true) }
    var isWorkHours by remember { mutableStateOf(true) }
    var hasSktm by remember { mutableStateOf(false) }

    val simulationResult = remember(isAtKua, isWorkHours, hasSktm) {
        PnbpCalculator.calculateMarriageCost(isAtKua, isWorkHours, hasSktm)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Simulasi PNBP & Biaya Nikah Resmi",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Berdasarkan PP No. 59 Tahun 2014 & PMA No. 20 Tahun 2019",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Divider()

                    // Pilihan Lokasi
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Lokasi Akad Nikah", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            Text(
                                text = if (isAtKua) "Di Balai Nikah KUA Biringbulu" else "Di Luar KUA (Rumah/Masjid)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Switch(checked = isAtKua, onCheckedChange = { isAtKua = it })
                    }

                    if (isAtKua) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Waktu Pelaksanaan", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Text(
                                    text = if (isWorkHours) "Hari & Jam Kerja (Senin-Jumat)" else "Hari Libur / Sabtu-Minggu",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Switch(checked = isWorkHours, onCheckedChange = { isWorkHours = it })
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Fasilitas Warga Tidak Mampu (SKTM)", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            Text(
                                text = if (hasSktm) "Ada SKTM Resmi dari Kantor Desa" else "Tidak Menggunakan SKTM",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Switch(checked = hasSktm, onCheckedChange = { hasSktm = it })
                    }
                }
            }
        }

        // Output Result Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (simulationResult.totalCost == 0.0) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ESTIMASI BIAYA RESMI NEGARA:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp),
                        color = if (simulationResult.totalCost == 0.0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = FormatUtils.formatRupiah(simulationResult.totalCost),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = if (simulationResult.totalCost == 0.0) Color(0xFF1B5E20) else MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = simulationResult.paymentMethod,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color.White.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = simulationResult.tips,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dokumen Yang Wajib Disiapkan:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    simulationResult.requiredDocuments.forEach { doc ->
                        Text("• $doc", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onApplyMarriage,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Daftar Nikah Online Sekarang (#1)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. TANYA PENGHULU AI VIEW
// -------------------------------------------------------------
@Composable
fun AiConsultantView(
    inputText: String,
    onInputChange: (String) -> Unit,
    currentResponse: AiConsultantHelper.AiResponse?,
    isThinking: Boolean,
    onAsk: (String) -> Unit,
    history: List<ConsultationEntity>,
    onDeleteHistory: (Long) -> Unit,
    onNavigateToService: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val quickChips = listOf(
        "Syarat pendaftaran nikah",
        "Biaya nikah di luar KUA",
        "Buku nikah hilang",
        "Cara ikrar wakaf tanah",
        "Daftar ID SIMAS masjid",
        "Hitung zakat pertanian jagung",
        "Sertifikat halal UMKM gratis"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // AI Intro Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SmartToy,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Asisten Syariah KUA Biringbulu",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Konsultasi hukum munakahat, mawaris, wakaf, zakat, dan 48 layanan KUA 24 jam",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Quick Topic Chips
        item {
            Text(
                text = "Topik Populer Cepat:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quickChips) { chip ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onAsk(chip) }
                    ) {
                        Text(
                            text = chip,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Input Field
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputChange,
                        placeholder = { Text("Tanyakan apa saja seputar nikah, zakat, wakaf, masjid...", fontSize = 13.sp) },
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_input_text"),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            focusManager.clearFocus()
                            onAsk(inputText)
                        })
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            onAsk(inputText)
                        },
                        enabled = inputText.isNotBlank() && !isThinking,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isThinking) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sedang Menjawab...")
                        } else {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Kirim Pertanyaan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Active AI Response Card
        currentResponse?.let { resp ->
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.SmartToy,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = resp.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resp.summary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        resp.details.forEach { detail ->
                            Text(
                                text = detail,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        if (resp.relatedServiceId != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { onNavigateToService(resp.relatedServiceId) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Lihat Detail Layanan #${resp.relatedServiceId}")
                            }
                        }

                        if (resp.suggestedQuestions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Pertanyaan Lanjutan Terkait:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            resp.suggestedQuestions.forEach { suggestion ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onAsk(suggestion) }
                                ) {
                                    Text(
                                        text = "• $suggestion",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Riwayat Konsultasi
        if (history.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Riwayat Tanya Jawab Sebelumnya",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("(${history.size})", style = MaterialTheme.typography.bodySmall)
                }
            }

            items(history, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Q: ${item.question}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { onDeleteHistory(item.id) }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Filled.Delete, contentDescription = "Hapus", modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.answer,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3
                        )
                    }
                }
            }
        }
    }
}
