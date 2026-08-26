package com.example.ui.screens.admin_portal

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
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
import com.example.data.model.ApprovalStatus
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.PlatformStats
import com.example.data.model.Provider
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.ui.components.ApprovalStatusBadge
import com.example.ui.components.CategoryIconHelper
import com.example.ui.components.LegalBadgePill
import com.example.ui.components.StatusChip
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.RoseLight
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdminPortalScreen(
  stats: PlatformStats,
  providers: List<Provider>,
  requests: List<ServiceRequest>,
  cities: List<City>,
  categories: List<Category>,
  users: List<UserAccount>,
  onApproveProvider: (String) -> Unit,
  onRejectProvider: (String) -> Unit,
  onSuspendProvider: (String) -> Unit,
  onVerifyFssai: (String, Boolean) -> Unit,
  onVerifyDrugLicence: (String, Boolean) -> Unit,
  onAddCity: (name: String, state: String, areas: List<String>) -> Unit,
  onAddAreaToCity: (City, String) -> Unit,
  onToggleCityActive: (City) -> Unit,
  onAddCategory: (name: String, desc: String, icon: String) -> Unit,
  onToggleCategoryActive: (Category) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Overview", "Seller Approvals", "Multi-City Setup", "Categories", "All Orders")

  var providerFilterStatus by remember { mutableStateOf<ApprovalStatus?>(null) }
  var showAddCityDialog by remember { mutableStateOf(false) }
  var showAddCategoryDialog by remember { mutableStateOf(false) }
  var cityForAreaAdd by remember { mutableStateOf<City?>(null) }
  var newAreaName by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("admin_portal_screen")
  ) {
    // Header
    Card(
      shape = RoundedCornerShape(0.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .background(RoseDanger, RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Column {
              Text(text = "OwnShop Admin Console", fontWeight = FontWeight.Black, fontSize = 17.sp, color = IndigoNavy)
              Text(text = "Platform Control, Verification & City Scaling", fontSize = 11.sp, color = SlateMuted)
            }
          }
        }
      }
    }

    // Tab Navigation
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      edgePadding = 16.dp,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = RoseDanger,
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

    when (selectedTab) {
      0 -> AdminOverviewTab(stats = stats, users = users, cities = cities)
      1 -> AdminProviderApprovalsTab(
        providers = providers,
        filterStatus = providerFilterStatus,
        onFilterChange = { providerFilterStatus = it },
        onApprove = onApproveProvider,
        onReject = onRejectProvider,
        onSuspend = onSuspendProvider,
        onVerifyFssai = onVerifyFssai,
        onVerifyDrugLicence = onVerifyDrugLicence
      )
      2 -> AdminMultiCityTab(
        cities = cities,
        onAddCityClick = { showAddCityDialog = true },
        onToggleCity = onToggleCityActive,
        onAddAreaClick = { cityForAreaAdd = it }
      )
      3 -> AdminCategoriesTab(
        categories = categories,
        onAddCategoryClick = { showAddCategoryDialog = true },
        onToggleCategory = onToggleCategoryActive
      )
      4 -> AdminAllOrdersTab(requests = requests)
    }
  }

  // Add City Dialog
  if (showAddCityDialog) {
    AddCityDialog(
      onDismiss = { showAddCityDialog = false },
      onConfirm = { name, state, areas ->
        onAddCity(name, state, areas)
        showAddCityDialog = false
      }
    )
  }

  // Add Category Dialog
  if (showAddCategoryDialog) {
    AddCategoryDialog(
      onDismiss = { showAddCategoryDialog = false },
      onConfirm = { name, desc, icon ->
        onAddCategory(name, desc, icon)
        showAddCategoryDialog = false
      }
    )
  }

  // Add Area to City Dialog
  if (cityForAreaAdd != null) {
    AlertDialog(
      onDismissRequest = { cityForAreaAdd = null },
      title = { Text("Add Neighborhood Area to ${cityForAreaAdd!!.name}", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
      text = {
        OutlinedTextField(
          value = newAreaName,
          onValueChange = { newAreaName = it },
          label = { Text("Area Name (e.g. Vaishali Nagar, Digjam Circle)") },
          modifier = Modifier.fillMaxWidth()
        )
      },
      confirmButton = {
        Button(
          onClick = {
            if (newAreaName.isNotBlank()) {
              onAddAreaToCity(cityForAreaAdd!!, newAreaName)
              cityForAreaAdd = null
              newAreaName = ""
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
        ) {
          Text("Add Area")
        }
      },
      dismissButton = {
        TextButton(onClick = { cityForAreaAdd = null }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
private fun AdminOverviewTab(
  stats: PlatformStats,
  users: List<UserAccount>,
  cities: List<City>
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Text(
      text = "Marketplace Health Metrics",
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onSurface
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      AdminMetricCard(
        title = "Pending Approvals",
        value = "${stats.pendingProviders}",
        subtitle = "Action required",
        color = AmberWarning,
        bgColor = AmberLight,
        modifier = Modifier.weight(1f)
      )
      AdminMetricCard(
        title = "Active Providers",
        value = "${stats.approvedProviders}",
        subtitle = "Approved sellers",
        color = EmeraldTrust,
        bgColor = EmeraldLight,
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      AdminMetricCard(
        title = "Total Orders",
        value = "${stats.totalRequests}",
        subtitle = "${stats.completedRequests} completed",
        color = IndigoNavy,
        bgColor = IndigoLight,
        modifier = Modifier.weight(1f)
      )
      AdminMetricCard(
        title = "Launch Cities",
        value = "${stats.activeCities}",
        subtitle = "${cities.sumOf { it.areas.size }} local areas",
        color = SaffronPrimary,
        bgColor = SaffronLight,
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Registered Users Summary
    Text(
      text = "Platform Users (${users.size})",
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onSurface
    )

    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        users.forEach { user ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
              Text(text = "${user.email} • ${user.cityName}", fontSize = 12.sp, color = SlateMuted)
            }
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = when (user.role.name) {
                "ADMIN" -> RoseLight
                "PROVIDER" -> AmberLight
                else -> EmeraldLight
              }
            ) {
              Text(
                text = user.role.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = when (user.role.name) {
                  "ADMIN" -> RoseDanger
                  "PROVIDER" -> AmberWarning
                  else -> EmeraldTrust
                },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AdminMetricCard(
  title: String,
  value: String,
  subtitle: String,
  color: Color,
  bgColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = bgColor),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(text = value, fontWeight = FontWeight.Black, fontSize = 24.sp, color = color)
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = color)
      Text(text = subtitle, fontSize = 11.sp, color = color.copy(alpha = 0.8f))
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdminProviderApprovalsTab(
  providers: List<Provider>,
  filterStatus: ApprovalStatus?,
  onFilterChange: (ApprovalStatus?) -> Unit,
  onApprove: (String) -> Unit,
  onReject: (String) -> Unit,
  onSuspend: (String) -> Unit,
  onVerifyFssai: (String, Boolean) -> Unit,
  onVerifyDrugLicence: (String, Boolean) -> Unit
) {
  val filteredList = remember(providers, filterStatus) {
    if (filterStatus == null) providers else providers.filter { it.approvalStatus == filterStatus }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    // Filter Chips Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      FilterChip(
        selected = filterStatus == null,
        onClick = { onFilterChange(null) },
        label = { Text("All (${providers.size})", fontSize = 11.sp) }
      )
      FilterChip(
        selected = filterStatus == ApprovalStatus.PENDING,
        onClick = { onFilterChange(ApprovalStatus.PENDING) },
        label = { Text("Pending (${providers.count { it.approvalStatus == ApprovalStatus.PENDING }})", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AmberWarning, selectedLabelColor = Color.White)
      )
      FilterChip(
        selected = filterStatus == ApprovalStatus.APPROVED,
        onClick = { onFilterChange(ApprovalStatus.APPROVED) },
        label = { Text("Approved (${providers.count { it.approvalStatus == ApprovalStatus.APPROVED }})", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldTrust, selectedLabelColor = Color.White)
      )
    }

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(filteredList) { prov ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
            .testTag("admin_prov_item_${prov.id}")
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(text = prov.businessName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "Owner: ${prov.ownerName} • ${prov.cityName}", fontSize = 12.sp, color = SlateMuted)
                Text(text = "Contact: ${prov.phone} • ${prov.email}", fontSize = 11.sp, color = SlateMuted)
              }
              ApprovalStatusBadge(status = prov.approvalStatus)
            }

            Text(
              text = prov.description,
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            // FSSAI Review Section
            if (!prov.fssaiNumber.isNullOrBlank()) {
              Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = if (prov.isFssaiVerified) EmeraldLight else AmberLight),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(text = "FSSAI Food License", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = "Cert #: ${prov.fssaiNumber}", fontSize = 11.sp, color = SlateMuted)
                  }
                  Button(
                    onClick = { onVerifyFssai(prov.id, !prov.isFssaiVerified) },
                    colors = ButtonDefaults.buttonColors(containerColor = if (prov.isFssaiVerified) EmeraldTrust else AmberWarning),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("verify_fssai_btn_${prov.id}")
                  ) {
                    Text(if (prov.isFssaiVerified) "Verified ✓" else "Approve FSSAI", fontSize = 10.sp)
                  }
                }
              }
            }

            // Drug License Review Section
            if (!prov.drugLicenceNumber.isNullOrBlank()) {
              Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = if (prov.isDrugLicenceVerified) IndigoLight else AmberLight),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(text = "State Drug / Pharmacy License", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = "Cert #: ${prov.drugLicenceNumber}", fontSize = 11.sp, color = SlateMuted)
                  }
                  Button(
                    onClick = { onVerifyDrugLicence(prov.id, !prov.isDrugLicenceVerified) },
                    colors = ButtonDefaults.buttonColors(containerColor = if (prov.isDrugLicenceVerified) IndigoNavy else AmberWarning),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("verify_drug_btn_${prov.id}")
                  ) {
                    Text(if (prov.isDrugLicenceVerified) "Verified ✓" else "Approve License", fontSize = 10.sp)
                  }
                }
              }
            }

            // Action Buttons
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
              horizontalArrangement = Arrangement.End,
              verticalAlignment = Alignment.CenterVertically
            ) {
              if (prov.approvalStatus != ApprovalStatus.REJECTED) {
                OutlinedButton(
                  onClick = { onReject(prov.id) },
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = RoseDanger),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .padding(end = 8.dp)
                    .testTag("admin_reject_btn_${prov.id}")
                ) {
                  Text("Reject", fontSize = 12.sp)
                }
              }

              if (prov.approvalStatus != ApprovalStatus.APPROVED) {
                Button(
                  onClick = { onApprove(prov.id) },
                  colors = ButtonDefaults.buttonColors(containerColor = EmeraldTrust),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.testTag("admin_approve_btn_${prov.id}")
                ) {
                  Text("Approve & Go Live", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
              }

              if (prov.approvalStatus == ApprovalStatus.APPROVED) {
                OutlinedButton(
                  onClick = { onSuspend(prov.id) },
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = RoseDanger),
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text("Suspend Seller", fontSize = 12.sp)
                }
              }
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdminMultiCityTab(
  cities: List<City>,
  onAddCityClick: () -> Unit,
  onToggleCity: (City) -> Unit,
  onAddAreaClick: (City) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Multi-City Expansion Engine",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "Add new Indian launch cities without code rewrites",
          fontSize = 11.sp,
          color = SlateMuted
        )
      }

      Button(
        onClick = onAddCityClick,
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.testTag("add_city_btn")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          Text("Add City", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    LazyColumn(
      modifier = Modifier.padding(top = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(cities) { city ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
            .testTag("admin_city_${city.id}")
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.LocationCity, contentDescription = null, tint = SaffronPrimary)
                Column {
                  Text(text = "${city.name}, ${city.state}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                  Text(text = "${city.areas.size} local neighborhood areas", fontSize = 11.sp, color = SlateMuted)
                }
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = if (city.isActive) "Active" else "Paused",
                  fontSize = 11.sp,
                  color = if (city.isActive) EmeraldTrust else SlateMuted,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(end = 6.dp)
                )
                Switch(
                  checked = city.isActive,
                  onCheckedChange = { onToggleCity(city) },
                  colors = SwitchDefaults.colors(checkedThumbColor = EmeraldTrust, checkedTrackColor = EmeraldLight)
                )
              }
            }

            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            ) {
              city.areas.forEach { area ->
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = SlateLight,
                  modifier = Modifier.padding(vertical = 2.dp)
                ) {
                  Text(text = area, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
              }
            }

            Button(
              onClick = { onAddAreaClick(city) },
              colors = ButtonDefaults.buttonColors(containerColor = IndigoLight),
              shape = RoundedCornerShape(6.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text("+ Add Neighborhood Area", fontSize = 11.sp, color = IndigoNavy, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AdminCategoriesTab(
  categories: List<Category>,
  onAddCategoryClick: () -> Unit,
  onToggleCategory: (Category) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Service Categories (${categories.size})",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Button(
        onClick = onAddCategoryClick,
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.testTag("add_category_btn")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          Text("Add Category", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    LazyColumn(
      modifier = Modifier.padding(top = 10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(categories) { cat ->
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .background(IndigoLight, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = CategoryIconHelper.getIcon(cat.iconName),
                  contentDescription = null,
                  tint = IndigoNavy,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(text = cat.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = cat.description, fontSize = 11.sp, color = SlateMuted, maxLines = 1)
              }
            }

            Switch(
              checked = cat.isActive,
              onCheckedChange = { onToggleCategory(cat) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldTrust, checkedTrackColor = EmeraldLight)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun AdminAllOrdersTab(
  requests: List<ServiceRequest>
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Text(
      text = "All Marketplace Orders & Requests (${requests.size})",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )

    LazyColumn(
      modifier = Modifier.padding(top = 10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(requests) { req ->
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(text = "${req.customerName} → ${req.providerName}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(text = "${req.serviceName} • ₹${req.totalAmount.toInt()} (${req.cityName})", fontSize = 11.sp, color = SaffronPrimary)
              }
              StatusChip(status = req.status)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AddCityDialog(
  onDismiss: () -> Unit,
  onConfirm: (name: String, state: String, areas: List<String>) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var state by remember { mutableStateOf("") }
  var areasStr by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Launch New Indian City", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("City Name (e.g. Udaipur, Surat)") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = state,
          onValueChange = { state = it },
          label = { Text("State (e.g. Rajasthan, Gujarat)") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = areasStr,
          onValueChange = { areasStr = it },
          label = { Text("Local Areas (comma-separated)") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val areasList = areasStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
          if (name.isNotBlank() && state.isNotBlank()) {
            onConfirm(name, state, if (areasList.isEmpty()) listOf("Central", "City Market") else areasList)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
      ) {
        Text("Launch City")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
private fun AddCategoryDialog(
  onDismiss: () -> Unit,
  onConfirm: (name: String, desc: String, icon: String) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var desc by remember { mutableStateOf("") }
  var iconName by remember { mutableStateOf("store") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Add Service Category", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Category Title (e.g. Pet Care & Grooming)") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = desc,
          onValueChange = { desc = it },
          label = { Text("Description") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank()) {
            onConfirm(name, desc.ifBlank { "Local service" }, iconName)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
      ) {
        Text("Create Category")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
