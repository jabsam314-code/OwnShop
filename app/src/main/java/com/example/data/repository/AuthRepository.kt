package com.example.data.repository

import com.example.data.demo.SeedDemoData
import com.example.data.local.OwnShopDatabase
import com.example.data.model.ApprovalStatus
import com.example.data.model.Provider
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.data.service.FirestoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.UUID

class AuthRepository(
  private val db: OwnShopDatabase,
  private val firestoreService: FirestoreService = FirestoreService()
) {

  private val userDao = db.userDao()
  private val providerDao = db.providerDao()

  // Default initial active user is customer
  private val _currentUser = MutableStateFlow<UserAccount?>(SeedDemoData.defaultUsers[0])
  val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

  // Quick switch role between default demo users or custom user
  suspend fun switchRole(role: UserRole) = withContext(Dispatchers.IO) {
    val demoUser = when (role) {
      UserRole.CUSTOMER -> SeedDemoData.defaultUsers[0]
      UserRole.PROVIDER -> SeedDemoData.defaultUsers[1]
      UserRole.ADMIN -> SeedDemoData.defaultUsers[2]
    }
    _currentUser.value = demoUser
  }

  suspend fun login(email: String, password: String): Result<UserAccount> = withContext(Dispatchers.IO) {
    val user = userDao.getUserByEmail(email)
    if (user != null) {
      _currentUser.value = user
      Result.success(user)
    } else {
      // Allow demo direct login for quick testing
      val defaultUser = SeedDemoData.defaultUsers.find { it.email.equals(email, ignoreCase = true) }
      if (defaultUser != null) {
        _currentUser.value = defaultUser
        Result.success(defaultUser)
      } else {
        Result.failure(Exception("Invalid email or user not found. Try customer@ownshop.com, provider@ownshop.com, or admin@ownshop.com"))
      }
    }
  }

  suspend fun registerCustomer(
    name: String,
    email: String,
    phone: String,
    cityId: String,
    cityName: String,
    address: String
  ): Result<UserAccount> = withContext(Dispatchers.IO) {
    val existing = userDao.getUserByEmail(email)
    if (existing != null) {
      return@withContext Result.failure(Exception("User with this email already exists."))
    }
    val newUser = UserAccount(
      id = "user_${UUID.randomUUID().toString().take(8)}",
      name = name,
      email = email,
      phone = phone,
      role = UserRole.CUSTOMER,
      cityId = cityId,
      cityName = cityName,
      address = address
    )
    userDao.insertUser(newUser)
    try {
      firestoreService.saveUserProfile(newUser)
    } catch (_: Exception) {}
    _currentUser.value = newUser
    Result.success(newUser)
  }

  suspend fun registerProvider(
    businessName: String,
    ownerName: String,
    email: String,
    phone: String,
    cityId: String,
    cityName: String,
    serviceAreas: List<String>,
    categoryIds: List<String>,
    description: String,
    startingPrice: Double,
    experienceYears: Int,
    address: String,
    fssaiNumber: String?,
    drugLicenceNumber: String?
  ): Result<UserAccount> = withContext(Dispatchers.IO) {
    val providerId = "prov_${UUID.randomUUID().toString().take(8)}"
    val provider = Provider(
      id = providerId,
      businessName = businessName,
      ownerName = ownerName,
      phone = phone,
      email = email,
      cityId = cityId,
      cityName = cityName,
      serviceAreas = serviceAreas,
      categoryIds = categoryIds,
      description = description,
      startingPrice = startingPrice,
      experienceYears = experienceYears,
      rating = 5.0,
      reviewCount = 0,
      isAvailable = true,
      approvalStatus = ApprovalStatus.PENDING,
      fssaiNumber = fssaiNumber?.takeIf { it.isNotBlank() },
      fssaiDocumentUrl = if (!fssaiNumber.isNullOrBlank()) "fssai_doc_${providerId}.pdf" else null,
      isFssaiVerified = false,
      drugLicenceNumber = drugLicenceNumber?.takeIf { it.isNotBlank() },
      drugLicenceDocUrl = if (!drugLicenceNumber.isNullOrBlank()) "drug_licence_${providerId}.pdf" else null,
      isDrugLicenceVerified = false,
      address = address
    )
    providerDao.insertProvider(provider)

    val newUser = UserAccount(
      id = "user_${UUID.randomUUID().toString().take(8)}",
      name = ownerName,
      email = email,
      phone = phone,
      role = UserRole.PROVIDER,
      cityId = cityId,
      cityName = cityName,
      address = address,
      providerId = providerId
    )
    userDao.insertUser(newUser)
    try {
      firestoreService.saveUserProfile(newUser)
    } catch (_: Exception) {}
    _currentUser.value = newUser
    Result.success(newUser)
  }

  fun logout() {
    _currentUser.value = null
  }

  suspend fun updateCurrentUserProfile(updated: UserAccount) = withContext(Dispatchers.IO) {
    userDao.insertUser(updated)
    try {
      firestoreService.saveUserProfile(updated)
    } catch (_: Exception) {}
    _currentUser.value = updated
  }
}
