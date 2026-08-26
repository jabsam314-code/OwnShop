package com.example.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.ui.components.CategoryIconHelper
import com.example.ui.components.LegalBadgePill
import com.example.ui.components.OwnShopFullBrandHeader
import com.example.ui.components.ProviderCard
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndiaGreen
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.OwnEmeraldAccent
import com.example.ui.theme.OwnEmeraldDark
import com.example.ui.theme.OwnEmeraldLight
import com.example.ui.theme.OwnEmeraldPrimary
import com.example.ui.theme.OwnNavyDark
import com.example.ui.theme.OwnNavyLight
import com.example.ui.theme.OwnNavyPrimary
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  selectedCity: City?,
  categories: List<Category>,
  providers: List<Provider>,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  onSearchSubmit: () -> Unit,
  onCategoryClick: (Category) -> Unit,
  onProviderClick: (Provider) -> Unit,
  onViewAllCategories: () -> Unit,
  onViewAllProviders: () -> Unit,
  onFilterClick: () -> Unit,
  onCityClick: () -> Unit,
  onBecomeProviderClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("home_screen_column"),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // 1. BRAND HERO BANNER WITH OWNSHOP EMBLEM & TRUST PILLARS
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(OwnNavyPrimary, OwnNavyDark, Color(0xFF07182C))
            )
          )
          .padding(horizontal = 16.dp, vertical = 20.dp)
      ) {
        Column {
          // Full Logo Header Card Embedded in Hero
          OwnShopFullBrandHeader(
            modifier = Modifier.padding(bottom = 16.dp)
          )

          Text(
            text = "Everything for your local needs in ${selectedCity?.name ?: "Jaipur"}",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 4.dp)
          )

          Text(
            text = "Discover verified shops, repair services, medicines & sweets with doorstep delivery.",
            color = Color(0xFFE2E8F0),
            fontSize = 12.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(bottom = 14.dp)
          )

          // "What do you need?" Live Search Bar
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = OwnNavyPrimary,
                modifier = Modifier
                  .size(24.dp)
                  .padding(start = 4.dp)
              )
              OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                  Text(
                    text = "Search AC repair, Ghewar, Electrician...",
                    fontSize = 13.sp,
                    color = SlateMuted
                  )
                },
                modifier = Modifier
                  .weight(1f)
                  .testTag("home_search_input"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = Color.Transparent,
                  unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true
              )
              IconButton(
                onClick = onFilterClick,
                modifier = Modifier.testTag("home_filter_button")
              ) {
                Icon(
                  imageVector = Icons.Default.FilterList,
                  contentDescription = "Filters",
                  tint = OwnEmeraldPrimary
                )
              }
            }
          }
        }
      }
    }

    // 2. POPULAR CATEGORIES SECTION
    item {
      Column(modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Popular Categories",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Explore top rated services in ${selectedCity?.name ?: "Jaipur"}",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }
          TextButton(onClick = onViewAllCategories) {
            Text(
              text = "See All (17)",
              color = OwnEmeraldPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Grid of first 8 popular categories
        val displayCats = categories.take(8)
        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          maxItemsInEachRow = 4
        ) {
          displayCats.forEach { category ->
            CategoryCard(
              category = category,
              onClick = { onCategoryClick(category) }
            )
          }
        }
      }
    }

    // 3. BECOME A PROVIDER BANNER CTA
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OwnEmeraldLight),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 20.dp)
          .border(1.dp, OwnEmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
          .testTag("become_provider_banner")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Grow Your Local Business",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = OwnEmeraldDark
            )
            Text(
              text = "Join OwnShop as a verified seller or technician in ${selectedCity?.name ?: "your city"}. 0% listing fee.",
              fontSize = 12.sp,
              color = OwnEmeraldAccent,
              lineHeight = 16.sp,
              modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )
            Button(
              onClick = onBecomeProviderClick,
              colors = ButtonDefaults.buttonColors(containerColor = OwnEmeraldPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("register_provider_cta_btn")
            ) {
              Text("Register as Seller", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }

          Box(
            modifier = Modifier
              .size(56.dp)
              .background(OwnEmeraldPrimary.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Store,
              contentDescription = null,
              tint = OwnEmeraldPrimary,
              modifier = Modifier.size(32.dp)
            )
          }
        }
      }
    }

    // 4. TOP-RATED & VERIFIED PROVIDERS
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(
                text = "Verified Local Providers",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = OwnEmeraldPrimary,
                modifier = Modifier.size(18.dp)
              )
            }
            Text(
              text = "Admin approved & legally compliant businesses",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }
          TextButton(onClick = onViewAllProviders) {
            Text(
              text = "View All",
              color = OwnNavyPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
      }
    }

    if (providers.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = SlateLight),
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Store,
              contentDescription = null,
              tint = SlateMuted,
              modifier = Modifier.size(40.dp)
            )
            Text(
              text = "No providers matching filter",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              modifier = Modifier.padding(top = 8.dp)
            )
            Text(
              text = "Try clearing filters or changing selected area.",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }
        }
      }
    } else {
      items(providers) { provider ->
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          ProviderCard(
            provider = provider,
            onClick = { onProviderClick(provider) }
          )
        }
      }
    }
  }
}

@Composable
fun CategoryCard(
  category: Category,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onClick,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .width(78.dp)
      .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
      .testTag("cat_card_${category.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 4.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(OwnNavyLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = CategoryIconHelper.getIcon(category.iconName),
          contentDescription = category.name,
          tint = OwnNavyPrimary,
          modifier = Modifier.size(22.dp)
        )
      }
      Text(
        text = category.name,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 6.dp)
      )
    }
  }
}

