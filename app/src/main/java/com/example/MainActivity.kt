package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BiringbuluData
import com.example.data.KuaServiceData
import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem
import com.example.ui.components.KuaHeaderBadge
import com.example.ui.screens.ApplicationFormScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileOfficeScreen
import com.example.ui.screens.ServiceDetailScreen
import com.example.ui.screens.ServicesListScreen
import com.example.ui.screens.SmartSyariahScreen
import com.example.ui.screens.TrackingScreen
import com.example.ui.theme.KuaGoldSecondary
import com.example.ui.theme.KuaGreenPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.KuaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: KuaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KuaAppRoot(viewModel = viewModel)
            }
        }
    }
}

sealed class ScreenDestination {
    object MainTabs : ScreenDestination()
    data class ServiceDetail(val serviceId: Int) : ScreenDestination()
    data class ApplicationForm(val serviceId: Int) : ScreenDestination()
}

@Composable
fun KuaAppRoot(viewModel: KuaViewModel) {
    var currentDestination by remember { mutableStateOf<ScreenDestination>(ScreenDestination.MainTabs) }
    var smartSyariahInitialSubTab by remember { mutableStateOf(0) }

    val selectedTab by viewModel.selectedTab.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsState()
    val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
    val allApplications by viewModel.allApplications.collectAsState()
    val searchedApplication by viewModel.searchedApplication.collectAsState()
    val hasSearchedTracking by viewModel.hasSearchedTracking.collectAsState()
    val trackingInput by viewModel.trackingInput.collectAsState()
    val checkedReqMap by viewModel.checkedRequirementsMap.collectAsState()
    val staffList by viewModel.staffList.collectAsState()
    val customLogoUri by viewModel.customLogoUri.collectAsState()

    // Smart Syariah States
    val deviceAzimuth by viewModel.deviceAzimuth.collectAsState()
    val aiInputText by viewModel.aiInputText.collectAsState()
    val aiCurrentResponse by viewModel.aiCurrentResponse.collectAsState()
    val isAiThinking by viewModel.isAiThinking.collectAsState()
    val allConsultations by viewModel.allConsultations.collectAsState()

    // Back handling
    BackHandler(enabled = currentDestination !is ScreenDestination.MainTabs || selectedTab != 0) {
        when {
            currentDestination is ScreenDestination.ApplicationForm -> {
                val serviceId = (currentDestination as ScreenDestination.ApplicationForm).serviceId
                currentDestination = ScreenDestination.ServiceDetail(serviceId)
            }
            currentDestination is ScreenDestination.ServiceDetail -> {
                currentDestination = ScreenDestination.MainTabs
            }
            selectedTab != 0 -> {
                viewModel.setTab(0)
            }
        }
    }

    Scaffold(
        topBar = {
            if (currentDestination is ScreenDestination.MainTabs) {
                KuaHeaderBadge(
                    title = "KUA BIRINGBULU",
                    subtitle = "Kemenag Kab. Gowa • PTSP Digital Terpadu",
                    customLogoUri = customLogoUri,
                    onSettingsClick = { viewModel.setTab(4) }
                )
            }
        },
        bottomBar = {
            if (currentDestination is ScreenDestination.MainTabs) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    val navItems = listOf(
                        Triple(0, "Beranda", Pair(Icons.Filled.Home, Icons.Outlined.Home)),
                        Triple(1, "48 Layanan", Pair(Icons.Filled.Verified, Icons.Outlined.Verified)),
                        Triple(2, "Lacak", Pair(Icons.Filled.QrCodeScanner, Icons.Outlined.QrCodeScanner)),
                        Triple(3, "Syariah AI", Pair(Icons.Filled.SmartToy, Icons.Outlined.SmartToy)),
                        Triple(4, "Profil KUA", Pair(Icons.Filled.AccountBalance, Icons.Filled.AccountBalance))
                    )

                    navItems.forEach { (index, label, icons) ->
                        val isSelected = selectedTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.setTab(index) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) icons.first else icons.second,
                                    contentDescription = label,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            label = {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.testTag("nav_item_$index")
                        )
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { destination ->
                when (destination) {
                    is ScreenDestination.MainTabs -> {
                        when (selectedTab) {
                            0 -> HomeScreen(
                                onNavigateToServices = { cat ->
                                    viewModel.setCategoryFilter(cat)
                                    viewModel.setTab(1)
                                },
                                onNavigateToServiceDetail = { serviceId ->
                                    currentDestination = ScreenDestination.ServiceDetail(serviceId)
                                },
                                onNavigateToTracking = { code ->
                                    viewModel.setTrackingInput(code)
                                    viewModel.searchTrackingCode(code)
                                    viewModel.setTab(2)
                                },
                                onNavigateToSmartSyariah = { subTab ->
                                    smartSyariahInitialSubTab = subTab
                                    viewModel.setTab(3)
                                },
                                onNavigateToProfile = {
                                    viewModel.setTab(4)
                                }
                            )
                            1 -> ServicesListScreen(
                                searchQuery = searchQuery,
                                onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                selectedCategory = selectedCategoryFilter,
                                onSelectCategory = { viewModel.setCategoryFilter(it) },
                                bookmarkedServiceIds = bookmarkedIds,
                                onToggleBookmark = { viewModel.toggleBookmark(it) },
                                onSelectService = { serviceId ->
                                    currentDestination = ScreenDestination.ServiceDetail(serviceId)
                                }
                            )
                            2 -> TrackingScreen(
                                trackingInput = trackingInput,
                                onTrackingInputChange = { viewModel.setTrackingInput(it) },
                                searchedApplication = searchedApplication,
                                hasSearched = hasSearchedTracking,
                                onSearchCode = { viewModel.searchTrackingCode(it) },
                                onClearSearch = { viewModel.clearTrackingSearch() },
                                allApplications = allApplications,
                                onDeleteApplication = { viewModel.deleteApplication(it) },
                                onUpdateStatus = { id, newStatus, notes ->
                                    viewModel.updateApplicationStatus(id, newStatus, notes)
                                }
                            )
                            3 -> SmartSyariahScreen(
                                initialSubTab = smartSyariahInitialSubTab,
                                deviceAzimuth = deviceAzimuth,
                                qiblaBearing = viewModel.qiblaBearing,
                                onNavigateToServiceDetail = { serviceId ->
                                    currentDestination = ScreenDestination.ServiceDetail(serviceId)
                                },
                                aiInputText = aiInputText,
                                onAiInputTextChange = { viewModel.setAiInputText(it) },
                                aiCurrentResponse = aiCurrentResponse,
                                isAiThinking = isAiThinking,
                                onAskAi = { viewModel.askAiConsultant(it) },
                                consultationHistory = allConsultations,
                                onDeleteConsultation = { viewModel.deleteConsultation(it) }
                            )
                            4 -> ProfileOfficeScreen(
                                staffList = staffList,
                                onSaveStaff = { viewModel.saveStaffChanges(it) },
                                allApplications = allApplications,
                                onUpdateStatus = { id, newStatus, notes ->
                                    viewModel.updateApplicationStatus(id, newStatus, notes)
                                },
                                customLogoUri = customLogoUri,
                                onUpdateCustomLogoUri = { viewModel.updateCustomLogoUri(it) }
                            )
                        }
                    }

                    is ScreenDestination.ServiceDetail -> {
                        val service = KuaServiceData.getServiceById(destination.serviceId)
                        if (service != null) {
                            ServiceDetailScreen(
                                service = service,
                                isBookmarked = bookmarkedIds.contains(service.id),
                                onToggleBookmark = { viewModel.toggleBookmark(service.id) },
                                onBack = { currentDestination = ScreenDestination.MainTabs },
                                onApplyOnline = {
                                    currentDestination = ScreenDestination.ApplicationForm(service.id)
                                },
                                checkedRequirements = checkedReqMap[service.id] ?: emptySet(),
                                onToggleRequirement = { idx ->
                                    viewModel.toggleRequirementCheck(service.id, idx)
                                }
                            )
                        } else {
                            currentDestination = ScreenDestination.MainTabs
                        }
                    }

                    is ScreenDestination.ApplicationForm -> {
                        val service = KuaServiceData.getServiceById(destination.serviceId)
                        if (service != null) {
                            ApplicationFormScreen(
                                service = service,
                                onBack = { currentDestination = ScreenDestination.ServiceDetail(service.id) },
                                onSubmit = { serviceId, serviceTitle, categoryName, applicantName, applicantNik, applicantPhone, applicantVillage, applicantAddress, notes, appointmentDate, uploadedDocuments, onSuccess ->
                                    viewModel.submitApplication(
                                        serviceId = serviceId,
                                        serviceTitle = serviceTitle,
                                        categoryName = categoryName,
                                        applicantName = applicantName,
                                        applicantNik = applicantNik,
                                        applicantPhone = applicantPhone,
                                        applicantVillage = applicantVillage,
                                        applicantAddress = applicantAddress,
                                        notes = notes,
                                        appointmentDate = appointmentDate,
                                        uploadedDocuments = uploadedDocuments,
                                        onSuccess = onSuccess
                                    )
                                }
                            )
                        } else {
                            currentDestination = ScreenDestination.MainTabs
                        }
                    }
                }
            }
        }
    }
}
