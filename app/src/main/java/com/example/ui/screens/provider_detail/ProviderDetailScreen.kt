package com.example.ui.screens.provider_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.Provider
import com.example.data.model.Review
import com.example.data.model.ServiceItem
import com.example.data.model.UserAccount
import com.example.ui.components.AddReviewBottomSheet
import com.example.ui.components.BookServiceBottomSheet
import com.example.ui.components.LegalBadgePill
import com.example.ui.components.StarRatingBar
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProviderDetailScreen(
  provider: Provider,
  categories: List<Category>,
  services: List<ServiceItem>,
  reviews: List<Review>,
  currentUser: UserAccount?,
  onBack: () -> Unit,
  onBookService: (
    service: ServiceItem,
    category: Category,
    name: String,
    phone: String,
    address: String,
    date: String,
    time: String,
    quantity: Int,
    notes: String
  ) -> Unit,
  onSubmitReview: (rating: Int, comment: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedServiceForBooking by remember { mutableStateOf<ServiceItem?>(null) }
  var showReviewSheet by remember { mutableStateOf(false) }

  val defaultCategory = remember(categories, provider) {
    categories.find { it.id == provider.categoryIds.firstOrNull() } ?: Category(
      id = "cat_general",
      name = "General Service",
      iconName = "store",
      description = "Local Service"
    )
  }

  Scaffold(
    modifier = modifier.testTag("provider_detail_screen"),
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        title = {
          Text(
            text = provider.businessName,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            maxLines = 1
          )
        },
        navigationIcon = {
          IconButton(onClick = onBack, modifier = Modifier.testTag("provider_detail_back_btn")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(bottom = 90.dp)
    ) {
      // 1. PROVIDER HEADER CARD
      item {
        Card(
          shape = RoundedCornerShape(0.dp),
          colors = CardDefaults.cardColors(containerColor = IndigoLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(IndigoNavy),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                  )
                }
                Column {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                      text = provider.businessName,
                      fontWeight = FontWeight.Black,
                      fontSize = 18.sp,
                      color = IndigoNavy
                    )
                    Icon(
                      imageVector = Icons.Default.Verified,
                      contentDescription = "Verified",
                      tint = EmeraldTrust,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                  Text(
                    text = "Owner: ${provider.ownerName} • ${provider.experienceYears} Years Experience",
                    fontSize = 13.sp,
                    color = SlateMuted
                  )
                }
              }

              // Star Rating Box
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = SaffronLight
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(Icons.Default.Star, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(16.dp))
                  Text(
                    text = "${provider.rating}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = SaffronPrimary
                  )
                }
              }
            }

            Text(
              text = provider.description,
              fontSize = 13.sp,
              lineHeight = 18.sp,
              color = MaterialTheme.colorScheme.onSurface,
              modifier = Modifier.padding(top = 14.dp, bottom = 12.dp)
            )

            // Contact & Address Details
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = IndigoNavy, modifier = Modifier.size(16.dp))
                Text(text = provider.address, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
              }
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Call, contentDescription = null, tint = IndigoNavy, modifier = Modifier.size(16.dp))
                Text(text = provider.phone, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
              }
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Email, contentDescription = null, tint = IndigoNavy, modifier = Modifier.size(16.dp))
                Text(text = provider.email, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
              }
            }

            // Legal Badges
            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.padding(top = 14.dp)
            ) {
              if (provider.isFssaiVerified) {
                LegalBadgePill(
                  text = "FSSAI Lic: ${provider.fssaiNumber ?: "Verified"}",
                  icon = Icons.Default.Security,
                  color = EmeraldTrust,
                  bgColor = EmeraldLight
                )
              }
              if (provider.isDrugLicenceVerified) {
                LegalBadgePill(
                  text = "Drug Lic: ${provider.drugLicenceNumber ?: "Verified"}",
                  icon = Icons.Default.Security,
                  color = IndigoNavy,
                  bgColor = Color.White
                )
              }
              if (provider.isAvailable) {
                LegalBadgePill(
                  text = "Taking Orders Today",
                  icon = Icons.Default.CheckCircle,
                  color = EmeraldTrust,
                  bgColor = EmeraldLight
                )
              }
            }
          }
        }
      }

      // 2. SERVICE AREAS
      item {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
          Text(
            text = "Service & Delivery Neighborhoods in ${provider.cityName}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(top = 8.dp)
          ) {
            provider.serviceAreas.forEach { area ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = SlateLight,
                modifier = Modifier.border(1.dp, SlateBorder, RoundedCornerShape(8.dp))
              ) {
                Text(
                  text = "📍 $area",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }

      // 3. SERVICES & PRODUCTS CATALOG
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Services & Price Menu",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "${services.size} items",
            fontSize = 12.sp,
            color = SlateMuted
          )
        }
      }

      if (services.isEmpty()) {
        item {
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SlateLight),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          ) {
            Text(
              text = "Starting service available from ₹${provider.startingPrice.toInt()}. Click request below.",
              fontSize = 13.sp,
              modifier = Modifier.padding(16.dp)
            )
          }
        }
      } else {
        items(services) { service ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 6.dp)
              .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
              .testTag("service_item_card_${service.id}")
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
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = service.description,
                  fontSize = 12.sp,
                  color = SlateMuted,
                  lineHeight = 16.sp,
                  modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                )
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Icon(Icons.Default.AccessTime, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(12.dp))
                  Text(
                    text = service.estimatedTime,
                    fontSize = 11.sp,
                    color = SlateMuted
                  )
                }
              }

              Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = "₹${service.price.toInt()}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = SaffronPrimary
                )
                Text(
                  text = service.priceUnit,
                  fontSize = 10.sp,
                  color = SlateMuted
                )
                Button(
                  onClick = { selectedServiceForBooking = service },
                  colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                  modifier = Modifier.testTag("book_service_btn_${service.id}")
                ) {
                  Text("Request", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }

      // 4. VERIFIED CUSTOMER REVIEWS
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Customer Reviews",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${reviews.size} verified customer ratings",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }

          Button(
            onClick = { showReviewSheet = true },
            colors = ButtonDefaults.buttonColors(containerColor = IndigoLight),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.testTag("write_review_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              Icon(Icons.Default.RateReview, contentDescription = null, tint = IndigoNavy, modifier = Modifier.size(14.dp))
              Text("Write Review", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IndigoNavy)
            }
          }
        }
      }

      if (reviews.isEmpty()) {
        item {
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SlateLight),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          ) {
            Text(
              text = "No reviews yet. Be the first to book and rate ${provider.businessName}!",
              fontSize = 13.sp,
              color = SlateMuted,
              modifier = Modifier.padding(16.dp)
            )
          }
        }
      } else {
        items(reviews) { review ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 6.dp)
              .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = review.customerName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                StarRatingBar(rating = review.rating)
              }
              Text(
                text = review.comment,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 6.dp)
              )
            }
          }
        }
      }
    }
  }

  // Booking Modal
  if (selectedServiceForBooking != null) {
    val srv = selectedServiceForBooking!!
    BookServiceBottomSheet(
      provider = provider,
      category = defaultCategory,
      service = srv,
      customer = currentUser,
      onDismiss = { selectedServiceForBooking = null },
      onConfirmBooking = { name, phone, address, date, time, quantity, notes ->
        onBookService(srv, defaultCategory, name, phone, address, date, time, quantity, notes)
        selectedServiceForBooking = null
      }
    )
  }

  // Review Modal
  if (showReviewSheet) {
    AddReviewBottomSheet(
      providerName = provider.businessName,
      onDismiss = { showReviewSheet = false },
      onSubmitReview = { rating, comment ->
        onSubmitReview(rating, comment)
        showReviewSheet = false
      }
    )
  }
}
