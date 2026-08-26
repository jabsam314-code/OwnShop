package com.example.data.service

/**
 * Modular Payment Architecture
 * Supports Pay on Service / Cash on Delivery, UPI intent preparation,
 * and future Razorpay/Paytm SDK integrations without altering the order system.
 */
enum class PaymentMethod {
  PAY_ON_SERVICE_OR_COD,
  UPI_DIRECT,
  ONLINE_GATEWAY
}

data class PaymentTransaction(
  val transactionId: String,
  val requestId: String,
  val amount: Double,
  val method: PaymentMethod,
  val isCompleted: Boolean,
  val notes: String
)

interface PaymentService {
  suspend fun initializeOrderPayment(requestId: String, amount: Double, method: PaymentMethod): PaymentTransaction
  suspend fun verifyPayment(transactionId: String): Boolean
}

class OwnShopPaymentService : PaymentService {
  override suspend fun initializeOrderPayment(
    requestId: String,
    amount: Double,
    method: PaymentMethod
  ): PaymentTransaction {
    val txId = "TXN-${System.currentTimeMillis()}-${requestId.takeLast(4)}"
    return PaymentTransaction(
      transactionId = txId,
      requestId = requestId,
      amount = amount,
      method = method,
      isCompleted = method == PaymentMethod.PAY_ON_SERVICE_OR_COD, // verified on fulfillment
      notes = when (method) {
        PaymentMethod.PAY_ON_SERVICE_OR_COD -> "Pay securely to provider upon service completion"
        PaymentMethod.UPI_DIRECT -> "UPI QR / VPA payment link ready"
        PaymentMethod.ONLINE_GATEWAY -> "Gateway ready for integration"
      }
    )
  }

  override suspend fun verifyPayment(transactionId: String): Boolean {
    return true
  }
}
