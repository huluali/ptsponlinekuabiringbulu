package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BiringbuluData
import com.example.model.KuaServiceItem
import com.example.ui.components.DigitalQrPassMock
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UploadedDocItem(
    val requirementIndex: Int,
    val requirementName: String,
    val fileName: String,
    val fileSizeFormatted: String,
    val uriString: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationFormScreen(
    service: KuaServiceItem,
    onBack: () -> Unit,
    onSubmit: (
        serviceId: Int,
        serviceTitle: String,
        categoryName: String,
        applicantName: String,
        applicantNik: String,
        applicantPhone: String,
        applicantVillage: String,
        applicantAddress: String,
        notes: String,
        appointmentDate: String,
        uploadedDocuments: String,
        onSuccess: (String) -> Unit
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val villageList = remember { BiringbuluData.villages.map { it.name } }

    var applicantName by remember { mutableStateOf("") }
    var applicantNik by remember { mutableStateOf("") }
    var applicantPhone by remember { mutableStateOf("") }
    var selectedVillage by remember { mutableStateOf(villageList.firstOrNull() ?: "Batumalonro") }
    var isVillageDropdownExpanded by remember { mutableStateOf(false) }
    var applicantAddress by remember { mutableStateOf("") }
    var appointmentDate by remember {
        val nextWeek = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)
        mutableStateOf(SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(Date(nextWeek)))
    }
    var notes by remember { mutableStateOf("") }

    // Uploaded Documents State (Mapped by Requirement Index: -1 for additional generic files)
    val uploadedDocsMap = remember { mutableStateMapOf<Int, UploadedDocItem>() }
    var currentTargetReqIndex by remember { mutableStateOf(0) }
    var currentTargetReqName by remember { mutableStateOf("") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val (name, size) = getFileInfo(context, uri)
            uploadedDocsMap[currentTargetReqIndex] = UploadedDocItem(
                requirementIndex = currentTargetReqIndex,
                requirementName = currentTargetReqName,
                fileName = name,
                fileSizeFormatted = size,
                uriString = uri.toString()
            )
            Toast.makeText(context, "Dokumen '$name' berhasil dilampirkan", Toast.LENGTH_SHORT).show()
        }
    }

    var submittedTrackingCode by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (submittedTrackingCode == null) "Formulir Pendaftaran & Dokumen" else "Bukti Pendaftaran Digital",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("back_button")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        modifier = modifier.testTag("application_form_screen")
    ) { innerPadding ->
        if (submittedTrackingCode != null) {
            // SUCCESS CONFIRMATION & DIGITAL PASS
            val uploadedCount = uploadedDocsMap.size
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Sukses",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Permohonan Berhasil Terkirim!",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Data pendaftaran & dokumen lampiran telah tercatat di sistem PTSP KUA Biringbulu.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    DigitalQrPassMock(
                        trackingCode = submittedTrackingCode ?: "",
                        serviceTitle = service.title,
                        applicantName = applicantName
                    )
                }

                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Ringkasan Pengajuan:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("• Layanan: #${service.id} - ${service.title}", style = MaterialTheme.typography.bodySmall)
                            Text("• NIK Pemohon: $applicantNik", style = MaterialTheme.typography.bodySmall)
                            Text("• No. Telepon: $applicantPhone", style = MaterialTheme.typography.bodySmall)
                            Text("• Domisili: $selectedVillage, Kec. Biringbulu", style = MaterialTheme.typography.bodySmall)
                            Text("• Estimasi Janji Temu: $appointmentDate", style = MaterialTheme.typography.bodySmall)
                            Text("• Biaya: ${service.cost}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Color(0xFFE2E8F0))
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Lampiran Dokumen Persyaratan ($uploadedCount Terunggah):",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = KuaGreenPrimary
                            )
                            if (uploadedDocsMap.isEmpty()) {
                                Text(
                                    text = "• Dokumen fisik akan diserahkan langsung di loket PTSP KUA Biringbulu.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            } else {
                                uploadedDocsMap.values.forEach { doc ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF2E7D32),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "${doc.requirementName.take(30)}... (${doc.fileName})",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontSize = 11.5.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val docSummary = if (uploadedDocsMap.isNotEmpty()) {
                                    "\nDisertai ${uploadedDocsMap.size} berkas dokumen persyaratan digital."
                                } else {
                                    "\nBerkas fisik akan kami bawa ke kantor KUA Biringbulu."
                                }
                                val message = "Halo Petugas PTSP KUA Biringbulu, saya telah mendaftar online layanan '${service.title}' dengan Kode Tracking *$submittedTrackingCode* atas nama *$applicantName* (Desa $selectedVillage).$docSummary\nMohon petunjuk verifikasi berkas selanjutnya. Terima kasih."
                                val url = "https://api.whatsapp.com/send?phone=${BiringbuluData.WHATSAPP_PTSP.replace("+", "").replace(" ", "").replace("-", "")}&text=${Uri.encode(message)}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "WhatsApp tidak terpasang di perangkat ini", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("send_whatsapp_btn")
                        ) {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Kirim WA PTSP", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = onBack,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Selesai")
                        }
                    }
                }
            }
        } else {
            // INPUT FORM & DOCUMENT UPLOAD
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Service Header Banner
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Layanan #${service.id}",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Surface(
                                    color = Color(0xFFDCFCE7),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = service.cost,
                                        color = Color(0xFF15803D),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = service.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Kategori: ${service.category.title}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Section 1: Data Identitas Pemohon
                item {
                    Text(
                        text = "1. Identitas Pemohon & Domisili",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                item {
                    OutlinedTextField(
                        value = applicantName,
                        onValueChange = { applicantName = it },
                        label = { Text("Nama Lengkap Pemohon *") },
                        placeholder = { Text("Sesuai KTP / Akta Kelahiran") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_applicant_name")
                    )
                }

                item {
                    OutlinedTextField(
                        value = applicantNik,
                        onValueChange = { if (it.length <= 16) applicantNik = it.filter { char -> char.isDigit() } },
                        label = { Text("Nomor Induk Kependudukan (NIK 16 Digit) *") },
                        placeholder = { Text("73060xxxxxxxxxxx") },
                        leadingIcon = { Icon(Icons.Filled.VerifiedUser, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_applicant_nik")
                    )
                }

                item {
                    OutlinedTextField(
                        value = applicantPhone,
                        onValueChange = { applicantPhone = it },
                        label = { Text("Nomor WhatsApp / HP Aktif *") },
                        placeholder = { Text("08xxxxxxxxxx (untuk konfirmasi berkas)") },
                        leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_applicant_phone")
                    )
                }

                // Dropdown Desa Biringbulu
                item {
                    ExposedDropdownMenuBox(
                        expanded = isVillageDropdownExpanded,
                        onExpandedChange = { isVillageDropdownExpanded = !isVillageDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedVillage,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Desa / Kelurahan di Kec. Biringbulu *") },
                            leadingIcon = { Icon(Icons.Filled.LocationCity, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isVillageDropdownExpanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("select_village_dropdown")
                        )
                        ExposedDropdownMenu(
                            expanded = isVillageDropdownExpanded,
                            onDismissRequest = { isVillageDropdownExpanded = false }
                        ) {
                            villageList.forEach { village ->
                                DropdownMenuItem(
                                    text = { Text(village) },
                                    onClick = {
                                        selectedVillage = village
                                        isVillageDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = applicantAddress,
                        onValueChange = { applicantAddress = it },
                        label = { Text("Alamat Lengkap / Dusun / RT-RW") },
                        placeholder = { Text("Contoh: Dusun Balassuka, RT 01 / RW 02") },
                        leadingIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = appointmentDate,
                        onValueChange = { appointmentDate = it },
                        label = { Text("Rencana Tanggal Pelayanan / Janji Temu") },
                        placeholder = { Text("Contoh: 25 September 2026") },
                        leadingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Keterangan Tambahan / Catatan Khusus") },
                        placeholder = { Text("Misal: Mempelai wanita dari luar daerah / Akta ikrar tanah wakaf masjid...") },
                        leadingIcon = { Icon(Icons.Filled.Notes, contentDescription = null) },
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Section 2: Upload Dokumen Persyaratan
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, KuaGoldSecondary.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.FileUpload,
                                    contentDescription = null,
                                    tint = KuaGreenPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "2. Upload Dokumen Persyaratan Digital",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Unggah foto/file dokumen sesuai daftar persyaratan di bawah ini untuk diverifikasi oleh petugas.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Requirements Itemized Upload Cards
                if (service.requirements.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Layanan ini tidak memerlukan upload berkas khusus. Anda dapat langsung mengirim formulir pendaftaran.",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                } else {
                    itemsIndexed(service.requirements) { index, requirementName ->
                        val uploadedDoc = uploadedDocsMap[index]
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (uploadedDoc != null) Color(0xFFF0FDF4) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (uploadedDoc != null) Color(0xFF86EFAC) else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(22.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (uploadedDoc != null) Color(0xFF16A34A) else MaterialTheme.colorScheme.primaryContainer
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${index + 1}",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (uploadedDoc != null) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 11.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = requirementName,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                if (uploadedDoc != null) {
                                    // File uploaded preview
                                    Surface(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.InsertDriveFile,
                                                    contentDescription = null,
                                                    tint = Color(0xFF16A34A),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Column {
                                                    Text(
                                                        text = uploadedDoc.fileName,
                                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Text(
                                                        text = "Ukuran: ${uploadedDoc.fileSizeFormatted} • Siap dikirim",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Color(0xFF15803D),
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }

                                            Row {
                                                IconButton(
                                                    onClick = {
                                                        currentTargetReqIndex = index
                                                        currentTargetReqName = requirementName
                                                        filePickerLauncher.launch("*/*")
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Refresh,
                                                        contentDescription = "Ganti File",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                                IconButton(
                                                    onClick = {
                                                        uploadedDocsMap.remove(index)
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Delete,
                                                        contentDescription = "Hapus File",
                                                        tint = Color(0xFFDC2626),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // Upload Button
                                    OutlinedButton(
                                        onClick = {
                                            currentTargetReqIndex = index
                                            currentTargetReqName = requirementName
                                            filePickerLauncher.launch("*/*")
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AttachFile,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Pilih Foto / Dokumen",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Submit Button
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (applicantName.isBlank() || applicantNik.isBlank() || applicantPhone.isBlank()) {
                                Toast.makeText(context, "Mohon lengkapi Nama, NIK, dan Nomor HP", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (applicantNik.length < 16) {
                                Toast.makeText(context, "NIK harus 16 digit angka", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            isSubmitting = true
                            
                            // Serialize uploaded documents
                            val docsSerialized = uploadedDocsMap.values.joinToString("|||") { doc ->
                                "${doc.requirementIndex}:::${doc.requirementName}:::${doc.fileName}:::${doc.fileSizeFormatted}"
                            }

                            onSubmit(
                                service.id,
                                service.title,
                                service.category.title,
                                applicantName,
                                applicantNik,
                                applicantPhone,
                                selectedVillage,
                                applicantAddress,
                                notes,
                                appointmentDate,
                                docsSerialized
                            ) { code ->
                                isSubmitting = false
                                submittedTrackingCode = code
                            }
                        },
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("submit_application_btn")
                    ) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSubmitting) "Menyimpan Permohonan..." else "Kirim Permohonan ke KUA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

private fun getFileInfo(context: Context, uri: Uri): Pair<String, String> {
    var name = "dokumen_${System.currentTimeMillis()}"
    var sizeFormatted = "Lampiran Dokumen"

    try {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex) ?: name
                }
                val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    val sizeBytes = it.getLong(sizeIndex)
                    sizeFormatted = when {
                        sizeBytes > 1024 * 1024 -> String.format(Locale.US, "%.1f MB", sizeBytes / (1024.0 * 1024.0))
                        sizeBytes > 1024 -> "${sizeBytes / 1024} KB"
                        sizeBytes > 0 -> "$sizeBytes B"
                        else -> "Lampiran File"
                    }
                }
            }
        }
    } catch (e: Exception) {
        val lastSegment = uri.lastPathSegment
        if (!lastSegment.isNullOrBlank()) {
            name = lastSegment
        }
    }
    return Pair(name, sizeFormatted)
}
