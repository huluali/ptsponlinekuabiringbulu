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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KuaServiceData
import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem
import com.example.ui.components.ServiceCategoryChip
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary

@Composable
fun ServicesListScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: KuaServiceCategory?,
    onSelectCategory: (KuaServiceCategory?) -> Unit,
    bookmarkedServiceIds: List<Int>,
    onToggleBookmark: (Int) -> Unit,
    onSelectService: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showOnlyBookmarked by remember { mutableStateOf(false) }

    val filteredServices = remember(searchQuery, selectedCategory, showOnlyBookmarked, bookmarkedServiceIds) {
        var list = if (selectedCategory != null) {
            KuaServiceData.getServicesByCategory(selectedCategory)
        } else {
            KuaServiceData.allServices
        }

        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.subtitle.lowercase().contains(q) ||
                it.category.title.lowercase().contains(q) ||
                it.requirements.any { req -> req.lowercase().contains(q) } ||
                it.outputDocument.lowercase().contains(q)
            }
        }

        if (showOnlyBookmarked) {
            list = list.filter { bookmarkedServiceIds.contains(it.id) }
        }

        list
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("services_list_screen")
    ) {
        // Search & Filter Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Cari dari 48 standar layanan KUA...", fontSize = 13.5.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Cari",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Hapus")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8FAFC),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedBorderColor = KuaGreenPrimary,
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("service_search_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Bookmark filter chip & Category selector row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = showOnlyBookmarked,
                        onClick = { showOnlyBookmarked = !showOnlyBookmarked },
                        label = { Text("Tersimpan (${bookmarkedServiceIds.size})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                        leadingIcon = {
                            Icon(
                                imageVector = if (showOnlyBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KuaGoldSecondary,
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Total: ${filteredServices.size} Layanan Terdaftar",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Horizontal Category Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        ServiceCategoryChip(
                            category = null,
                            isSelected = selectedCategory == null,
                            label = "Semua (48)",
                            onClick = { onSelectCategory(null) }
                        )
                    }
                    items(KuaServiceCategory.values()) { cat ->
                        ServiceCategoryChip(
                            category = cat,
                            isSelected = selectedCategory == cat,
                            label = cat.shortName,
                            onClick = { onSelectCategory(cat) }
                        )
                    }
                }
            }
        }

        // Service List
        if (filteredServices.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tidak ada layanan yang sesuai",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Coba gunakan kata kunci pencarian lain atau pilih kategori Semua",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredServices, key = { it.id }) { service ->
                    ServiceItemCard(
                        service = service,
                        isBookmarked = bookmarkedServiceIds.contains(service.id),
                        onToggleBookmark = { onToggleBookmark(service.id) },
                        onClick = { onSelectService(service.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceItemCard(
    service: KuaServiceItem,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("service_item_${service.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${service.id}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = service.category.shortName,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            fontSize = 11.sp
                        )
                    }
                }
                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Simpan",
                        tint = if (isBookmarked) KuaGoldSecondary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFFDCFCE7),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                ) {
                    Text(
                        text = if (service.cost.contains("Gratis")) "Rp 0,- (Gratis)" else "PNBP Rp 600rb",
                        color = Color(0xFF15803D),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = service.processingTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

