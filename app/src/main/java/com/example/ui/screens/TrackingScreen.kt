package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.ui.components.DigitalQrPassMock
import com.example.ui.components.StatusBadge
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.material.icons.filled.AdminPanelSettings
import com.example.ui.components.AdminVerificationModal

@Composable
fun TrackingScreen(
    trackingInput: String,
    onTrackingInputChange: (String) -> Unit,
    searchedApplication: ServiceApplicationEntity?,
    hasSearched: Boolean,
    onSearchCode: (String) -> Unit,
    onClearSearch: () -> Unit,
    allApplications: List<ServiceApplicationEntity>,
    onDeleteApplication: (Long) -> Unit,
    onUpdateStatus: (Long, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAppForModal by remember { mutableStateOf<ServiceApplicationEntity?>(null) }
    var isAdminModalOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("tracking_screen")
    ) {
        // Officer Admin Quick Banner
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.AdminPanelSettings,
                        contentDescription = null,
                        tint = KuaGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Meja Petugas Verifikasi PTSP",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Kelola & perbarui status berkas masuk online",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                            fontSize = 10.5.sp
                        )
                    }
                }

                Button(
                    onClick = { isAdminModalOpen = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("open_admin_verification_btn")
                ) {
                    Icon(imageVector = Icons.Filled.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Buka Verifikator", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Divider(color = Color(0xFFE2E8F0))

        // Search bar for Tracking Code
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Lacak Posisi Berkas Layanan",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Cek progres verifikasi berkas pendaftaran Anda secara real-time",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = trackingInput,
                        onValueChange = onTrackingInputChange,
                        placeholder = { Text("Contoh: KB-2608-1234", fontSize = 13.sp) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        trailingIcon = {
                            if (trackingInput.isNotEmpty()) {
                                IconButton(onClick = onClearSearch) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Hapus")
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedBorderColor = KuaGreenPrimary,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("tracking_code_input")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onSearchCode(trackingInput) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .height(54.dp)
                            .testTag("tracking_search_btn")
                    ) {
                        Text("Cari", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Search Result Section
            if (hasSearched) {
                item {
                    if (searchedApplication != null) {
                        TrackingDetailCard(
                            application = searchedApplication,
                            onShowDigitalPass = { selectedAppForModal = searchedApplication }
                        )
                    } else {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                            border = BorderStroke(1.dp, Color(0xFFFECACA)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = Color(0xFFDC2626),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Kode Tracking Tidak Ditemukan",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFFDC2626)
                                    )
                                    Text(
                                        text = "Pastikan format kode benar (contoh: KB-2608-XXXX) atau periksa riwayat permohonan di bawah.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF991B1B)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // History / List of Local Applications
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Riwayat Permohonan di Perangkat Ini",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Text(
                        text = "(${allApplications.size})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (allApplications.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.History,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Belum Ada Riwayat Permohonan",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Ajukan salah satu dari 48 layanan KUA Biringbulu untuk melihat progres tracking di sini.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(allApplications, key = { it.id }) { app ->
                    ApplicationHistoryItem(
                        application = app,
                        onClick = { selectedAppForModal = app },
                        onDelete = { onDeleteApplication(app.id) }
                    )
                }
            }
        }
    }

            // Modal Digital QR Pass / Detail
    selectedAppForModal?.let { app ->
        AlertDialog(
            onDismissRequest = { selectedAppForModal = null },
            title = {
                Text(
                    text = "Bukti Pendaftaran Digital",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DigitalQrPassMock(
                        trackingCode = app.trackingCode,
                        serviceTitle = app.serviceTitle,
                        applicantName = app.applicantName
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("• Desa: ${app.applicantVillage}, Biringbulu", style = MaterialTheme.typography.bodySmall)
                    Text("• NIK: ${app.applicantNik}", style = MaterialTheme.typography.bodySmall)
                    Text("• Jadwal: ${app.appointmentDate}", style = MaterialTheme.typography.bodySmall)
                    Text("• Catatan Petugas: ${app.statusNotes}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    if (app.uploadedDocuments.isNotBlank()) {
                        val docList = app.uploadedDocuments.split("|||").filter { it.isNotBlank() }
                        Text("• Lampiran Berkas (${docList.size} dokumen):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = KuaGreenPrimary)
                        docList.forEach { rawDoc ->
                            val parts = rawDoc.split(":::")
                            val docName = parts.getOrNull(2) ?: parts.getOrNull(1) ?: rawDoc
                            Text("  ✓ $docName", style = MaterialTheme.typography.bodySmall, fontSize = 11.5.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedAppForModal = null }) {
                    Text("Tutup")
                }
            }
        )
    }

    // Admin / Petugas Verification Modal
    AdminVerificationModal(
        isOpen = isAdminModalOpen,
        onDismiss = { isAdminModalOpen = false },
        applications = allApplications,
        onUpdateStatus = onUpdateStatus
    )
}

@Composable
fun TrackingDetailCard(
    application: ServiceApplicationEntity,
    onShowDigitalPass: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = application.trackingCode,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                StatusBadge(status = application.status)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = application.serviceTitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Pemohon: ${application.applicantName} • ${application.applicantVillage}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step Progress Timeline
            TrackingStepper(currentStatus = application.status)

            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Keterangan Petugas: ${application.statusNotes}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (application.uploadedDocuments.isNotBlank()) {
                val context = androidx.compose.ui.platform.LocalContext.current
                Spacer(modifier = Modifier.height(10.dp))
                val docList = application.uploadedDocuments.split("|||").filter { it.isNotBlank() }
                Surface(
                    color = Color(0xFFF0FDF4),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Berkas Persyaratan Terlampir (${docList.size}):",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF15803D)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        docList.forEach { rawDoc ->
                            val parts = rawDoc.split(":::")
                            val uriString = parts.getOrNull(0) ?: ""
                            val reqName = parts.getOrNull(1) ?: ""
                            val fileName = parts.getOrNull(2) ?: rawDoc
                            val fileSize = parts.getOrNull(3) ?: ""

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "• ${if (reqName.isNotBlank()) "$reqName: " else ""}$fileName",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF166534)
                                    )
                                    if (fileSize.isNotBlank()) {
                                        Text(
                                            text = "  Ukuran: $fileSize",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 10.sp,
                                            color = Color(0xFF15803D).copy(alpha = 0.8f)
                                        )
                                    }
                                }
                                if (uriString.startsWith("content://") || uriString.startsWith("file://")) {
                                    IconButton(
                                        onClick = {
                                            try {
                                                val viewIntent = Intent(Intent.ACTION_VIEW).apply {
                                                    setDataAndType(Uri.parse(uriString), "*/*")
                                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                }
                                                context.startActivity(Intent.createChooser(viewIntent, "Buka / Unduh $fileName"))
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Berkas $fileName: ${e.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Share,
                                            contentDescription = "Buka / Unduh Berkas",
                                            tint = Color(0xFF15803D),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onShowDigitalPass,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.QrCode, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Buka Kartu Bukti Pendaftaran Digital", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun TrackingStepper(currentStatus: String) {
    val steps = listOf(
        "Permohonan Diterima",
        "Verifikasi Berkas",
        "Pemeriksaan / Jadwal",
        "Selesai / Pengambilan"
    )

    val stepIndex = when (currentStatus) {
        "TERKIRIM" -> 0
        "VERIFIKASI_BERKAS" -> 1
        "JADWAL_PEMERIKSAAN" -> 2
        "SELESAI" -> 3
        else -> 0
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        steps.forEachIndexed { index, stepName ->
            val isDone = index <= stepIndex
            val isCurrent = index == stepIndex

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(
                            if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stepName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = if (isDone) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ApplicationHistoryItem(
    application: ServiceApplicationEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = remember(application.createdAtTimestamp) {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date(application.createdAtTimestamp))
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = application.trackingCode,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    StatusBadge(status = application.status)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = application.serviceTitle,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1
                )
                Text(
                    text = "Pemohon: ${application.applicantName} • Diajukan: $dateStr",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Hapus Riwayat",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

