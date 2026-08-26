package com.example.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.ui.components.OwnShopBrandEmblem
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.OwnEmeraldAccent
import com.example.ui.theme.OwnEmeraldLight
import com.example.ui.theme.OwnEmeraldPrimary
import com.example.ui.theme.OwnNavyDark
import com.example.ui.theme.OwnNavyLight
import com.example.ui.theme.OwnNavyPrimary
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.RoseLight
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@Composable
fun ProfileScreen(
  currentUser: UserAccount?,
  onSwitchRoleClick: () -> Unit,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .testTag("profile_screen")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
    ) {
      OwnShopBrandEmblem(size = 40)
      Column {
        Text(
          text = "My Profile & Account",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "OwnShop Verified Account",
          fontSize = 12.sp,
          color = SlateMuted
        )
      }
    }

    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .background(OwnNavyPrimary, RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
            Column {
              Text(
                text = currentUser?.name ?: "Guest User",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
              )
              Text(
                text = currentUser?.email ?: "guest@ownshop.com",
                fontSize = 12.sp,
                color = SlateMuted
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = when (currentUser?.role) {
              UserRole.ADMIN -> RoseLight
              UserRole.PROVIDER -> OwnEmeraldLight
              else -> SaffronLight
            }
          ) {
            Text(
              text = currentUser?.role?.name ?: "CUSTOMER",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = when (currentUser?.role) {
                UserRole.ADMIN -> RoseDanger
                UserRole.PROVIDER -> OwnEmeraldPrimary
                else -> SaffronAccent
              },
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "Phone: ${currentUser?.phone ?: "Not set"}", fontSize = 13.sp)
        Text(text = "City: ${currentUser?.cityName ?: "Jaipur"}", fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
        Text(text = "Address: ${currentUser?.address ?: "Jaipur, Rajasthan"}", fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = onSwitchRoleClick,
          colors = ButtonDefaults.buttonColors(containerColor = OwnNavyPrimary),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Switch Role (Customer / Seller / Admin)", fontWeight = FontWeight.Bold)
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Multi-Role Architecture Card
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = SlateLight),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Text(
          text = "About OwnShop Multi-Role Architecture",
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = OwnNavyPrimary
        )
        Text(
          text = "OwnShop is built with complete role-based isolation. You can freely switch between Customer, Service Provider / Seller Portal, and Admin Console anytime using the role button on top.",
          fontSize = 12.sp,
          color = SlateMuted,
          lineHeight = 16.sp,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterProviderScreen(
  cities: List<City>,
  categories: List<Category>,
  onRegister: (
    businessName: String,
    ownerName: String,
    email: String,
    phone: String,
    cityId: String,
    cityName: String,
    serviceAreas: List<String>,
    categoryIds: List<String>,
    desc: String,
    startingPrice: Double,
    experience: Int,
    address: String,
    fssai: String?,
    drugLicence: String?
  ) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var businessName by remember { mutableStateOf("") }
  var ownerName by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var selectedCity by remember { mutableStateOf(cities.firstOrNull() ?: City("jaipur", "Jaipur", "Rajasthan", true, listOf("Vaishali Nagar", "Mansarovar"))) }
  var selectedAreas by remember { mutableStateOf(listOf<String>()) }
  var selectedCategoryIds by remember { mutableStateOf(listOf<String>()) }
  var desc by remember { mutableStateOf("") }
  var startingPriceStr by remember { mutableStateOf("199") }
  var experienceStr by remember { mutableStateOf("5") }
  var address by remember { mutableStateOf("") }
  var fssaiNumber by remember { mutableStateOf("") }
  var drugLicenceNumber by remember { mutableStateOf("") }
  var error by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("register_provider_screen")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(top = 8.dp)
    ) {
      OwnShopBrandEmblem(size = 36)
      Text(
        text = "Register as a Local Seller / Pro",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
    Text(
      text = "List your business in Jaipur or Jamnagar. Direct customer orders without aggregator commission.",
      fontSize = 12.sp,
      color = SlateMuted,
      modifier = Modifier.padding(bottom = 12.dp, top = 4.dp)
    )

    OutlinedTextField(
      value = businessName,
      onValueChange = { businessName = it },
      label = { Text("Business / Store Name") },
      leadingIcon = { Icon(Icons.Default.Store, contentDescription = null, tint = OwnNavyPrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("reg_business_name_input"),
      singleLine = true
    )

    OutlinedTextField(
      value = ownerName,
      onValueChange = { ownerName = it },
      label = { Text("Owner / Technician Full Name") },
      leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = OwnNavyPrimary) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
      singleLine = true
    )

    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Business Email") },
        modifier = Modifier.weight(1f),
        singleLine = true
      )
      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Phone (+91)") },
        modifier = Modifier.weight(1f),
        singleLine = true
      )
    }

    // City Selection
    Text(
      text = "Operating Launch City",
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp,
      modifier = Modifier.padding(top = 12.dp)
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 6.dp)) {
      cities.forEach { city ->
        val isSelected = selectedCity.id == city.id
        FilterChip(
          selected = isSelected,
          onClick = {
            selectedCity = city
            selectedAreas = emptyList()
          },
          label = { Text("${city.name}, ${city.state}") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OwnNavyPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    // Neighborhood Areas in selected city
    Text(
      text = "Select Service Neighborhoods in ${selectedCity.name}",
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp,
      modifier = Modifier.padding(top = 10.dp)
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 6.dp)) {
      selectedCity.areas.forEach { area ->
        val isSelected = selectedAreas.contains(area)
        FilterChip(
          selected = isSelected,
          onClick = {
            selectedAreas = if (isSelected) selectedAreas - area else selectedAreas + area
          },
          label = { Text(area, fontSize = 11.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OwnEmeraldPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    // Categories
    Text(
      text = "Primary Service Categories",
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp,
      modifier = Modifier.padding(top = 10.dp)
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 6.dp)) {
      categories.forEach { cat ->
        val isSelected = selectedCategoryIds.contains(cat.id)
        FilterChip(
          selected = isSelected,
          onClick = {
            selectedCategoryIds = if (isSelected) selectedCategoryIds - cat.id else selectedCategoryIds + cat.id
          },
          label = { Text(cat.name, fontSize = 11.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OwnEmeraldPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    OutlinedTextField(
      value = desc,
      onValueChange = { desc = it },
      label = { Text("Business Description & Specialties") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp),
      maxLines = 3
    )

    OutlinedTextField(
      value = address,
      onValueChange = { address = it },
      label = { Text("Physical Store / Office Address") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
    )

    // Legal Compliance Inputs
    Text(
      text = "Legal Compliance & Licenses (Optional for general pros, mandatory for Food / Med)",
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp,
      modifier = Modifier.padding(top = 12.dp)
    )

    OutlinedTextField(
      value = fssaiNumber,
      onValueChange = { fssaiNumber = it },
      label = { Text("FSSAI License # (Food & Sweets)") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = drugLicenceNumber,
      onValueChange = { drugLicenceNumber = it },
      label = { Text("Drug License # (Pharmacy)") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp),
      singleLine = true
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
        val price = startingPriceStr.toDoubleOrNull() ?: 199.0
        val exp = experienceStr.toIntOrNull() ?: 3
        if (businessName.isBlank() || ownerName.isBlank() || phone.isBlank()) {
          error = "Please fill in business name, owner name, and phone number."
        } else {
          error = null
          onRegister(
            businessName,
            ownerName,
            email.ifBlank { "${businessName.lowercase().replace(" ", "")}@ownshop.com" },
            phone,
            selectedCity.id,
            selectedCity.name,
            if (selectedAreas.isEmpty()) selectedCity.areas.take(2) else selectedAreas,
            if (selectedCategoryIds.isEmpty()) listOf("cat_home_services") else selectedCategoryIds,
            desc.ifBlank { "Verified local seller in ${selectedCity.name}" },
            price,
            exp,
            address.ifBlank { "${selectedCity.name}, ${selectedCity.state}" },
            fssaiNumber.takeIf { it.isNotBlank() },
            drugLicenceNumber.takeIf { it.isNotBlank() }
          )
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = OwnEmeraldPrimary),
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("submit_provider_registration_btn")
    ) {
      Text("Submit Seller Application", fontWeight = FontWeight.Bold)
    }

    TextButton(
      onClick = onBack,
      modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
      Text("Cancel", color = SlateMuted)
    }
  }
}

