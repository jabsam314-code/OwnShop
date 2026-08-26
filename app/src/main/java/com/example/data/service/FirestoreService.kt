package com.example.data.service

import android.util.Log
import com.example.data.model.ServiceItem
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreService {

  private val tag = "FirestoreService"

  private val firestore: FirebaseFirestore? by lazy {
    try {
      FirebaseFirestore.getInstance()
    } catch (e: Exception) {
      Log.w(tag, "Firebase Firestore initialization skipped/unavailable: ${e.message}")
      null
    }
  }

  val isAvailable: Boolean
    get() = firestore != null

  // ==========================================
  // MARKETPLACE PRODUCT / SERVICE LISTINGS
  // ==========================================

  suspend fun saveProductListing(service: ServiceItem): Result<Unit> {
    val db = firestore ?: return Result.failure(IllegalStateException("Firestore is not initialized"))
    return try {
      val data = mapOf(
        "id" to service.id,
        "providerId" to service.providerId,
        "cityId" to service.cityId,
        "categoryId" to service.categoryId,
        "title" to service.title,
        "description" to service.description,
        "price" to service.price,
        "priceUnit" to service.priceUnit,
        "isAvailable" to service.isAvailable,
        "estimatedTime" to service.estimatedTime,
        "updatedAt" to System.currentTimeMillis()
      )
      db.collection("products")
        .document(service.id)
        .set(data, SetOptions.merge())
        .await()
      Log.d(tag, "Product listing saved to Firestore: ${service.id}")
      Result.success(Unit)
    } catch (e: Exception) {
      Log.e(tag, "Error saving product listing to Firestore", e)
      Result.failure(e)
    }
  }

  suspend fun deleteProductListing(serviceId: String): Result<Unit> {
    val db = firestore ?: return Result.failure(IllegalStateException("Firestore is not initialized"))
    return try {
      db.collection("products")
        .document(serviceId)
        .delete()
        .await()
      Log.d(tag, "Product listing deleted from Firestore: $serviceId")
      Result.success(Unit)
    } catch (e: Exception) {
      Log.e(tag, "Error deleting product listing from Firestore", e)
      Result.failure(e)
    }
  }

  fun observeProductListings(): Flow<List<ServiceItem>> = callbackFlow {
    val db = firestore
    if (db == null) {
      trySend(emptyList())
      close()
      return@callbackFlow
    }

    val subscription = db.collection("products")
      .addSnapshotListener { snapshot, error ->
        if (error != null) {
          Log.e(tag, "Listen failed for products", error)
          return@addSnapshotListener
        }
        if (snapshot != null) {
          val items = snapshot.documents.mapNotNull { doc ->
            try {
              ServiceItem(
                id = doc.getString("id") ?: doc.id,
                providerId = doc.getString("providerId") ?: "",
                cityId = doc.getString("cityId") ?: "",
                categoryId = doc.getString("categoryId") ?: "",
                title = doc.getString("title") ?: "",
                description = doc.getString("description") ?: "",
                price = doc.getDouble("price") ?: 0.0,
                priceUnit = doc.getString("priceUnit") ?: "per service",
                isAvailable = doc.getBoolean("isAvailable") ?: true,
                estimatedTime = doc.getString("estimatedTime") ?: "30-60 mins"
              )
            } catch (e: Exception) {
              Log.w(tag, "Failed to parse product document: ${doc.id}", e)
              null
            }
          }
          trySend(items)
        }
      }

    awaitClose { subscription.remove() }
  }

  // ==========================================
  // USER PROFILE DATA
  // ==========================================

  suspend fun saveUserProfile(user: UserAccount): Result<Unit> {
    val db = firestore ?: return Result.failure(IllegalStateException("Firestore is not initialized"))
    return try {
      val data = mapOf(
        "id" to user.id,
        "name" to user.name,
        "email" to user.email,
        "phone" to user.phone,
        "role" to user.role.name,
        "cityId" to user.cityId,
        "cityName" to user.cityName,
        "address" to user.address,
        "providerId" to user.providerId,
        "createdAt" to user.createdAt,
        "updatedAt" to System.currentTimeMillis()
      )
      db.collection("users")
        .document(user.id)
        .set(data, SetOptions.merge())
        .await()
      Log.d(tag, "User profile saved to Firestore: ${user.id}")
      Result.success(Unit)
    } catch (e: Exception) {
      Log.e(tag, "Error saving user profile to Firestore", e)
      Result.failure(e)
    }
  }

  suspend fun getUserProfile(userId: String): Result<UserAccount?> {
    val db = firestore ?: return Result.failure(IllegalStateException("Firestore is not initialized"))
    return try {
      val doc = db.collection("users").document(userId).get().await()
      if (doc.exists()) {
        val roleStr = doc.getString("role") ?: UserRole.CUSTOMER.name
        val role = try {
          UserRole.valueOf(roleStr)
        } catch (e: Exception) {
          UserRole.CUSTOMER
        }
        val user = UserAccount(
          id = doc.getString("id") ?: doc.id,
          name = doc.getString("name") ?: "",
          email = doc.getString("email") ?: "",
          phone = doc.getString("phone") ?: "",
          role = role,
          cityId = doc.getString("cityId") ?: "jaipur",
          cityName = doc.getString("cityName") ?: "Jaipur",
          address = doc.getString("address") ?: "",
          providerId = doc.getString("providerId"),
          createdAt = doc.getLong("createdAt") ?: System.currentTimeMillis()
        )
        Result.success(user)
      } else {
        Result.success(null)
      }
    } catch (e: Exception) {
      Log.e(tag, "Error fetching user profile from Firestore", e)
      Result.failure(e)
    }
  }

  fun observeUserProfile(userId: String): Flow<UserAccount?> = callbackFlow {
    val db = firestore
    if (db == null) {
      trySend(null)
      close()
      return@callbackFlow
    }

    val subscription = db.collection("users").document(userId)
      .addSnapshotListener { doc, error ->
        if (error != null) {
          Log.e(tag, "Listen failed for user $userId", error)
          return@addSnapshotListener
        }
        if (doc != null && doc.exists()) {
          val roleStr = doc.getString("role") ?: UserRole.CUSTOMER.name
          val role = try {
            UserRole.valueOf(roleStr)
          } catch (e: Exception) {
            UserRole.CUSTOMER
          }
          val user = UserAccount(
            id = doc.getString("id") ?: doc.id,
            name = doc.getString("name") ?: "",
            email = doc.getString("email") ?: "",
            phone = doc.getString("phone") ?: "",
            role = role,
            cityId = doc.getString("cityId") ?: "jaipur",
            cityName = doc.getString("cityName") ?: "Jaipur",
            address = doc.getString("address") ?: "",
            providerId = doc.getString("providerId"),
            createdAt = doc.getLong("createdAt") ?: System.currentTimeMillis()
          )
          trySend(user)
        } else {
          trySend(null)
        }
      }

    awaitClose { subscription.remove() }
  }
}
