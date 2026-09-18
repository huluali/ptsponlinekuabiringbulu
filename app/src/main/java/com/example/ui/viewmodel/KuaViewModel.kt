package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BiringbuluData
import com.example.data.KuaServiceData
import com.example.data.local.KuaDatabase
import com.example.data.local.entity.ConsultationEntity
import com.example.data.local.entity.IkmSurveyEntity
import com.example.data.local.entity.ServiceApplicationEntity
import com.example.data.repository.KuaRepository
import com.example.model.KuaServiceCategory
import com.example.model.KuaServiceItem
import com.example.model.KuaStaff
import com.example.model.StaffRole
import com.example.util.AiConsultantHelper
import com.example.util.PnbpCalculator
import com.example.util.ZakatCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KuaViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val repository: KuaRepository

    // Custom Kemenag / Office Logo State (persisted via SharedPreferences)
    private val prefs = application.getSharedPreferences("kua_biringbulu_prefs", Context.MODE_PRIVATE)
    private val _customLogoUri = MutableStateFlow<String?>(prefs.getString("custom_logo_uri", null))
    val customLogoUri: StateFlow<String?> = _customLogoUri.asStateFlow()

    fun updateCustomLogoUri(uriString: String?) {
        _customLogoUri.value = uriString
        prefs.edit().putString("custom_logo_uri", uriString).apply()
    }

    // Profil KUA & Aparatur State
    val staffList: StateFlow<List<KuaStaff>>

    private val _selectedStaffForDetail = MutableStateFlow<KuaStaff?>(null)
    val selectedStaffForDetail: StateFlow<KuaStaff?> = _selectedStaffForDetail.asStateFlow()

    private val _selectedStaffForEdit = MutableStateFlow<KuaStaff?>(null)
    val selectedStaffForEdit: StateFlow<KuaStaff?> = _selectedStaffForEdit.asStateFlow()

    private val _isStaffDetailOpen = MutableStateFlow(false)
    val isStaffDetailOpen: StateFlow<Boolean> = _isStaffDetailOpen.asStateFlow()

    private val _isStaffEditOpen = MutableStateFlow(false)
    val isStaffEditOpen: StateFlow<Boolean> = _isStaffEditOpen.asStateFlow()

    private val _selectedStaffRoleFilter = MutableStateFlow<StaffRole?>(null)
    val selectedStaffRoleFilter: StateFlow<StaffRole?> = _selectedStaffRoleFilter.asStateFlow()

    init {
        val db = KuaDatabase.getDatabase(application)
        repository = KuaRepository(
            db.serviceApplicationDao(),
            db.consultationDao(),
            db.bookmarkDao(),
            db.staffDao(),
            db.ikmSurveyDao()
        )

        staffList = repository.staffList
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        // Seed initial applications, consultations & staff into Room database if empty
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    val allIkmSurveys: StateFlow<List<IkmSurveyEntity>> = repository.allIkmSurveys
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allApplications: StateFlow<List<ServiceApplicationEntity>> = repository.allApplications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allConsultations: StateFlow<List<ConsultationEntity>> = repository.allConsultations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarkedIds: StateFlow<List<Int>> = repository.bookmarkedIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Navigation State
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedServiceId = MutableStateFlow<Int?>(null)
    val selectedServiceId: StateFlow<Int?> = _selectedServiceId.asStateFlow()

    // Service Filtering & Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<KuaServiceCategory?>(null)
    val selectedCategoryFilter: StateFlow<KuaServiceCategory?> = _selectedCategoryFilter.asStateFlow()

    // Tracking Lookup State
    private val _trackingInput = MutableStateFlow("")
    val trackingInput: StateFlow<String> = _trackingInput.asStateFlow()

    private val _searchedApplication = MutableStateFlow<ServiceApplicationEntity?>(null)
    val searchedApplication: StateFlow<ServiceApplicationEntity?> = _searchedApplication.asStateFlow()

    private val _hasSearchedTracking = MutableStateFlow(false)
    val hasSearchedTracking: StateFlow<Boolean> = _hasSearchedTracking.asStateFlow()

    // Interactive Document Checklist state for current viewed service (Service ID to Set of Checked Indices)
    private val _checkedRequirementsMap = MutableStateFlow<Map<Int, Set<Int>>>(emptyMap())
    val checkedRequirementsMap: StateFlow<Map<Int, Set<Int>>> = _checkedRequirementsMap.asStateFlow()

    // AI Consultant State
    private val _aiInputText = MutableStateFlow("")
    val aiInputText: StateFlow<String> = _aiInputText.asStateFlow()

    private val _aiCurrentResponse = MutableStateFlow<AiConsultantHelper.AiResponse?>(null)
    val aiCurrentResponse: StateFlow<AiConsultantHelper.AiResponse?> = _aiCurrentResponse.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Zakat State
    private val _zakatType = MutableStateFlow(0) // 0: Maal/Emas, 1: Profesi, 2: Pertanian
    val zakatType: StateFlow<Int> = _zakatType.asStateFlow()

    // Marriage Simulation State
    private val _isAtKuaOffice = MutableStateFlow(true)
    val isAtKuaOffice: StateFlow<Boolean> = _isAtKuaOffice.asStateFlow()

    private val _isWorkHours = MutableStateFlow(true)
    val isWorkHours: StateFlow<Boolean> = _isWorkHours.asStateFlow()

    private val _hasSktm = MutableStateFlow(false)
    val hasSktm: StateFlow<Boolean> = _hasSktm.asStateFlow()

    // Compass / Sensor State
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val orientationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)

    private val _deviceAzimuth = MutableStateFlow(0f)
    val deviceAzimuth: StateFlow<Float> = _deviceAzimuth.asStateFlow()

    val qiblaBearing: Float = BiringbuluData.QIBLA_AZIMUTH.toFloat()

    init {
        startCompassSensor()
    }

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun selectService(serviceId: Int?) {
        _selectedServiceId.value = serviceId
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: KuaServiceCategory?) {
        _selectedCategoryFilter.value = category
    }

    fun setTrackingInput(input: String) {
        _trackingInput.value = input
    }

    fun searchTrackingCode(code: String) {
        _hasSearchedTracking.value = true
        viewModelScope.launch {
            val result = repository.getApplicationByTrackingCode(code.trim().uppercase())
            _searchedApplication.value = result
        }
    }

    fun clearTrackingSearch() {
        _trackingInput.value = ""
        _searchedApplication.value = null
        _hasSearchedTracking.value = false
    }

    fun toggleRequirementCheck(serviceId: Int, requirementIndex: Int) {
        val currentMap = _checkedRequirementsMap.value.toMutableMap()
        val currentSet = currentMap[serviceId]?.toMutableSet() ?: mutableSetOf()
        if (currentSet.contains(requirementIndex)) {
            currentSet.remove(requirementIndex)
        } else {
            currentSet.add(requirementIndex)
        }
        currentMap[serviceId] = currentSet
        _checkedRequirementsMap.value = currentMap
    }

    fun toggleBookmark(serviceId: Int) {
        viewModelScope.launch {
            val isBookmarked = bookmarkedIds.value.contains(serviceId)
            repository.toggleBookmark(serviceId, isBookmarked)
        }
    }

    fun submitApplication(
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
        uploadedDocuments: String = "",
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val code = repository.submitApplication(
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
                uploadedDocuments = uploadedDocuments
            )
            onSuccess(code)
        }
    }

    fun updateApplicationStatus(id: Long, newStatus: String, newNotes: String) {
        viewModelScope.launch {
            repository.updateApplicationStatus(id, newStatus, newNotes)
            // Refresh searched application if it's currently open
            val currentSearched = _searchedApplication.value
            if (currentSearched != null && currentSearched.id == id) {
                _searchedApplication.value = repository.getApplicationByTrackingCode(currentSearched.trackingCode)
            }
        }
    }

    fun deleteApplication(id: Long) {
        viewModelScope.launch {
            repository.deleteApplication(id)
        }
    }

    fun deleteConsultation(id: Long) {
        viewModelScope.launch {
            repository.deleteConsultation(id)
        }
    }

    fun submitIkmSurvey(
        respondentName: String,
        respondentPhone: String,
        serviceName: String,
        village: String,
        overallRating: Int,
        ratingRequirements: Int,
        ratingProcedure: Int,
        ratingSpeed: Int,
        ratingCost: Int,
        ratingStaff: Int,
        ratingFacility: Int,
        feedback: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            repository.submitIkmSurvey(
                respondentName = respondentName,
                respondentPhone = respondentPhone,
                serviceName = serviceName,
                village = village,
                overallRating = overallRating,
                ratingRequirements = ratingRequirements,
                ratingProcedure = ratingProcedure,
                ratingSpeed = ratingSpeed,
                ratingCost = ratingCost,
                ratingStaff = ratingStaff,
                ratingFacility = ratingFacility,
                feedback = feedback
            )
            onSuccess()
        }
    }

    fun deleteIkmSurvey(id: Long) {
        viewModelScope.launch {
            repository.deleteIkmSurvey(id)
        }
    }

    fun setAiInputText(text: String) {
        _aiInputText.value = text
    }

    fun askAiConsultant(question: String) {
        if (question.isBlank()) return
        _isAiThinking.value = true
        _aiInputText.value = ""
        viewModelScope.launch {
            kotlinx.coroutines.delay(400)
            val response = AiConsultantHelper.answerQuery(question)
            _aiCurrentResponse.value = response
            _isAiThinking.value = false

            // Save to consultation history
            repository.saveConsultation(
                topic = response.title,
                question = question,
                answer = response.summary + "\n\n" + response.details.joinToString("\n"),
                category = "Syariah & Layanan"
            )
        }
    }

    fun setZakatType(type: Int) {
        _zakatType.value = type
    }

    fun setMarriageLocation(isAtKua: Boolean) {
        _isAtKuaOffice.value = isAtKua
    }

    fun setMarriageWorkHours(isWork: Boolean) {
        _isWorkHours.value = isWork
    }

    fun setMarriageHasSktm(hasSktm: Boolean) {
        _hasSktm.value = hasSktm
    }

    // Kepegawaian & Staff Management Operations
    fun openStaffDetail(staff: KuaStaff) {
        _selectedStaffForDetail.value = staff
        _isStaffDetailOpen.value = true
    }

    fun closeStaffDetail() {
        _isStaffDetailOpen.value = false
        _selectedStaffForDetail.value = null
    }

    fun openStaffEdit(staff: KuaStaff) {
        _selectedStaffForEdit.value = staff
        _isStaffEditOpen.value = true
    }

    fun closeStaffEdit() {
        _isStaffEditOpen.value = false
        _selectedStaffForEdit.value = null
    }

    fun saveStaffChanges(updatedStaff: KuaStaff) {
        viewModelScope.launch {
            repository.updateStaff(updatedStaff)
            _isStaffEditOpen.value = false
            _selectedStaffForEdit.value = null
        }
    }

    fun setStaffRoleFilter(role: StaffRole?) {
        _selectedStaffRoleFilter.value = role
    }

    private fun startCompassSensor() {
        if (rotationSensor != null) {
            sensorManager?.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        } else if (orientationSensor != null) {
            sensorManager?.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            val orientation = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientation)
            val azimuthInDeg = (Math.toDegrees(orientation[0].toDouble()) + 360) % 360
            _deviceAzimuth.value = azimuthInDeg.toFloat()
        } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
            _deviceAzimuth.value = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }
}
