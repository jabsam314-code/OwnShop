package com.example.data.service

/**
 * Modular Delivery Service Abstraction
 * Allows pluggable integration of delivery partners (e.g. Rapido, Porter, Dunzo, Shadowfax)
 * without modifying the core order/request pipelines.
 */
interface DeliveryService {
  suspend fun estimateDeliveryFee(cityId: String, pickupArea: String, dropArea: String): Double
  suspend fun estimateTimeMinutes(cityId: String, pickupArea: String, dropArea: String): Int
  suspend fun schedulePickup(requestId: String, providerAddress: String, customerAddress: String): DeliveryBookingResult
  suspend fun trackDelivery(trackingId: String): DeliveryTrackingStatus
}

data class DeliveryBookingResult(
  val success: Boolean,
  val trackingId: String?,
  val estimatedArrivalMinutes: Int,
  val partnerName: String = "OwnShop Direct Dispatch"
)

data class DeliveryTrackingStatus(
  val trackingId: String,
  val status: String,
  val currentEtaMinutes: Int,
  val partnerName: String
)

class OwnShopLocalDeliveryService : DeliveryService {
  override suspend fun estimateDeliveryFee(cityId: String, pickupArea: String, dropArea: String): Double {
    return if (pickupArea.equals(dropArea, ignoreCase = true)) 30.0 else 60.0
  }

  override suspend fun estimateTimeMinutes(cityId: String, pickupArea: String, dropArea: String): Int {
    return if (pickupArea.equals(dropArea, ignoreCase = true)) 20 else 45
  }

  override suspend fun schedulePickup(
    requestId: String,
    providerAddress: String,
    customerAddress: String
  ): DeliveryBookingResult {
    return DeliveryBookingResult(
      success = true,
      trackingId = "OWN-DEL-$requestId",
      estimatedArrivalMinutes = 35,
      partnerName = "OwnShop Express Local"
    )
  }

  override suspend fun trackDelivery(trackingId: String): DeliveryTrackingStatus {
    return DeliveryTrackingStatus(
      trackingId = trackingId,
      status = "Out for delivery with local partner",
      currentEtaMinutes = 20,
      partnerName = "OwnShop Express Local"
    )
  }
}
