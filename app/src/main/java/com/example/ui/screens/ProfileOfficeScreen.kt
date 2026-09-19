package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.BiringbuluData
import com.example.data.VillageItem
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.model.KuaStaff
import com.example.model.StaffRole
import com.example.ui.components.AdminVerificationModal
import com.example.ui.components.EditStaffDialog
import com.example.ui.components.OfficeGovernanceCard
import com.example.ui.components.StaffDetailDialog
import com.example.ui.components.StaffMemberCard
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary

@Composable
fun ProfileOfficeScreen(
    staffList: List<KuaStaff>,
    onSaveStaff: (KuaStaff) -> Unit,
    allApplications: List<ServiceApplicationEntity> = emptyList(),
    onUpdateStatus: (Long, String, String) -> Unit = { _, _, _ -> },
    customLogoUri: String? = null,
    onUpdateCustomLogoUri: (String?) -> Unit = {},
    onNavigateToIkmSurvey: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var selectedRoleFilter by remember { mutableStateOf<StaffRole?>(null) }
    var selectedStaffForDetail by remember { mutableStateOf<KuaStaff?>(null) }
    var selectedStaffForEdit by remember { mutableStateOf<KuaStaff?>(null) }
    var isAdminVerificationOpen by remember { mutableStateOf(false) }
    var isLogoUploadDialogOpen by remember { mutableStateOf(false) }

    val logoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            onUpdateCustomLogoUri(uri.toString())
            Toast.makeText(context, "Logo Kemenag / Kantor berhasil diunggah & diperbarui!", Toast.LENGTH_SHORT).show()
        }
    }

    val filteredStaff = remember(staffList, selectedRoleFilter) {
        if (selectedRoleFilter == null) {
            staffList
        } else {
            staffList.filter { it.role == selectedRoleFilter }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_office_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Office Banner Card with Customizable Logo
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Official Kemenag Logo Badge (Clickable to change/upload logo)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { isLogoUploadDialogOpen = true }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White,
                                border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
                                modifier = Modifier.size(56.dp)
                            ) {
                                if (!customLogoUri.isNullOrBlank()) {
                                    AsyncImage(
                                        model = customLogoUri,
                                        contentDescription = "Logo Kemenag RI",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp)
                                    )
                                } else {
                                    androidx.compose.foundation.Image(
                                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_kemenag_logo),
                                        contentDescription = "Logo Kemenag RI",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp)
                                    )
                                }
                            }
                            // Upload badge overlay
                            Surface(
                                color = Color(0xFFFFD54F),
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.BottomEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CameraAlt,
                                    contentDescription = "Ubah Logo",
                                    tint = Color(0xFF003823),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "KANTOR URUSAN AGAMA",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = Color.White
                            )
                            Text(
                                text = "Kecamatan Biringbulu - Kab. Gowa",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        // Upload Logo Action Button
                        OutlinedButton(
                            onClick = { isLogoUploadDialogOpen = true },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0xFFFFD54F)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFFD54F)),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Filled.CloudUpload, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Ganti Logo", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "KUA Biringbulu berkomitmen menghadirkan tata kelola kelembagaan transparan, profil aparatur profesional, dan transformasi layanan publik prima berbasis digital di 11 Desa/Kelurahan.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.95f),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // 2. Office Governance & Quota Card (Pengaturan Tata Kelola Kantor & Verifikasi Berkas PTSP)
        item {
            OfficeGovernanceCard(
                onManageStaff = {
                    Toast.makeText(context, "Silakan pilih salah satu aparatur di bawah untuk melihat dossier profil atau memperbarui data.", Toast.LENGTH_LONG).show()
                },
                onVerifyApplications = {
                    isAdminVerificationOpen = true
                }
            )
        }

        if (onNavigateToIkmSurvey != null) {
            item {
                Card(
                    onClick = onNavigateToIkmSurvey,
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5EE)),
                    border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("office_ikm_survey_card")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF006C48).copy(alpha = 0.15f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Color(0xFF006C48),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Survei IKM",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF004D34)
                            )
                            Text(
                                text = "Laporan & evaluasi kepuasan masyarakat KUA Biringbulu",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF065F46),
                                fontSize = 11.5.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = Color(0xFF006C48),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // 3. Staff Management Section Header (Profil Aparatur KUA)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profil Aparatur & Petugas KUA",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${filteredStaff.size} Aparatur",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
                Text(
                    text = "Profil Kepala KUA, Petugas PTSP, Penyuluh Agama Islam, dan Penghulu",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.5.sp
                )
            }
        }

        // 4. Role Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                item {
                    StaffRoleFilterChip(
                        label = "Semua Aparatur",
                        isSelected = selectedRoleFilter == null,
                        onClick = { selectedRoleFilter = null }
                    )
                }
                items(StaffRole.values()) { role ->
                    StaffRoleFilterChip(
                        label = role.title,
                        isSelected = selectedRoleFilter == role,
                        onClick = { selectedRoleFilter = role }
                    )
                }
            }
        }

        // 5. Staff Cards List
        items(filteredStaff, key = { it.id }) { staff ->
            StaffMemberCard(
                staff = staff,
                onViewDetail = { selectedStaffForDetail = staff },
                onEditStaff = { selectedStaffForEdit = staff }
            )
        }

        // 6. Contact & Service Location Card
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Informasi Kontak & Jam Kerja",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = BiringbuluData.ADDRESS, style = MaterialTheme.typography.bodySmall)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = BiringbuluData.WORK_HOURS, style = MaterialTheme.typography.bodySmall)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = BiringbuluData.EMAIL, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val url = "https://api.whatsapp.com/send?phone=${BiringbuluData.WHATSAPP_PTSP.replace("+", "").replace(" ", "").replace("-", "")}&text=${Uri.encode("Halo KUA Biringbulu, saya ingin berkonsultasi mengenai pelayanan PTSP.")}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "WhatsApp tidak tersedia", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Chat WA PTSP", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${BiringbuluData.PHONE_PTSP}"))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Fitur telepon tidak didukung", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Filled.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Hubungi KUA")
                        }
                    }
                }
            }
        }

        // 7. 5 Nilai Budaya Kerja Kemenag
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "5 Nilai Budaya Kerja Kementerian Agama",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    listOf(
                        "1. Integritas" to "Keselarasan antara hati, pikiran, perkataan, dan perbuatan yang baik dan benar.",
                        "2. Profesionalitas" to "Bekerja secara disiplin, kompeten, dan tepat waktu dengan hasil terbaik.",
                        "3. Inovasi" to "Menyempurnakan yang sudah ada dan mengkreasi hal baru yang lebih bermanfaat.",
                        "4. Tanggung Jawab" to "Bekerja secara tuntas dan konsekuen dengan penuh amanah.",
                        "5. Keteladanan" to "Menjadi contoh yang baik bagi orang lain dalam ucapan dan tindakan."
                    ).forEach { (title, desc) ->
                        Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
                            Icon(imageVector = Icons.Filled.Verified, contentDescription = null, tint = KuaGoldSecondary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                Text(text = desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }

        // 8. Direktori 11 Desa di Kec. Biringbulu
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Direktori 11 Desa / Kelurahan Biringbulu",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Informasi Imam Desa & Penyuluh Pendamping Agama Islam",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(BiringbuluData.villages) { village ->
            VillageDirectoryCard(village = village)
        }
    }

    // Detail Dialog
    selectedStaffForDetail?.let { staff ->
        StaffDetailDialog(
            staff = staff,
            onDismiss = { selectedStaffForDetail = null },
            onEdit = { staffToEdit ->
                selectedStaffForDetail = null
                selectedStaffForEdit = staffToEdit
            }
        )
    }

    // Edit Dialog
    selectedStaffForEdit?.let { staff ->
        EditStaffDialog(
            staff = staff,
            isOpen = true,
            onDismiss = { selectedStaffForEdit = null },
            onSave = { updated ->
                onSaveStaff(updated)
                selectedStaffForEdit = null
                Toast.makeText(context, "Data ${updated.name} berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Admin Verification Modal
    AdminVerificationModal(
        isOpen = isAdminVerificationOpen,
        onDismiss = { isAdminVerificationOpen = false },
        applications = allApplications,
        onUpdateStatus = onUpdateStatus
    )

    // Upload & Atur Logo Kemenag Dialog
    if (isLogoUploadDialogOpen) {
        AlertDialog(
            onDismissRequest = { isLogoUploadDialogOpen = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = null,
                        tint = KuaGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pengaturan Logo Kemenag / Kantor",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Current Logo Preview
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White,
                        border = BorderStroke(2.dp, KuaGoldSecondary),
                        modifier = Modifier.size(80.dp)
                    ) {
                        if (!customLogoUri.isNullOrBlank()) {
                            AsyncImage(
                                model = customLogoUri,
                                contentDescription = "Logo Kemenag Aktif",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp)
                            )
                        } else {
                            androidx.compose.foundation.Image(
                                painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_kemenag_logo),
                                contentDescription = "Logo Kemenag Standar",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp)
                            )
                        }
                    }

                    Text(
                        text = if (!customLogoUri.isNullOrBlank()) "Logo Kemenag / Kantor Kustom Sedang Digunakan" else "Menggunakan Logo Resmi Ikhlas Beramal Kemenag RI",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = if (!customLogoUri.isNullOrBlank()) KuaGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "Anda dapat mengunggah file logo Kemenag RI, logo Zona Integritas Kemenag, atau logo khusus KUA Biringbulu dari galeri HP Anda (PNG/JPG).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )

                    Button(
                        onClick = {
                            logoPickerLauncher.launch("image/*")
                            isLogoUploadDialogOpen = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KuaGreenPrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.AddAPhoto, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pilih / Upload Logo dari Galeri", fontSize = 13.sp)
                    }

                    if (!customLogoUri.isNullOrBlank()) {
                        OutlinedButton(
                            onClick = {
                                onUpdateCustomLogoUri(null)
                                isLogoUploadDialogOpen = false
                                Toast.makeText(context, "Logo dikembalikan ke Logo Standar Kemenag RI", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.RestartAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Reset ke Logo Standar Kemenag RI", fontSize = 13.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { isLogoUploadDialogOpen = false }) {
                    Text("Tutup", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
private fun StaffRoleFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFE2E8F0)),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
    }
}

@Composable
fun VillageDirectoryCard(village: VillageItem) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = village.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Surface(
                    color = if (village.isKelurahan) Color(0xFFEDE7F6) else Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = if (village.isKelurahan) "Kelurahan" else "Desa",
                        color = if (village.isKelurahan) Color(0xFF673AB7) else Color(0xFF2E7D32),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (village.isKelurahan) "• Imam Kelurahan: ${village.imamDesa}" else "• Imam Desa: ${village.imamDesa}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "• Penyuluh KUA Pendamping: ${village.penyuluhPendamping}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "• Kemasjidan & Wakaf: ${village.totalMosques} Masjid/Musala • ${village.totalWakafLand} Persil Tanah Wakaf",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
        }
    }
}
