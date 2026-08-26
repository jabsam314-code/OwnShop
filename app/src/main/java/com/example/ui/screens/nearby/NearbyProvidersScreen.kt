package com.example.ui.screens.nearby

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.ui.components.ProviderCard
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@Composable
fun NearbyProvidersScreen(
  selectedCity: City?,
  providers: List<Provider>,
  onProviderClick: (Provider) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedArea by remember(selectedCity) {
    mutableStateOf(selectedCity?.areas?.firstOrNull() ?: "")
  }

  val filteredByArea = remember(providers, selectedArea) {
    if (selectedArea.isBlank()) providers
    else providers.filter { it.serviceAreas.any { area -> area.equals(selectedArea, ignoreCase = true) } }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
      .testTag("nearby_screen")
  ) {
    Text(
      text = "Nearby in ${selectedCity?.name ?: "Jaipur"}",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(top = 16.dp)
    )
    Text(
      text = "Select a neighborhood zone to discover sellers delivering to your doorstep",
      fontSize = 12.sp,
      color = SlateMuted,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    // Neighborhood Area Horizontal Carousel
    if (selectedCity != null && selectedCity.areas.isNotEmpty()) {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 14.dp)
      ) {
        items(selectedCity.areas) { area ->
          val isSelected = selectedArea == area
          Surface(
            onClick = { selectedArea = area },
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) SaffronPrimary else SlateLight,
            modifier = Modifier
              .border(
                1.dp,
                if (isSelected) SaffronPrimary else SlateBorder,
                RoundedCornerShape(20.dp)
              )
              .testTag("area_chip_$area")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (isSelected) Color.White else IndigoNavy,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = area,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }
    }

    // Active Neighborhood Banner
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = EmeraldLight),
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)
        .border(1.dp, EmeraldTrust.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Icon(
          imageVector = Icons.Default.NearMe,
          contentDescription = null,
          tint = EmeraldTrust,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = "Showing ${filteredByArea.size} active verified providers serving '$selectedArea'",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldTrust
        )
      }
    }

    if (filteredByArea.isEmpty()) {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateLight),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 24.dp)
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
            text = "No providers listed in this area yet",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 8.dp)
          )
          Text(
            text = "Try picking another nearby neighborhood above.",
            fontSize = 12.sp,
            color = SlateMuted
          )
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
      ) {
        items(filteredByArea) { provider ->
          ProviderCard(
            provider = provider,
            onClick = { onProviderClick(provider) }
          )
        }
      }
    }
  }
}
