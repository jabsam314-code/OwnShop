package com.example.ui.screens.categories

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.model.City
import com.example.ui.components.CategoryIconHelper
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@Composable
fun CategoriesScreen(
  selectedCity: City?,
  categories: List<Category>,
  onCategorySelected: (Category) -> Unit,
  modifier: Modifier = Modifier
) {
  var categorySearchQuery by remember { mutableStateOf("") }

  val filteredCategories = remember(categories, categorySearchQuery) {
    if (categorySearchQuery.isBlank()) categories
    else categories.filter {
      it.name.contains(categorySearchQuery, ignoreCase = true) ||
          it.description.contains(categorySearchQuery, ignoreCase = true)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
      .testTag("categories_screen")
  ) {
    Text(
      text = "All Marketplace Categories",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(top = 16.dp)
    )
    Text(
      text = "Browse ${categories.size} verified service & product departments in ${selectedCity?.name ?: "your city"}",
      fontSize = 12.sp,
      color = SlateMuted,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    OutlinedTextField(
      value = categorySearchQuery,
      onValueChange = { categorySearchQuery = it },
      placeholder = { Text("Filter categories...", fontSize = 13.sp) },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SaffronPrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)
        .testTag("category_search_field"),
      singleLine = true,
      shape = RoundedCornerShape(12.dp)
    )

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(filteredCategories.size) { index ->
        val category = filteredCategories[index]
        Card(
          onClick = { onCategorySelected(category) },
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(14.dp))
            .testTag("category_list_item_${category.id}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(IndigoLight),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = CategoryIconHelper.getIcon(category.iconName),
                contentDescription = category.name,
                tint = IndigoNavy,
                modifier = Modifier.size(24.dp)
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = category.description,
                fontSize = 12.sp,
                color = SlateMuted,
                lineHeight = 16.sp
              )
            }

            Icon(
              imageVector = Icons.Default.ArrowForwardIos,
              contentDescription = "Open",
              tint = SlateMuted,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }
    }
  }
}
