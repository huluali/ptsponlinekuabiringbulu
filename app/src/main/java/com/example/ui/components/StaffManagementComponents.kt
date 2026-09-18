package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.KuaStaff
import com.example.model.StaffRole
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.MintBg
import com.example.ui.theme.NavySecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StaffMemberCard(
    staff: KuaStaff,
    onViewDetail: (KuaStaff) -> Unit,
    onEditStaff: (KuaStaff) -> Unit,
    modifier: Modifier = Modifier
) {
    val roleColor = when (staff.role) {
        StaffRole.KEPALA_KUA -> EmeraldPrimary
        StaffRole.PENGHULU -> NavySecondary
        StaffRole.PENYULUH -> Color(0xFF2E7D32)
        StaffRole.PETUGAS_PTSP -> Color(0xFFD84315)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, roleColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .testTag("staff_card_${staff.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Role & Active Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = roleColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (staff.role) {
                                StaffRole.KEPALA_KUA -> Icons.Default.Security
                                StaffRole.PENGHULU -> Icons.Default.Verified
                                StaffRole.PENYULUH -> Icons.Default.School
                                StaffRole.PETUGAS_PTSP -> Icons.Default.Work
                            },
                            contentDescription = null,
                            tint = roleColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = staff.role.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = roleColor
                        )
                    }
                }

                // Status Tag
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (staff.isDutyActive) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (staff.isDutyActive) Color(0xFF2E7D32) else Color(0xFFE65100))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (staff.isDutyActive) "Siaga Piket" else "Dinas Luar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (staff.isDutyActive) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Info Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Badge
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(roleColor, roleColor.copy(alpha = 0.8f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = staff.initials,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = staff.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = staff.positionTitle,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = roleColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "NIP: ${staff.nip}",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Key Attributes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Pangkat / Golongan", fontSize = 11.sp, color = Color(0xFF9CA3AF))
                    Text(
                        text = staff.rankGrade,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                }
                Column {
                    Text(text = "Status Kepegawaian", fontSize = 11.sp, color = Color(0xFF9CA3AF))
                    Text(
                        text = staff.employmentStatus,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Ruang / Loket", fontSize = 11.sp, color = Color(0xFF9CA3AF))
                    Text(
                        text = staff.roomDesk,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status message
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF9FAFB),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = staff.statusMessage,
                        fontSize = 11.sp,
                        color = Color(0xFF4B5563),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onViewDetail(staff) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, roleColor),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = roleColor),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_detail_${staff.id}")
                ) {
                    Icon(imageVector = Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Detail Dossier", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { onEditStaff(staff) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = roleColor),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_edit_${staff.id}")
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Pengaturan", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun StaffDetailDialog(
    staff: KuaStaff?,
    onDismiss: () -> Unit,
    onEdit: (KuaStaff) -> Unit
) {
    if (staff == null) return

    val roleColor = when (staff.role) {
        StaffRole.KEPALA_KUA -> EmeraldPrimary
        StaffRole.PENGHULU -> NavySecondary
        StaffRole.PENYULUH -> Color(0xFF2E7D32)
        StaffRole.PETUGAS_PTSP -> Color(0xFFD84315)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxSize(0.88f)
                .clip(RoundedCornerShape(20.dp)),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = roleColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = null,
                                    tint = roleColor,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Dossier Kepegawaian Kemenag",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = "Kantor Urusan Agama Kec. Biringbulu",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup", tint = Color(0xFF6B7280))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFE5E7EB))
                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Dossier Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Profile Header Box
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MintBg,
                        border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(roleColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = staff.initials,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = staff.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = staff.positionTitle,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = roleColor
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "NIP: ${staff.nip}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Detail Data Grid
                    Text(
                        text = "Data Pokok Kepegawaian",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    DossierItem(label = "Pangkat / Golongan", value = staff.rankGrade, icon = Icons.Default.Verified)
                    DossierItem(label = "Status ASN", value = staff.employmentStatus, icon = Icons.Default.Security)
                    DossierItem(label = "Pendidikan Terakhir", value = staff.education, icon = Icons.Default.School)
                    DossierItem(label = "Nomor SK Penugasan", value = staff.appointmentSk, icon = Icons.Default.Badge)
                    DossierItem(label = "Wilayah Penugasan", value = staff.serviceArea, icon = Icons.Default.LocationOn)
                    DossierItem(label = "Jadwal & Waktu Tugas", value = staff.scheduleTime, icon = Icons.Default.Schedule)
                    DossierItem(label = "Ruang / Loket Layanan", value = staff.roomDesk, icon = Icons.Default.Work)
                    DossierItem(label = "Nomor WhatsApp Dinas", value = staff.phone, icon = Icons.Default.Call)
                    DossierItem(label = "Email Resmi Kemenag", value = staff.email, icon = Icons.Default.Email)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tugas Pokok & Fungsi (Tupoksi)
                    Text(
                        text = "Tugas Pokok & Wewenang Resmi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    staff.mainDuties.forEachIndexed { index, duty ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(roleColor)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = duty,
                                fontSize = 12.sp,
                                color = Color(0xFF374151),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Footer Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tutup", fontSize = 13.sp)
                    }

                    Button(
                        onClick = {
                            onDismiss()
                            onEdit(staff)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = roleColor),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Edit Data Pegawai", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EditStaffDialog(
    staff: KuaStaff?,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (KuaStaff) -> Unit
) {
    if (!isOpen || staff == null) return

    var name by remember(staff) { mutableStateOf(staff.name) }
    var nip by remember(staff) { mutableStateOf(staff.nip) }
    var rankGrade by remember(staff) { mutableStateOf(staff.rankGrade) }
    var positionTitle by remember(staff) { mutableStateOf(staff.positionTitle) }
    var employmentStatus by remember(staff) { mutableStateOf(staff.employmentStatus) }
    var education by remember(staff) { mutableStateOf(staff.education) }
    var appointmentSk by remember(staff) { mutableStateOf(staff.appointmentSk) }
    var serviceArea by remember(staff) { mutableStateOf(staff.serviceArea) }
    var phone by remember(staff) { mutableStateOf(staff.phone) }
    var email by remember(staff) { mutableStateOf(staff.email) }
    var scheduleTime by remember(staff) { mutableStateOf(staff.scheduleTime) }
    var roomDesk by remember(staff) { mutableStateOf(staff.roomDesk) }
    var isDutyActive by remember(staff) { mutableStateOf(staff.isDutyActive) }
    var statusMessage by remember(staff) { mutableStateOf(staff.statusMessage) }

    val context = LocalContext.current
    val roleColor = when (staff.role) {
        StaffRole.KEPALA_KUA -> EmeraldPrimary
        StaffRole.PENGHULU -> NavySecondary
        StaffRole.PENYULUH -> Color(0xFF2E7D32)
        StaffRole.PETUGAS_PTSP -> Color(0xFFD84315)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxSize(0.92f)
                .clip(RoundedCornerShape(20.dp)),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Pengaturan Data Pegawai",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Text(
                            text = "${staff.role.title} • KUA Biringbulu",
                            fontSize = 12.sp,
                            color = roleColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Batal", tint = Color(0xFF6B7280))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFE5E7EB))
                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable Form Fields
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Duty Switch Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isDutyActive) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                        border = BorderStroke(1.dp, if (isDutyActive) Color(0xFFA5D6A7) else Color(0xFFFFCC80)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isDutyActive) "Status: Siaga Piket di Kantor" else "Status: Sedang Tugas / Dinas Luar",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDutyActive) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                                )
                                Text(
                                    text = "Mengatur ketersediaan petugas untuk masyarakat",
                                    fontSize = 11.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }
                            Switch(
                                checked = isDutyActive,
                                onCheckedChange = { isDutyActive = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = EmeraldPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = statusMessage,
                        onValueChange = { statusMessage = it },
                        label = { Text("Pesan Kesiagaan / Catatan Tugas Hari Ini") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama Lengkap & Gelar Resmi") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = nip,
                        onValueChange = { nip = it },
                        label = { Text("NIP / Nomor Induk Pegawai") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = rankGrade,
                            onValueChange = { rankGrade = it },
                            label = { Text("Pangkat / Golongan") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                        )
                        OutlinedTextField(
                            value = employmentStatus,
                            onValueChange = { employmentStatus = it },
                            label = { Text("Status ASN") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = positionTitle,
                        onValueChange = { positionTitle = it },
                        label = { Text("Jabatan Fungsional / Struktural") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = education,
                        onValueChange = { education = it },
                        label = { Text("Pendidikan Terakhir") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = appointmentSk,
                        onValueChange = { appointmentSk = it },
                        label = { Text("Nomor SK Penugasan / Pengangkatan") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = serviceArea,
                        onValueChange = { serviceArea = it },
                        label = { Text("Wilayah Binaan / Penugasan") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("No. WhatsApp") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                        )
                        OutlinedTextField(
                            value = roomDesk,
                            onValueChange = { roomDesk = it },
                            label = { Text("Ruang / Loket") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Dinas Kemenag") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = scheduleTime,
                        onValueChange = { scheduleTime = it },
                        label = { Text("Jadwal & Jam Layanan") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = roleColor)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Batal", fontSize = 13.sp)
                    }

                    Button(
                        onClick = {
                            val initials = name.split(" ")
                                .filter { it.isNotEmpty() && !it.contains(".") }
                                .take(2)
                                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                                .joinToString("")
                                .ifEmpty { "KB" }

                            val updated = staff.copy(
                                name = name.trim(),
                                nip = nip.trim(),
                                rankGrade = rankGrade.trim(),
                                positionTitle = positionTitle.trim(),
                                employmentStatus = employmentStatus.trim(),
                                education = education.trim(),
                                appointmentSk = appointmentSk.trim(),
                                serviceArea = serviceArea.trim(),
                                phone = phone.trim(),
                                email = email.trim(),
                                scheduleTime = scheduleTime.trim(),
                                roomDesk = roomDesk.trim(),
                                isDutyActive = isDutyActive,
                                statusMessage = statusMessage.trim(),
                                initials = initials
                            )
                            onSave(updated)
                            android.widget.Toast.makeText(context, "Data Pegawai Berhasil Diperbarui", android.widget.Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Simpan Perubahan", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun OfficeGovernanceCard(
    onManageStaff: () -> Unit,
    onVerifyApplications: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, EmeraldPrimary.copy(alpha = 0.25f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MintBg,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = EmeraldPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pengaturan Tata Kelola Kantor",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Kepala KUA • PTSP • Penghulu • Penyuluh",
                        fontSize = 12.sp,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Kelola data kepegawaian resmi, verifikasi berkas ajuan online masuk, kuota layanan, dan kesiagaan aparatur KUA Kecamatan Biringbulu.",
                fontSize = 12.sp,
                color = Color(0xFF4B5563),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (onVerifyApplications != null) {
                Button(
                    onClick = onVerifyApplications,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Verifikasi Berkas Ajuan Masuk (PTSP)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onManageStaff,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Pengaturan Data Kepegawaian", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = EmeraldPrimary)
                }
            } else {
                Button(
                    onClick = onManageStaff,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Kelola Kepegawaian & Petugas", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DossierItem(
    label: String,
    value: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = EmeraldPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = Color(0xFF9CA3AF))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F2937)
            )
        }
    }
}
