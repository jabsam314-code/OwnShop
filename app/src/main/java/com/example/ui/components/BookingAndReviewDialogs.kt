package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.Category
import com.example.data.model.Provider
import com.example.data.model.ServiceItem
import com.example.data.model.UserAccount
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookServiceBottomSheet(
  provider: Provider,
  category: Category,
  service: ServiceItem,
  customer: UserAccount?,
  onDismiss: () -> Unit,
  onConfirmBooking: (
    name: String,
    phone: String,
    address: String,
    date: String,
    time: String,
    quantity: Int,
    notes: String
  ) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var name by remember { mutableStateOf(customer?.name ?: "") }
  var phone by remember { mutableStateOf(customer?.phone ?: "") }
  var address by remember { mutableStateOf(customer?.address ?: "") }
  var selectedDate by remember { mutableStateOf("Today") }
  var selectedTime by remember { mutableStateOf("04:00 PM - 06:00 PM") }
  var quantity by remember { mutableIntStateOf(1) }
  var notes by remember { mutableStateOf("") }
  var formError by remember { mutableStateOf<String?>(null) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .padding(bottom = 36.dp)
        .testTag("book_service_bottom_sheet")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Request Service / Order",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "${provider.businessName} • ${provider.cityName}",
            fontSize = 13.sp,
            color = SaffronPrimary,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Selected Service Summary Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = IndigoLight),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = service.title,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = IndigoNavy
            )
            Text(
              text = "Est. duration: ${service.estimatedTime}",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }

          // Quantity controls
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              onClick = { if (quantity > 1) quantity-- },
              shape = RoundedCornerShape(6.dp),
              color = Color.White
            ) {
              Text(
                text = "−",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = IndigoNavy,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
            Text(
              text = "$quantity",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = IndigoNavy
            )
            Surface(
              onClick = { quantity++ },
              shape = RoundedCornerShape(6.dp),
              color = Color.White
            ) {
              Text(
                text = "+",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = IndigoNavy,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Your Contact & Location",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Your Full Name") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SaffronPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .testTag("booking_name_input"),
        singleLine = true
      )

      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Contact Phone (e.g. +91 98290 00000)") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SaffronPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .testTag("booking_phone_input"),
        singleLine = true
      )

      OutlinedTextField(
        value = address,
        onValueChange = { address = it },
        label = { Text("Doorstep Address & Area") },
        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = SaffronPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .testTag("booking_address_input")
      )

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Preferred Date & Time",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      // Date Chips
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("Today", "Tomorrow", "Weekend").forEach { dateOption ->
          val isSelected = selectedDate == dateOption
          Surface(
            onClick = { selectedDate = dateOption },
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) SaffronPrimary else SlateLight,
            modifier = Modifier.border(
              1.dp,
              if (isSelected) SaffronPrimary else SlateBorder,
              RoundedCornerShape(8.dp)
            )
          ) {
            Text(
              text = dateOption,
              color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
            )
          }
        }
      }

      // Time Chips
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("Morning (9-12)", "Afternoon (1-4)", "Evening (4-8)").forEach { timeOption ->
          val isSelected = selectedTime == timeOption
          Surface(
            onClick = { selectedTime = timeOption },
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) IndigoNavy else SlateLight,
            modifier = Modifier.border(
              1.dp,
              if (isSelected) IndigoNavy else SlateBorder,
              RoundedCornerShape(8.dp)
            )
          ) {
            Text(
              text = timeOption,
              color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            )
          }
        }
      }

      OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        label = { Text("Special instructions or requirements (optional)") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
          .testTag("booking_notes_input"),
        maxLines = 3
      )

      if (formError != null) {
        Text(
          text = formError ?: "",
          color = MaterialTheme.colorScheme.error,
          fontSize = 12.sp,
          modifier = Modifier.padding(top = 8.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Total Calculation
      val total = service.price * quantity
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "Total Payable (Pay on Service / COD)", fontSize = 11.sp, color = SlateMuted)
          Text(
            text = "₹${total.toInt()}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            color = SaffronPrimary
          )
        }

        Button(
          onClick = {
            if (name.isBlank() || phone.isBlank() || address.isBlank()) {
              formError = "Please fill in your name, phone number, and address."
            } else {
              formError = null
              onConfirmBooking(name, phone, address, selectedDate, selectedTime, quantity, notes)
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("confirm_booking_button")
        ) {
          Text(
            text = "Send Request",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewBottomSheet(
  providerName: String,
  onDismiss: () -> Unit,
  onSubmitReview: (rating: Int, comment: String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var rating by remember { mutableIntStateOf(5) }
  var comment by remember { mutableStateOf("") }
  var error by remember { mutableStateOf<String?>(null) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 36.dp)
        .testTag("add_review_bottom_sheet")
    ) {
      Text(
        text = "Rate & Review Service",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = "How was your experience with $providerName?",
        fontSize = 13.sp,
        color = SlateMuted,
        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        StarRatingBar(
          rating = rating,
          onRatingChanged = { rating = it },
          isEditable = true
        )
      }

      OutlinedTextField(
        value = comment,
        onValueChange = { comment = it },
        label = { Text("Write your feedback (optional)") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp)
          .testTag("review_comment_input"),
        minLines = 3
      )

      if (error != null) {
        Text(
          text = error ?: "",
          color = MaterialTheme.colorScheme.error,
          fontSize = 12.sp,
          modifier = Modifier.padding(top = 8.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          onSubmitReview(rating, comment.ifBlank { "Great verified local service!" })
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("submit_review_button")
      ) {
        Text("Submit Verified Review", fontWeight = FontWeight.Bold)
      }
    }
  }
}
