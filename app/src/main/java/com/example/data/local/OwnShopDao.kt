package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ApprovalStatus
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.Review
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
  @Query("SELECT * FROM cities WHERE isActive = 1 ORDER BY name ASC")
  fun getAllActiveCities(): Flow<List<City>>

  @Query("SELECT * FROM cities ORDER BY name ASC")
  fun getAllCities(): Flow<List<City>>

  @Query("SELECT * FROM cities WHERE id = :cityId LIMIT 1")
  suspend fun getCityById(cityId: String): City?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCity(city: City)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCities(cities: List<City>)

  @Update
  suspend fun updateCity(city: City)
}

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories WHERE isActive = 1 ORDER BY displayOrder ASC, name ASC")
  fun getAllActiveCategories(): Flow<List<Category>>

  @Query("SELECT * FROM categories ORDER BY displayOrder ASC, name ASC")
  fun getAllCategories(): Flow<List<Category>>

  @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
  suspend fun getCategoryById(id: String): Category?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCategory(category: Category)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCategories(categories: List<Category>)

  @Update
  suspend fun updateCategory(category: Category)
}

@Dao
interface ProviderDao {
  @Query("SELECT * FROM providers WHERE approvalStatus = 'APPROVED' ORDER BY rating DESC, reviewCount DESC")
  fun getApprovedProviders(): Flow<List<Provider>>

  @Query("SELECT * FROM providers WHERE cityId = :cityId AND approvalStatus = 'APPROVED' ORDER BY rating DESC, reviewCount DESC")
  fun getApprovedProvidersByCity(cityId: String): Flow<List<Provider>>

  @Query("SELECT * FROM providers ORDER BY createdAt DESC")
  fun getAllProviders(): Flow<List<Provider>>

  @Query("SELECT * FROM providers WHERE id = :providerId LIMIT 1")
  fun getProviderByIdFlow(providerId: String): Flow<Provider?>

  @Query("SELECT * FROM providers WHERE id = :providerId LIMIT 1")
  suspend fun getProviderById(providerId: String): Provider?

  @Query("SELECT * FROM providers WHERE email = :email LIMIT 1")
  suspend fun getProviderByEmail(email: String): Provider?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProvider(provider: Provider)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProviders(providers: List<Provider>)

  @Update
  suspend fun updateProvider(provider: Provider)

  @Query("UPDATE providers SET approvalStatus = :status WHERE id = :providerId")
  suspend fun updateApprovalStatus(providerId: String, status: ApprovalStatus)

  @Query("UPDATE providers SET isFssaiVerified = :verified WHERE id = :providerId")
  suspend fun updateFssaiVerification(providerId: String, verified: Boolean)

  @Query("UPDATE providers SET isDrugLicenceVerified = :verified WHERE id = :providerId")
  suspend fun updateDrugLicenceVerification(providerId: String, verified: Boolean)

  @Query("UPDATE providers SET rating = :rating, reviewCount = :reviewCount WHERE id = :providerId")
  suspend fun updateRatingAndReviewCount(providerId: String, rating: Double, reviewCount: Int)
}

@Dao
interface ServiceDao {
  @Query("SELECT * FROM services WHERE providerId = :providerId AND isAvailable = 1")
  fun getServicesByProvider(providerId: String): Flow<List<ServiceItem>>

  @Query("SELECT * FROM services WHERE isAvailable = 1")
  fun getAllServices(): Flow<List<ServiceItem>>

  @Query("SELECT * FROM services WHERE id = :serviceId LIMIT 1")
  suspend fun getServiceById(serviceId: String): ServiceItem?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertService(service: ServiceItem)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertServices(services: List<ServiceItem>)

  @Update
  suspend fun updateService(service: ServiceItem)

  @Delete
  suspend fun deleteService(service: ServiceItem)
}

@Dao
interface RequestDao {
  @Query("SELECT * FROM service_requests ORDER BY createdAt DESC")
  fun getAllRequests(): Flow<List<ServiceRequest>>

  @Query("SELECT * FROM service_requests WHERE customerId = :customerId ORDER BY createdAt DESC")
  fun getRequestsByCustomer(customerId: String): Flow<List<ServiceRequest>>

  @Query("SELECT * FROM service_requests WHERE providerId = :providerId ORDER BY createdAt DESC")
  fun getRequestsByProvider(providerId: String): Flow<List<ServiceRequest>>

  @Query("SELECT * FROM service_requests WHERE id = :requestId LIMIT 1")
  suspend fun getRequestById(requestId: String): ServiceRequest?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRequest(request: ServiceRequest)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRequests(requests: List<ServiceRequest>)

  @Update
  suspend fun updateRequest(request: ServiceRequest)

  @Query("UPDATE service_requests SET status = :status, updatedAt = :updatedAt WHERE id = :requestId")
  suspend fun updateRequestStatus(requestId: String, status: RequestStatus, updatedAt: Long = System.currentTimeMillis())
}

@Dao
interface ReviewDao {
  @Query("SELECT * FROM reviews WHERE providerId = :providerId ORDER BY createdAt DESC")
  fun getReviewsByProvider(providerId: String): Flow<List<Review>>

  @Query("SELECT * FROM reviews WHERE requestId = :requestId LIMIT 1")
  suspend fun getReviewByRequestId(requestId: String): Review?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertReview(review: Review)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertReviews(reviews: List<Review>)

  @Query("SELECT AVG(rating) FROM reviews WHERE providerId = :providerId")
  suspend fun getAverageRating(providerId: String): Double?

  @Query("SELECT COUNT(*) FROM reviews WHERE providerId = :providerId")
  suspend fun getReviewCount(providerId: String): Int
}

@Dao
interface UserDao {
  @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
  suspend fun getUserByEmail(email: String): UserAccount?

  @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
  suspend fun getUserById(userId: String): UserAccount?

  @Query("SELECT * FROM users ORDER BY createdAt DESC")
  fun getAllUsers(): Flow<List<UserAccount>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: UserAccount)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUsers(users: List<UserAccount>)

  @Update
  suspend fun updateUser(user: UserAccount)
}
