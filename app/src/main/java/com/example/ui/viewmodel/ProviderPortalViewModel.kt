package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.repository.OwnShopRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ProviderPortalViewModel(
  private val repository: OwnShopRepository
) : ViewModel() {

  private val _currentProviderId = MutableStateFlow<String?>("prov_jpr_ac_1")
  val currentProviderId: StateFlow<String?> = _currentProviderId.asStateFlow()

  fun setProviderFromUser(user: UserAccount?) {
    if (user?.providerId != null) {
      _currentProviderId.value = user.providerId
    } else {
      _currentProviderId.value = "prov_jpr_ac_1" // Fallback to demo provider
    }
  }

  val providerDetails: StateFlow<Provider?> = _currentProviderId
    .flatMapLatest { id ->
      if (id != null) repository.getProviderByIdFlow(id) else flowOf(null)
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  val providerRequests: StateFlow<List<ServiceRequest>> = _currentProviderId
    .flatMapLatest { id ->
      if (id != null) repository.getProviderRequests(id) else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val providerServices: StateFlow<List<ServiceItem>> = _currentProviderId
    .flatMapLatest { id ->
      if (id != null) repository.getServicesForProvider(id) else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun acceptRequest(requestId: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.ACCEPTED)
    }
  }

  fun rejectRequest(requestId: String, reason: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.REJECTED, reason)
    }
  }

  fun startService(requestId: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.IN_PROGRESS)
    }
  }

  fun completeService(requestId: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.COMPLETED)
    }
  }

  fun cancelService(requestId: String, reason: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.CANCELLED, reason)
    }
  }

  fun toggleAvailability() {
    viewModelScope.launch {
      val prov = providerDetails.value ?: return@launch
      repository.updateProvider(prov.copy(isAvailable = !prov.isAvailable))
    }
  }

  fun addService(
    title: String,
    description: String,
    price: Double,
    priceUnit: String,
    estimatedTime: String
  ) {
    val prov = providerDetails.value ?: return
    viewModelScope.launch {
      val service = ServiceItem(
        id = "srv_${UUID.randomUUID().toString().take(8)}",
        providerId = prov.id,
        cityId = prov.cityId,
        categoryId = prov.categoryIds.firstOrNull() ?: "cat_other_services",
        title = title,
        description = description,
        price = price,
        priceUnit = priceUnit,
        isAvailable = true,
        estimatedTime = estimatedTime
      )
      repository.addOrUpdateService(service)
    }
  }

  fun deleteService(service: ServiceItem) {
    viewModelScope.launch {
      repository.deleteService(service)
    }
  }

  fun submitFssai(number: String) {
    val prov = providerDetails.value ?: return
    viewModelScope.launch {
      repository.updateProvider(
        prov.copy(
          fssaiNumber = number,
          fssaiDocumentUrl = "fssai_doc_${prov.id}.pdf",
          isFssaiVerified = false // Needs admin review
        )
      )
    }
  }

  fun submitDrugLicence(number: String) {
    val prov = providerDetails.value ?: return
    viewModelScope.launch {
      repository.updateProvider(
        prov.copy(
          drugLicenceNumber = number,
          drugLicenceDocUrl = "drug_licence_${prov.id}.pdf",
          isDrugLicenceVerified = false // Needs admin review
        )
      )
    }
  }

  fun updateBusinessProfile(
    businessName: String,
    description: String,
    startingPrice: Double,
    experienceYears: Int,
    address: String,
    areas: List<String>
  ) {
    val prov = providerDetails.value ?: return
    viewModelScope.launch {
      repository.updateProvider(
        prov.copy(
          businessName = businessName,
          description = description,
          startingPrice = startingPrice,
          experienceYears = experienceYears,
          address = address,
          serviceAreas = areas
        )
      )
    }
  }
}
