package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ApprovalStatus
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.PlatformStats
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.repository.OwnShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AdminViewModel(
  private val repository: OwnShopRepository
) : ViewModel() {

  val stats: StateFlow<PlatformStats> = repository.getPlatformStats()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlatformStats())

  val allProviders: StateFlow<List<Provider>> = repository.getAllProviders()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allRequests: StateFlow<List<ServiceRequest>> = repository.getAllRequests()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allCities: StateFlow<List<City>> = repository.getAllCities()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allCategories: StateFlow<List<Category>> = repository.getAllCategories()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allUsers: StateFlow<List<UserAccount>> = repository.getAllUsers()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _providerFilterStatus = MutableStateFlow<ApprovalStatus?>(null)
  val providerFilterStatus: StateFlow<ApprovalStatus?> = _providerFilterStatus.asStateFlow()

  fun setProviderFilter(status: ApprovalStatus?) {
    _providerFilterStatus.value = status
  }

  fun approveProvider(providerId: String) {
    viewModelScope.launch {
      repository.updateProviderApproval(providerId, ApprovalStatus.APPROVED)
    }
  }

  fun rejectProvider(providerId: String) {
    viewModelScope.launch {
      repository.updateProviderApproval(providerId, ApprovalStatus.REJECTED)
    }
  }

  fun suspendProvider(providerId: String) {
    viewModelScope.launch {
      repository.updateProviderApproval(providerId, ApprovalStatus.SUSPENDED)
    }
  }

  fun verifyFssai(providerId: String, verified: Boolean) {
    viewModelScope.launch {
      repository.verifyFssaiDocument(providerId, verified)
    }
  }

  fun verifyDrugLicence(providerId: String, verified: Boolean) {
    viewModelScope.launch {
      repository.verifyDrugLicenceDocument(providerId, verified)
    }
  }

  fun addNewCity(name: String, state: String, areasList: List<String>) {
    viewModelScope.launch {
      val cityId = name.lowercase().replace(" ", "_")
      val city = City(
        id = cityId,
        name = name,
        state = state,
        isActive = true,
        areas = areasList
      )
      repository.addOrUpdateCity(city)
    }
  }

  fun addAreaToCity(city: City, newArea: String) {
    if (newArea.isBlank()) return
    viewModelScope.launch {
      val updatedAreas = (city.areas + newArea.trim()).distinct()
      repository.addOrUpdateCity(city.copy(areas = updatedAreas))
    }
  }

  fun toggleCityActive(city: City) {
    viewModelScope.launch {
      repository.addOrUpdateCity(city.copy(isActive = !city.isActive))
    }
  }

  fun addNewCategory(name: String, description: String, iconName: String) {
    viewModelScope.launch {
      val catId = "cat_" + name.lowercase().replace("[^a-z0-9]".toRegex(), "_")
      val category = Category(
        id = catId,
        name = name,
        iconName = iconName.ifBlank { "category" },
        description = description,
        isActive = true,
        displayOrder = (allCategories.value.maxOfOrNull { it.displayOrder } ?: 0) + 1
      )
      repository.addOrUpdateCategory(category)
    }
  }

  fun toggleCategoryActive(category: Category) {
    viewModelScope.launch {
      repository.addOrUpdateCategory(category.copy(isActive = !category.isActive))
    }
  }

  fun updateAdminRequestStatus(requestId: String, status: RequestStatus) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, status)
    }
  }
}
