package com.example.data.repository

import com.example.data.demo.SeedDemoData
import com.example.data.local.OwnShopDatabase
import com.example.data.model.ApprovalStatus
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.PlatformStats
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.Review
import com.example.data.model.SearchFilter
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.service.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class OwnShopRepository(
  private val db: OwnShopDatabase,
  val firestoreService: FirestoreService = FirestoreService()
) {

  private val cityDao = db.cityDao()
  private val categoryDao = db.categoryDao()
  private val providerDao = db.providerDao()
  private val serviceDao = db.serviceDao()
  private val requestDao = db.requestDao()
  private val reviewDao = db.reviewDao()
  private val userDao = db.userDao()

  init {
    CoroutineScope(Dispatchers.IO).launch {
      seedInitialDataIfNeeded()
    }
  }

  suspend fun seedInitialDataIfNeeded() = withContext(Dispatchers.IO) {
    val existingCities = cityDao.getAllCities().first()
    if (existingCities.isEmpty()) {
      cityDao.insertCities(SeedDemoData.cities)
      categoryDao.insertCategories(SeedDemoData.categories)
      providerDao.insertProviders(SeedDemoData.providers)
      serviceDao.insertServices(SeedDemoData.services)
      requestDao.insertRequests(SeedDemoData.sampleRequests)
      reviewDao.insertReviews(SeedDemoData.sampleReviews)
      userDao.insertUsers(SeedDemoData.defaultUsers)
    }
  }

  // CITIES
  fun getActiveCities(): Flow<List<City>> = cityDao.getAllActiveCities()
  fun getAllCities(): Flow<List<City>> = cityDao.getAllCities()
  suspend fun getCityById(id: String): City? = cityDao.getCityById(id)
  suspend fun addOrUpdateCity(city: City) = withContext(Dispatchers.IO) {
    cityDao.insertCity(city)
  }

  // CATEGORIES
  fun getActiveCategories(): Flow<List<Category>> = categoryDao.getAllActiveCategories()
  fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
  suspend fun getCategoryById(id: String): Category? = categoryDao.getCategoryById(id)
  suspend fun addOrUpdateCategory(category: Category) = withContext(Dispatchers.IO) {
    categoryDao.insertCategory(category)
  }

  // PROVIDERS
  fun getApprovedProviders(): Flow<List<Provider>> = providerDao.getApprovedProviders()
  fun getApprovedProvidersByCity(cityId: String): Flow<List<Provider>> = providerDao.getApprovedProvidersByCity(cityId)
  fun getAllProviders(): Flow<List<Provider>> = providerDao.getAllProviders()
  fun getProviderByIdFlow(id: String): Flow<Provider?> = providerDao.getProviderByIdFlow(id)
  suspend fun getProviderById(id: String): Provider? = providerDao.getProviderById(id)

  suspend fun registerProvider(provider: Provider) = withContext(Dispatchers.IO) {
    providerDao.insertProvider(provider)
  }

  suspend fun updateProvider(provider: Provider) = withContext(Dispatchers.IO) {
    providerDao.updateProvider(provider)
  }

  suspend fun updateProviderApproval(providerId: String, status: ApprovalStatus) = withContext(Dispatchers.IO) {
    providerDao.updateApprovalStatus(providerId, status)
  }

  suspend fun verifyFssaiDocument(providerId: String, verified: Boolean) = withContext(Dispatchers.IO) {
    providerDao.updateFssaiVerification(providerId, verified)
  }

  suspend fun verifyDrugLicenceDocument(providerId: String, verified: Boolean) = withContext(Dispatchers.IO) {
    providerDao.updateDrugLicenceVerification(providerId, verified)
  }

  // SERVICES
  fun getServicesForProvider(providerId: String): Flow<List<ServiceItem>> = serviceDao.getServicesByProvider(providerId)
  suspend fun addOrUpdateService(service: ServiceItem) = withContext(Dispatchers.IO) {
    serviceDao.insertService(service)
    try {
      firestoreService.saveProductListing(service)
    } catch (_: Exception) {}
  }
  suspend fun deleteService(service: ServiceItem) = withContext(Dispatchers.IO) {
    serviceDao.deleteService(service)
    try {
      firestoreService.deleteProductListing(service.id)
    } catch (_: Exception) {}
  }

  // REQUESTS & ORDERS
  fun getAllRequests(): Flow<List<ServiceRequest>> = requestDao.getAllRequests()
  fun getCustomerRequests(customerId: String): Flow<List<ServiceRequest>> = requestDao.getRequestsByCustomer(customerId)
  fun getProviderRequests(providerId: String): Flow<List<ServiceRequest>> = requestDao.getRequestsByProvider(providerId)

  suspend fun createServiceRequest(
    customerId: String,
    customerName: String,
    customerPhone: String,
    customerAddress: String,
    cityId: String,
    cityName: String,
    area: String,
    provider: Provider,
    category: Category,
    service: ServiceItem,
    quantity: Int,
    preferredDate: String,
    preferredTime: String,
    descriptionNotes: String
  ): ServiceRequest = withContext(Dispatchers.IO) {
    val totalAmount = service.price * quantity
    val request = ServiceRequest(
      id = "req_${UUID.randomUUID().toString().take(8)}",
      customerId = customerId,
      customerName = customerName,
      customerPhone = customerPhone,
      customerAddress = customerAddress,
      cityId = cityId,
      cityName = cityName,
      area = area,
      providerId = provider.id,
      providerName = provider.businessName,
      providerPhone = provider.phone,
      categoryId = category.id,
      categoryName = category.name,
      serviceId = service.id,
      serviceName = service.title,
      price = service.price,
      quantity = quantity,
      totalAmount = totalAmount,
      preferredDate = preferredDate,
      preferredTime = preferredTime,
      descriptionNotes = descriptionNotes,
      status = RequestStatus.PENDING
    )
    requestDao.insertRequest(request)
    request
  }

  suspend fun updateRequestStatus(
    requestId: String,
    status: RequestStatus,
    reason: String? = null
  ) = withContext(Dispatchers.IO) {
    val existing = requestDao.getRequestById(requestId)
    if (existing != null) {
      val updated = existing.copy(
        status = status,
        rejectionReason = if (status == RequestStatus.REJECTED) reason else existing.rejectionReason,
        cancellationReason = if (status == RequestStatus.CANCELLED) reason else existing.cancellationReason,
        updatedAt = System.currentTimeMillis()
      )
      requestDao.updateRequest(updated)
    }
  }

  // REVIEWS
  fun getReviewsForProvider(providerId: String): Flow<List<Review>> = reviewDao.getReviewsByProvider(providerId)
  suspend fun getReviewByRequestId(requestId: String): Review? = reviewDao.getReviewByRequestId(requestId)

  suspend fun submitReview(
    customerId: String,
    customerName: String,
    providerId: String,
    requestId: String,
    rating: Int,
    comment: String
  ) = withContext(Dispatchers.IO) {
    val review = Review(
      id = "rev_${UUID.randomUUID().toString().take(8)}",
      customerId = customerId,
      customerName = customerName,
      providerId = providerId,
      requestId = requestId,
      rating = rating,
      comment = comment
    )
    reviewDao.insertReview(review)
    // Recalculate provider rating
    val avg = reviewDao.getAverageRating(providerId) ?: 5.0
    val count = reviewDao.getReviewCount(providerId)
    val roundedAvg = Math.round(avg * 10.0) / 10.0
    providerDao.updateRatingAndReviewCount(providerId, roundedAvg, count)
  }

  // USERS & ADMIN STATS
  fun getAllUsers(): Flow<List<UserAccount>> = userDao.getAllUsers()

  fun getPlatformStats(): Flow<PlatformStats> = combine(
    userDao.getAllUsers(),
    providerDao.getAllProviders(),
    requestDao.getAllRequests(),
    cityDao.getAllActiveCities()
  ) { users, providers, requests, cities ->
    PlatformStats(
      totalUsers = users.size,
      totalProviders = providers.size,
      pendingProviders = providers.count { it.approvalStatus == ApprovalStatus.PENDING },
      approvedProviders = providers.count { it.approvalStatus == ApprovalStatus.APPROVED },
      totalRequests = requests.size,
      pendingRequests = requests.count { it.status == RequestStatus.PENDING },
      completedRequests = requests.count { it.status == RequestStatus.COMPLETED },
      activeCities = cities.size
    )
  }

  // FILTERED SEARCH
  fun searchProviders(
    filter: SearchFilter
  ): Flow<List<Provider>> {
    val baseFlow = if (filter.cityId.isNotBlank()) {
      providerDao.getApprovedProvidersByCity(filter.cityId)
    } else {
      providerDao.getApprovedProviders()
    }

    return combine(baseFlow) { providersArray ->
      val list = providersArray[0]
      list.filter { provider ->
        val matchesQuery = filter.searchQuery.isBlank() ||
            provider.businessName.contains(filter.searchQuery, ignoreCase = true) ||
            provider.description.contains(filter.searchQuery, ignoreCase = true) ||
            provider.serviceAreas.any { it.contains(filter.searchQuery, ignoreCase = true) }

        val matchesArea = filter.area.isBlank() || provider.serviceAreas.any { it.equals(filter.area, ignoreCase = true) }

        val matchesCategory = filter.categoryId.isBlank() || provider.categoryIds.contains(filter.categoryId)

        val matchesRating = provider.rating >= filter.minRating

        val matchesPrice = filter.maxPrice == null || provider.startingPrice <= filter.maxPrice

        val matchesAvailability = !filter.onlyAvailable || provider.isAvailable

        matchesQuery && matchesArea && matchesCategory && matchesRating && matchesPrice && matchesAvailability
      }
    }
  }
}
