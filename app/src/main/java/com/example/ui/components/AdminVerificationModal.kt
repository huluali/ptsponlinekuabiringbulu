package com.example.ui.components

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminVerificationModal(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    applications: List<ServiceApplicationEntity>,
    onUpdateStatus: (id: Long, newStatus: String, notes: String) -> Unit
) {
    if (!isOpen) return

    val context = LocalContext.current
    var selectedStatusFilter by remember { mutableStateOf("SEMUA") }
    var selectedAppForDetail by remember { mutableStateOf<ServiceApplicationEntity?>(null) }

    val filterOptions = listOf(
        "SEMUA" to "Semua (${applications.size})",
        "TERKIRIM" to "Baru Masuk (${applications.count { it.status.uppercase() == "TERKIRIM" }})",
        "VERIFIKASI_BERKAS" to "Proses Verifikasi (${applications.count { it.status.uppercase() == "VERIFIKASI_BERKAS" }})",
        "JADWAL_PEMERIKSAAN" to "Pemeriksaan/Jadwal (${applications.count { it.status.uppercase() == "JADWAL_PEMERIKSAAN" }})",
        "SELESAI" to "Selesai (${applications.count { it.status.uppercase() == "SELESAI" }})",
        "DITOLAK" to "Ditolak/Revisi (${applications.count { it.status.uppercase() == "DITOLAK" }})"
    )

    val filteredApplications = remember(selectedStatusFilter, applications) {
        if (selectedStatusFilter == "SEMUA") {
            applications
        } else {
            applications.filter { it.status.equals(selectedStatusFilter, ignoreCase = true) }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.background,
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Modal Top Bar
                Surface(
                    color = KuaGreenPrimary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Meja Petugas Verifikator PTSP",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Verifikasi Berkas & Update Status Pengajuan Online KUA",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Tutup",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Filter Tabs Row
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterOptions) { (key, label) ->
                        val isSelected = selectedStatusFilter == key
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedStatusFilter = key },
                            label = { Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = KuaGreenPrimary,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Divider(color = Color(0xFFE2E8F0))

                // Application List
                if (filteredApplications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.AssignmentTurnedIn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Tidak Ada Ajuan Pada Filter Ini",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Pilih filter status lainnya atau tunggu berkas ajuan online masuk.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredApplications, key = { it.id }) { app ->
                            AdminApplicationCard(
                                app = app,
                                onClick = { selectedAppForDetail = app }
                            )
                        }
                    }
                }

                // Footer Info
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total ${applications.size} Berkas Terdaftar di Sistem",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.5.sp
                        )
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Tutup", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }

    // Detail & Status Editor Modal for Selected Application
    selectedAppForDetail?.let { app ->
        AdminApplicationDetailDialog(
            app = app,
            onDismiss = { selectedAppForDetail = null },
            onSaveStatus = { newStatus, notes ->
                onUpdateStatus(app.id, newStatus, notes)
                selectedAppForDetail = null
                Toast.makeText(context, "Status ajuan ${app.trackingCode} berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun AdminApplicationCard(
    app: ServiceApplicationEntity,
    onClick: () -> Unit
) {
    val dateStr = try {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date(app.createdAtTimestamp))
    } catch (e: Exception) {
        ""
    }

    val docCount = if (app.uploadedDocuments.isBlank()) 0 else app.uploadedDocuments.split("|||").filter { it.isNotBlank() }.size

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = KuaGreenPrimary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = app.trackingCode,
                        color = KuaGreenPrimary,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                StatusBadge(status = app.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = app.serviceTitle,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Person, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${app.applicantName} (NIK: ${app.applicantNik})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.LocationCity, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Desa ${app.applicantVillage} • Telp/WA: ${app.applicantPhone}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (docCount > 0) Icons.Filled.CheckCircle else Icons.Filled.Info,
                        contentDescription = null,
                        tint = if (docCount > 0) Color(0xFF16A34A) else Color(0xFFD97706),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (docCount > 0) "$docCount Berkas Terunggah" else "Tanpa Berkas Digital",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (docCount > 0) Color(0xFF16A34A) else Color(0xFFB45309),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Verifikasi",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = KuaGreenPrimary
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = KuaGreenPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminApplicationDetailDialog(
    app: ServiceApplicationEntity,
    onDismiss: () -> Unit,
    onSaveStatus: (newStatus: String, notes: String) -> Unit
) {
    val context = LocalContext.current

    val statusChoices = listOf(
        "TERKIRIM" to "TERKIRIM (Pengajuan Baru Masuk)",
        "VERIFIKASI_BERKAS" to "VERIFIKASI BERKAS (Sedang Divalidasi)",
        "JADWAL_PEMERIKSAAN" to "JADWAL PEMERIKSAAN (Lengkap & Dijadwalkan)",
        "SELESAI" to "SELESAI (Layanan Tuntas Diterbitkan)",
        "DITOLAK" to "DITOLAK / REVISI (Dokumen Tidak Lengkap)"
    )

    var currentStatus by remember { mutableStateOf(app.status.uppercase()) }
    var currentNotes by remember { mutableStateOf(app.statusNotes) }
    var isStatusDropdownOpen by remember { mutableStateOf(false) }

    val docList = remember(app.uploadedDocuments) {
        if (app.uploadedDocuments.isBlank()) emptyList()
        else app.uploadedDocuments.split("|||").filter { it.isNotBlank() }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Surface(
                    color = KuaGreenPrimary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Lembar Verifikasi Berkas #${app.trackingCode}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = app.serviceTitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Filled.Close, contentDescription = "Tutup", tint = Color.White)
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Applicant Dossier Card
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "1. Data Identitas Pemohon:",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = KuaGreenPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("• Nama Lengkap: ${app.applicantName}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                                Text("• NIK: ${app.applicantNik}", style = MaterialTheme.typography.bodySmall)
                                Text("• WhatsApp / Telp: ${app.applicantPhone}", style = MaterialTheme.typography.bodySmall)
                                Text("• Desa Asal: Desa ${app.applicantVillage}, Kec. Biringbulu", style = MaterialTheme.typography.bodySmall)
                                if (app.applicantAddress.isNotBlank()) {
                                    Text("• Alamat Dusun: ${app.applicantAddress}", style = MaterialTheme.typography.bodySmall)
                                }
                                Text("• Rencana Janji Temu: ${app.appointmentDate}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                if (app.notes.isNotBlank()) {
                                    Text("• Catatan Pemohon: \"${app.notes}\"", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }

                    // Uploaded Documents Section
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "2. Dokumen Persyaratan Digital (${docList.size} Terlampir):",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = KuaGreenPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                if (docList.isEmpty()) {
                                    Surface(
                                        color = Color(0xFFFEF3C7),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.Info, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Pemohon memilih membawa berkas fisik langsung ke loket PTSP KUA Biringbulu.",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF92400E),
                                                fontSize = 11.5.sp
                                            )
                                        }
                                    }
                                } else {
                                    docList.forEachIndexed { idx, rawDoc ->
                                        val parts = rawDoc.split(":::")
                                        val uriString = parts.getOrNull(0) ?: ""
                                        val reqName = parts.getOrNull(1) ?: "Persyaratan #${idx + 1}"
                                        val fileName = parts.getOrNull(2) ?: rawDoc
                                        val fileSize = parts.getOrNull(3) ?: "Siap diverifikasi"

                                        Surface(
                                            color = Color(0xFFF8FAFC),
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFDCFCE7)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Check,
                                                        contentDescription = null,
                                                        tint = Color(0xFF16A34A),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = reqName,
                                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                    Text(
                                                        text = "$fileName ($fileSize)",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Color(0xFF15803D),
                                                        fontSize = 11.sp
                                                    )
                                                }
                                                // Buka / Unduh Berkas Button
                                                OutlinedButton(
                                                    onClick = {
                                                        try {
                                                            if (uriString.startsWith("content://") || uriString.startsWith("file://")) {
                                                                val viewIntent = Intent(Intent.ACTION_VIEW).apply {
                                                                    setDataAndType(Uri.parse(uriString), "*/*")
                                                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                                }
                                                                context.startActivity(Intent.createChooser(viewIntent, "Buka / Unduh $fileName"))
                                                            } else {
                                                                Toast.makeText(context, "Berkas $fileName tersimpan di Database PTSP KUA Biringbulu", Toast.LENGTH_LONG).show()
                                                            }
                                                        } catch (e: Exception) {
                                                            Toast.makeText(context, "Berkas $fileName siap di loket verifikasi: ${e.message}", Toast.LENGTH_SHORT).show()
                                                        }
                                                    },
                                                    shape = RoundedCornerShape(8.dp),
                                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                                ) {
                                                    Icon(Icons.Filled.Share, contentDescription = "Unduh / Buka", modifier = Modifier.size(14.dp))
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text("Buka / Unduh", fontSize = 11.sp)
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Surface(
                                        color = Color(0xFFEFF6FF),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Filled.Info, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Lokasi Penyimpanan: Seluruh berkas digital & identitas pemohon tersimpan di penyimpanan internal aman (SAF / Room Database Lokal PTSP KUA) dan dapat dibuka langsung saat verifikasi berkas.",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF1E40AF),
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Action: Change Status & Catatan Verifikator
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, KuaGoldSecondary.copy(alpha = 0.7f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "3. Keputusan Verifikasi Petugas:",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Status Dropdown
                                ExposedDropdownMenuBox(
                                    expanded = isStatusDropdownOpen,
                                    onExpandedChange = { isStatusDropdownOpen = !isStatusDropdownOpen },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = statusChoices.firstOrNull { it.first == currentStatus }?.second ?: currentStatus,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Pilih Status Terkini *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isStatusDropdownOpen) },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = isStatusDropdownOpen,
                                        onDismissRequest = { isStatusDropdownOpen = false }
                                    ) {
                                        statusChoices.forEach { (code, label) ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = label,
                                                        fontWeight = if (currentStatus == code) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                },
                                                onClick = {
                                                    currentStatus = code
                                                    isStatusDropdownOpen = false
                                                    // Set template note based on status
                                                    when (code) {
                                                        "VERIFIKASI_BERKAS" -> currentNotes = "Berkas persyaratan sedang divalidasi oleh petugas verifikator KUA Biringbulu."
                                                        "JADWAL_PEMERIKSAAN" -> currentNotes = "Berkas dinyatakan LENGKAP & VALID. Pemohon dijadwalkan hadir ke KUA Biringbulu pada ${app.appointmentDate} pukul 09.00 WITA."
                                                        "SELESAI" -> currentNotes = "Layanan telah tuntas diproses dan dokumen resmi telah diterbitkan oleh KUA Biringbulu."
                                                        "DITOLAK" -> currentNotes = "Mohon maaf, berkas belum lengkap. Harap melengkapi dokumen persyaratan yang kurang ke loket PTSP KUA Biringbulu."
                                                        else -> currentNotes = "Permohonan berhasil didaftarkan di sistem digital KUA Biringbulu."
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = currentNotes,
                                    onValueChange = { currentNotes = it },
                                    label = { Text("Catatan / Instruksi Petugas untuk Pemohon *") },
                                    placeholder = { Text("Tuliskan arahan, jadwal kehadiran, atau kekurangan berkas...") },
                                    maxLines = 4,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Direct WhatsApp Notify Action
                    item {
                        OutlinedButton(
                            onClick = {
                                val cleanPhone = app.applicantPhone.replace("+", "").replace(" ", "").replace("-", "").let {
                                    if (it.startsWith("0")) "62" + it.substring(1) else it
                                }
                                val statusLabel = statusChoices.firstOrNull { it.first == currentStatus }?.second ?: currentStatus
                                val message = """
                                    *PEMBERITAHUAN RESMI KUA BIRINGBULU*
                                    
                                    Yth. Bapak/Ibu *${app.applicantName}*,
                                    Pengajuan online Anda untuk layanan *${app.serviceTitle}* dengan Kode Tracking: *${app.trackingCode}* saat ini berstatus:
                                    
                                    📋 *STATUS:* $statusLabel
                                    📝 *CATATAN PETUGAS:*
                                    $currentNotes
                                    
                                    📍 *Lokasi:* KUA Kecamatan Biringbulu, Kab. Gowa
                                    Terima kasih.
                                """.trimIndent()

                                val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Aplikasi WhatsApp tidak ditemukan", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF16A34A)),
                            border = BorderStroke(1.dp, Color(0xFF16A34A)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Kirim Notifikasi WA ke Pemohon (${app.applicantPhone})", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Dialog Action Buttons
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Batal")
                        }

                        Button(
                            onClick = {
                                onSaveStatus(currentStatus, currentNotes)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                            modifier = Modifier.weight(1.5f)
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Simpan Status", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
