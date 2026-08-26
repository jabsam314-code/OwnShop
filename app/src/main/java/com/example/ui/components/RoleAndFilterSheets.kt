package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.SearchFilter
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.RoseLight
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSwitcherBottomSheet(
  currentUser: UserAccount?,
  onSelectRole: (UserRole) -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState()

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
        .testTag("role_switcher_sheet")
    ) {
      Text(
        text = "Switch User View & Role",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = "Experience OwnShop from the perspective of different platform users.",
        fontSize = 13.sp,
        color = SlateMuted,
        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
      )

      RoleOptionCard(
        title = "Customer View",
        subtitle = "Search, browse verified providers, book services & write reviews",
        role = UserRole.CUSTOMER,
        isSelected = currentUser?.role == UserRole.CUSTOMER,
        icon = Icons.Default.Person,
        color = EmeraldTrust,
        bgColor = EmeraldLight,
        onClick = {
          onSelectRole(UserRole.CUSTOMER)
          onDismiss()
        }
      )

      RoleOptionCard(
        title = "Seller / Service Provider Portal",
        subtitle = "Manage requests, accept orders, configure services & submit FSSAI/Drug licence",
        role = UserRole.PROVIDER,
        isSelected = currentUser?.role == UserRole.PROVIDER,
        icon = Icons.Default.Storefront,
        color = AmberWarning,
        bgColor = AmberLight,
        onClick = {
          onSelectRole(UserRole.PROVIDER)
          onDismiss()
        }
      )

      RoleOptionCard(
        title = "Admin Dashboard",
        subtitle = "Platform stats, approve/reject sellers, review compliance documents & manage cities",
        role = UserRole.ADMIN,
        isSelected = currentUser?.role == UserRole.ADMIN,
        icon = Icons.Default.AdminPanelSettings,
        color = RoseDanger,
        bgColor = RoseLight,
        onClick = {
          onSelectRole(UserRole.ADMIN)
          onDismiss()
        }
      )
    }
  }
}

@Composable
private fun RoleOptionCard(
  title: String,
  subtitle: String,
  role: UserRole,
  isSelected: Boolean,
  icon: ImageVector,
  color: Color,
  bgColor: Color,
  onClick: () -> Unit
) {
  Card(
    onClick = onClick,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) bgColor else SlateLight
    ),
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
      .border(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) color else SlateBorder,
        shape = RoundedCornerShape(12.dp)
      )
      .testTag("role_option_${role.name.lowercase()}")
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
          .size(42.dp)
          .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(24.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = if (isSelected) color else MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = subtitle,
          fontSize = 12.sp,
          color = SlateMuted
        )
      }

      if (isSelected) {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = "Active Role",
          tint = color,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
  currentFilter: SearchFilter,
  selectedCity: City?,
  categories: List<Category>,
  onApplyFilter: (SearchFilter) -> Unit,
  onResetFilter: () -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var selectedArea by remember { mutableStateOf(currentFilter.area) }
  var selectedCategory by remember { mutableStateOf(currentFilter.categoryId) }
  var minRating by remember { mutableDoubleStateOf(currentFilter.minRating) }
  var onlyAvailable by remember { mutableStateOf(currentFilter.onlyAvailable) }

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
        .testTag("filter_bottom_sheet")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.FilterAlt, contentDescription = null, tint = SaffronPrimary)
          Text(
            text = "Filter Providers",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
        TextButton(
          onClick = {
            selectedArea = ""
            selectedCategory = ""
            minRating = 0.0
            onlyAvailable = false
            onResetFilter()
            onDismiss()
          }
        ) {
          Text("Reset All", color = SaffronPrimary, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Local Area Filter
      if (selectedCity != null && selectedCity.areas.isNotEmpty()) {
        Text(
          text = "Local Area in ${selectedCity.name}",
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.padding(top = 8.dp, bottom = 14.dp)
        ) {
          selectedCity.areas.forEach { area ->
            val isSelected = selectedArea == area
            FilterChip(
              selected = isSelected,
              onClick = { selectedArea = if (isSelected) "" else area },
              label = { Text(area, fontSize = 12.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SaffronPrimary,
                selectedLabelColor = Color.White
              )
            )
          }
        }
      }

      // Categories
      Text(
        text = "Category",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp, bottom = 14.dp)
      ) {
        categories.forEach { cat ->
          val isSelected = selectedCategory == cat.id
          FilterChip(
            selected = isSelected,
            onClick = { selectedCategory = if (isSelected) "" else cat.id },
            label = { Text(cat.name, fontSize = 12.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = IndigoNavy,
              selectedLabelColor = Color.White
            )
          )
        }
      }

      // Minimum Rating
      Text(
        text = "Minimum Rating",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf(0.0 to "All", 4.0 to "4.0+ ★", 4.5 to "4.5+ ★", 4.8 to "4.8+ ★").forEach { (rating, label) ->
          val isSelected = minRating == rating
          Surface(
            onClick = { minRating = rating },
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) SaffronLight else SlateLight,
            modifier = Modifier.border(
              1.dp,
              if (isSelected) SaffronPrimary else SlateBorder,
              RoundedCornerShape(8.dp)
            )
          ) {
            Text(
              text = label,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.onSurface,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }
      }

      // Available Today Toggle
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Show Available Today Only",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Filters out providers currently off-duty or closed",
            fontSize = 12.sp,
            color = SlateMuted
          )
        }
        Switch(
          checked = onlyAvailable,
          onCheckedChange = { onlyAvailable = it },
          colors = SwitchDefaults.colors(checkedThumbColor = EmeraldTrust, checkedTrackColor = EmeraldLight)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          onApplyFilter(
            currentFilter.copy(
              area = selectedArea,
              categoryId = selectedCategory,
              minRating = minRating,
              onlyAvailable = onlyAvailable
            )
          )
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("apply_filter_button")
      ) {
        Text("Apply Filters", fontWeight = FontWeight.Bold)
      }
    }
  }
}
