package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.SearchFilter
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.repository.OwnShopRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MarketplaceViewModel(
  private val repository: OwnShopRepository
) : ViewModel() {

  val cities: StateFlow<List<City>> = repository.getActiveCities()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val categories: StateFlow<List<Category>> = repository.getActiveCategories()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _selectedCity = MutableStateFlow<City?>(null)
  val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

  private val _searchFilter = MutableStateFlow(SearchFilter(cityId = "jaipur"))
  val searchFilter: StateFlow<SearchFilter> = _searchFilter.asStateFlow()

  init {
    viewModelScope.launch {
      cities.collect { list ->
        if (list.isNotEmpty() && _selectedCity.value == null) {
          val defaultCity = list.find { it.id == "jaipur" } ?: list.first()
          _selectedCity.value = defaultCity
          _searchFilter.value = _searchFilter.value.copy(cityId = defaultCity.id)
        }
      }
    }
  }

  fun selectCity(city: City) {
    _selectedCity.value = city
    _searchFilter.value = _searchFilter.value.copy(cityId = city.id, area = "")
  }

  fun updateSearchQuery(query: String) {
    _searchFilter.value = _searchFilter.value.copy(searchQuery = query)
  }

  fun updateCategoryFilter(categoryId: String) {
    val current = _searchFilter.value.categoryId
    val newCat = if (current == categoryId) "" else categoryId
    _searchFilter.value = _searchFilter.value.copy(categoryId = newCat)
  }

  fun updateAreaFilter(area: String) {
    val current = _searchFilter.value.area
    val newArea = if (current == area) "" else area
    _searchFilter.value = _searchFilter.value.copy(area = newArea)
  }

  fun updateRatingFilter(rating: Double) {
    _searchFilter.value = _searchFilter.value.copy(minRating = rating)
  }

  fun updatePriceFilter(maxPrice: Double?) {
    _searchFilter.value = _searchFilter.value.copy(maxPrice = maxPrice)
  }

  fun toggleAvailabilityOnly(onlyAvailable: Boolean) {
    _searchFilter.value = _searchFilter.value.copy(onlyAvailable = onlyAvailable)
  }

  fun clearFilters() {
    val currentCityId = _selectedCity.value?.id ?: "jaipur"
    _searchFilter.value = SearchFilter(cityId = currentCityId)
  }

  val filteredProviders: StateFlow<List<Provider>> = _searchFilter
    .flatMapLatest { filter ->
      repository.searchProviders(filter)
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun getServicesForProvider(providerId: String) = repository.getServicesForProvider(providerId)

  fun getReviewsForProvider(providerId: String) = repository.getReviewsForProvider(providerId)

  fun getCustomerRequests(customerId: String) = repository.getCustomerRequests(customerId)

  fun createServiceRequest(
    customer: UserAccount,
    provider: Provider,
    category: Category,
    service: ServiceItem,
    quantity: Int,
    preferredDate: String,
    preferredTime: String,
    notes: String,
    onSuccess: (ServiceRequest) -> Unit
  ) {
    viewModelScope.launch {
      val cityId = provider.cityId
      val cityName = provider.cityName
      val area = customer.address.split(",").firstOrNull()?.trim() ?: provider.serviceAreas.firstOrNull() ?: cityName
      val req = repository.createServiceRequest(
        customerId = customer.id,
        customerName = customer.name,
        customerPhone = customer.phone,
        customerAddress = customer.address,
        cityId = cityId,
        cityName = cityName,
        area = area,
        provider = provider,
        category = category,
        service = service,
        quantity = quantity,
        preferredDate = preferredDate,
        preferredTime = preferredTime,
        descriptionNotes = notes
      )
      onSuccess(req)
    }
  }

  fun cancelRequest(requestId: String, reason: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, RequestStatus.CANCELLED, reason)
    }
  }

  fun submitReview(
    customer: UserAccount,
    providerId: String,
    requestId: String,
    rating: Int,
    comment: String,
    onComplete: () -> Unit
  ) {
    viewModelScope.launch {
      repository.submitReview(
        customerId = customer.id,
        customerName = customer.name,
        providerId = providerId,
        requestId = requestId,
        rating = rating,
        comment = comment
      )
      onComplete()
    }
  }
}
