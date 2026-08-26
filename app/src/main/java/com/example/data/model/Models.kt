package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.local.Converters

enum class UserRole {
  CUSTOMER,
  PROVIDER,
  ADMIN
}

enum class ApprovalStatus {
  PENDING,
  APPROVED,
  REJECTED,
  SUSPENDED
}

enum class RequestStatus {
  PENDING,
  ACCEPTED,
  REJECTED,
  IN_PROGRESS,
  COMPLETED,
  CANCELLED
}

@Entity(tableName = "cities")
data class City(
  @PrimaryKey val id: String,
  val name: String,
  val state: String,
  val isActive: Boolean = true,
  @field:TypeConverters(Converters::class)
  val areas: List<String> = emptyList()
)

@Entity(tableName = "categories")
data class Category(
  @PrimaryKey val id: String,
  val name: String,
  val iconName: String,
  val description: String,
  val isActive: Boolean = true,
  val displayOrder: Int = 0,
  val bannerHexColor: String = "#1E3A8A"
)

@Entity(tableName = "providers")
data class Provider(
  @PrimaryKey val id: String,
  val businessName: String,
  val ownerName: String,
  val phone: String,
  val email: String,
  val cityId: String,
  val cityName: String,
  @field:TypeConverters(Converters::class)
  val serviceAreas: List<String> = emptyList(),
  @field:TypeConverters(Converters::class)
  val categoryIds: List<String> = emptyList(),
  val description: String = "",
  val startingPrice: Double = 0.0,
  val experienceYears: Int = 1,
  val rating: Double = 4.5,
  val reviewCount: Int = 0,
  val isAvailable: Boolean = true,
  val approvalStatus: ApprovalStatus = ApprovalStatus.PENDING,
  // Legal Compliance
  val fssaiNumber: String? = null,
  val fssaiDocumentUrl: String? = null,
  val isFssaiVerified: Boolean = false,
  val drugLicenceNumber: String? = null,
  val drugLicenceDocUrl: String? = null,
  val isDrugLicenceVerified: Boolean = false,
  val address: String = "",
  val avatarUrl: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "services")
data class ServiceItem(
  @PrimaryKey val id: String,
  val providerId: String,
  val cityId: String,
  val categoryId: String,
  val title: String,
  val description: String,
  val price: Double,
  val priceUnit: String = "per service", // e.g., "per visit", "per hour", "per item"
  val isAvailable: Boolean = true,
  val estimatedTime: String = "30-60 mins"
)

@Entity(tableName = "service_requests")
data class ServiceRequest(
  @PrimaryKey val id: String,
  val customerId: String,
  val customerName: String,
  val customerPhone: String,
  val customerAddress: String,
  val cityId: String,
  val cityName: String,
  val area: String,
  val providerId: String,
  val providerName: String,
  val providerPhone: String,
  val categoryId: String,
  val categoryName: String,
  val serviceId: String,
  val serviceName: String,
  val price: Double,
  val quantity: Int = 1,
  val totalAmount: Double,
  val preferredDate: String,
  val preferredTime: String,
  val descriptionNotes: String = "",
  val status: RequestStatus = RequestStatus.PENDING,
  val rejectionReason: String? = null,
  val cancellationReason: String? = null,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reviews")
data class Review(
  @PrimaryKey val id: String,
  val customerId: String,
  val customerName: String,
  val providerId: String,
  val requestId: String,
  val rating: Int, // 1 to 5
  val comment: String,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "users")
data class UserAccount(
  @PrimaryKey val id: String,
  val name: String,
  val email: String,
  val phone: String,
  val role: UserRole = UserRole.CUSTOMER,
  val cityId: String = "jaipur",
  val cityName: String = "Jaipur",
  val address: String = "",
  val providerId: String? = null, // Linked if provider
  val createdAt: Long = System.currentTimeMillis()
)

data class PlatformStats(
  val totalUsers: Int = 0,
  val totalProviders: Int = 0,
  val pendingProviders: Int = 0,
  val approvedProviders: Int = 0,
  val totalRequests: Int = 0,
  val pendingRequests: Int = 0,
  val completedRequests: Int = 0,
  val activeCities: Int = 0
)

data class SearchFilter(
  val searchQuery: String = "",
  val cityId: String = "",
  val area: String = "",
  val categoryId: String = "",
  val minRating: Double = 0.0,
  val maxPrice: Double? = null,
  val onlyAvailable: Boolean = false
)
