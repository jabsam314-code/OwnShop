package com.example.ui.screens.search

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.SearchFilter
import com.example.ui.components.ProviderCard
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchExploreScreen(
  selectedCity: City?,
  categories: List<Category>,
  searchFilter: SearchFilter,
  providers: List<Provider>,
  onSearchQueryChange: (String) -> Unit,
  onCategoryFilterChange: (String) -> Unit,
  onAreaFilterChange: (String) -> Unit,
  onFilterClick: () -> Unit,
  onClearFilters: () -> Unit,
  onProviderClick: (Provider) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
      .testTag("search_explore_screen")
  ) {
    Text(
      text = "Search & Explore",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(top = 16.dp)
    )
    Text(
      text = "Find top verified sellers & service providers in ${selectedCity?.name ?: "Jaipur"}",
      fontSize = 12.sp,
      color = SlateMuted,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    // Search Input Field
    OutlinedTextField(
      value = searchFilter.searchQuery,
      onValueChange = onSearchQueryChange,
      placeholder = { Text("Search services, pros, items...", fontSize = 13.sp) },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SaffronPrimary) },
      trailingIcon = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (searchFilter.searchQuery.isNotBlank()) {
            IconButton(onClick = { onSearchQueryChange("") }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear", tint = SlateMuted)
            }
          }
          IconButton(
            onClick = onFilterClick,
            modifier = Modifier.testTag("search_filter_trigger_button")
          ) {
            Icon(Icons.Default.FilterAlt, contentDescription = "Advanced Filter", tint = SaffronPrimary)
          }
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("explore_search_field"),
      singleLine = true,
      shape = RoundedCornerShape(12.dp)
    )

    // Active Quick Category Chips
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.padding(vertical = 10.dp)
    ) {
      categories.take(6).forEach { cat ->
        val isSelected = searchFilter.categoryId == cat.id
        FilterChip(
          selected = isSelected,
          onClick = { onCategoryFilterChange(cat.id) },
          label = { Text(cat.name, fontSize = 11.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = IndigoNavy,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    // Results Header with Count
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${providers.size} verified providers found",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      if (searchFilter.searchQuery.isNotBlank() || searchFilter.categoryId.isNotBlank() || searchFilter.area.isNotBlank()) {
        TextButton(onClick = onClearFilters) {
          Text("Reset", color = SaffronPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Results List
    if (providers.isEmpty()) {
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
            modifier = Modifier.size(48.dp)
          )
          Text(
            text = "No matching providers found",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp)
          )
          Text(
            text = "Try broadening your search term or clearing the active category filters.",
            fontSize = 12.sp,
            color = SlateMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 14.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )
          Button(
            onClick = onClearFilters,
            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text("Reset Search Filters", fontSize = 12.sp)
          }
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
      ) {
        items(providers) { provider ->
          ProviderCard(
            provider = provider,
            onClick = { onProviderClick(provider) }
          )
        }
      }
    }
  }
}
