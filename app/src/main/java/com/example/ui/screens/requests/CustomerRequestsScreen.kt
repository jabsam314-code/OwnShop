package com.example.ui.screens.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceRequest
import com.example.ui.components.AddReviewBottomSheet
import com.example.ui.components.StatusChip
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@Composable
fun CustomerRequestsScreen(
  requests: List<ServiceRequest>,
  onCancelRequest: (requestId: String, reason: String) -> Unit,
  onSubmitReview: (providerId: String, requestId: String, rating: Int, comment: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("All Requests", "Active", "Completed", "Cancelled")

  var requestToCancel by remember { mutableStateOf<ServiceRequest?>(null) }
  var cancelReason by remember { mutableStateOf("") }
  var requestToReview by remember { mutableStateOf<ServiceRequest?>(null) }

  val filteredRequests = remember(requests, selectedTab) {
    when (selectedTab) {
      1 -> requests.filter { it.status == RequestStatus.PENDING || it.status == RequestStatus.ACCEPTED || it.status == RequestStatus.IN_PROGRESS }
      2 -> requests.filter { it.status == RequestStatus.COMPLETED }
      3 -> requests.filter { it.status == RequestStatus.CANCELLED || it.status == RequestStatus.REJECTED }
      else -> requests
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("customer_requests_screen")
  ) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
      Text(
        text = "My Bookings & Orders",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = "Track your local service requests and past order history",
        fontSize = 12.sp,
        color = SlateMuted,
        modifier = Modifier.padding(bottom = 10.dp)
      )
    }

    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      edgePadding = 16.dp,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = SaffronPrimary,
      modifier = Modifier.fillMaxWidth()
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
              fontSize = 13.sp
            )
          }
        )
      }
    }

    if (filteredRequests.isEmpty()) {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateLight),
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(
            imageVector = Icons.Default.ReceiptLong,
            contentDescription = null,
            tint = SlateMuted,
            modifier = Modifier.size(48.dp)
          )
          Text(
            text = "No requests found",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp)
          )
          Text(
            text = "Your booking requests will appear here with live tracking updates.",
            fontSize = 12.sp,
            color = SlateMuted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
      ) {
        items(filteredRequests) { req ->
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, SlateBorder, RoundedCornerShape(14.dp))
              .testTag("request_item_card_${req.id}")
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = req.providerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = req.categoryName,
                    fontSize = 11.sp,
                    color = SlateMuted
                  )
                }
                StatusChip(status = req.status)
              }

              Spacer(modifier = Modifier.height(10.dp))

              // Service details
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${req.serviceName} (Qty: ${req.quantity})",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp,
                  color = IndigoNavy
                )
                Text(
                  text = "₹${req.totalAmount.toInt()}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = SaffronPrimary
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Delivery address & preferred slot
              Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Default.LocationOn, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(14.dp))
                  Text(text = "${req.customerAddress} (${req.cityName})", fontSize = 11.sp, color = SlateMuted)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Default.AccessTime, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(14.dp))
                  Text(text = "Slot: ${req.preferredDate} • ${req.preferredTime}", fontSize = 11.sp, color = SlateMuted)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Default.Call, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(14.dp))
                  Text(text = "Provider Contact: ${req.providerPhone}", fontSize = 11.sp, color = SlateMuted)
                }
              }

              if (!req.rejectionReason.isNullOrBlank()) {
                Text(
                  text = "Rejection Reason: ${req.rejectionReason}",
                  color = RoseDanger,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(top = 6.dp)
                )
              }

              if (!req.cancellationReason.isNullOrBlank()) {
                Text(
                  text = "Cancellation Reason: ${req.cancellationReason}",
                  color = SlateMuted,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(top = 6.dp)
                )
              }

              // Actions Row
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
              ) {
                if (req.status == RequestStatus.PENDING) {
                  OutlinedButton(
                    onClick = { requestToCancel = req },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RoseDanger),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("Cancel Request", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }

                if (req.status == RequestStatus.COMPLETED) {
                  Button(
                    onClick = { requestToReview = req },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                      Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                      Text("Rate & Review", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Cancel Dialog
  if (requestToCancel != null) {
    AlertDialog(
      onDismissRequest = { requestToCancel = null },
      title = { Text("Cancel Service Request", fontWeight = FontWeight.Bold) },
      text = {
        Column {
          Text("Are you sure you want to cancel this booking?", fontSize = 13.sp)
          OutlinedTextField(
            value = cancelReason,
            onValueChange = { cancelReason = it },
            placeholder = { Text("Reason for cancellation (optional)") },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 10.dp)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onCancelRequest(requestToCancel!!.id, cancelReason.ifBlank { "Cancelled by customer" })
            requestToCancel = null
            cancelReason = ""
          },
          colors = ButtonDefaults.buttonColors(containerColor = RoseDanger)
        ) {
          Text("Confirm Cancel")
        }
      },
      dismissButton = {
        TextButton(onClick = { requestToCancel = null }) {
          Text("Keep Request")
        }
      }
    )
  }

  // Review Sheet
  if (requestToReview != null) {
    AddReviewBottomSheet(
      providerName = requestToReview!!.providerName,
      onDismiss = { requestToReview = null },
      onSubmitReview = { rating, comment ->
        onSubmitReview(requestToReview!!.providerId, requestToReview!!.id, rating, comment)
        requestToReview = null
      }
    )
  }
}
