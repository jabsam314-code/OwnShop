package com.example.ui.screens.provider_portal

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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.ui.components.ApprovalStatusBadge
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
fun ProviderPortalScreen(
  provider: Provider?,
  requests: List<ServiceRequest>,
  services: List<ServiceItem>,
  onAcceptRequest: (String) -> Unit,
  onRejectRequest: (String, String) -> Unit,
  onStartService: (String) -> Unit,
  onCompleteService: (String) -> Unit,
  onToggleAvailability: () -> Unit,
  onAddService: (title: String, desc: String, price: Double, unit: String, time: String) -> Unit,
  onDeleteService: (ServiceItem) -> Unit,
  onSubmitFssai: (String) -> Unit,
  onSubmitDrugLicence: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Orders & Requests", "My Services Catalog", "Business & Compliance")

  var showAddServiceSheet by remember { mutableStateOf(false) }
  var requestToReject by remember { mutableStateOf<ServiceRequest?>(null) }
  var rejectionReason by remember { mutableStateOf("") }
  var showFssaiDialog by remember { mutableStateOf(false) }
  var showDrugLicenceDialog by remember { mutableStateOf(false) }

  if (provider == null) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
      contentAlignment = Alignment.Center
    ) {
      Text("No provider registered for this account.", color = SlateMuted)
    }
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("provider_portal_screen")
  ) {
    // Top Seller Header Card
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
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = provider.businessName,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = IndigoNavy
              )
              if (provider.approvalStatus == ApprovalStatus.APPROVED) {
                Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldTrust, modifier = Modifier.size(18.dp))
              }
            }
            Text(
              text = "${provider.cityName} • Owner: ${provider.ownerName}",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }

          ApprovalStatusBadge(status = provider.approvalStatus)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Stats Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          StatCard(
            label = "Pending",
            value = "${requests.count { it.status == RequestStatus.PENDING }}",
            color = AmberWarning,
            bgColor = AmberLight,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            label = "Active Jobs",
            value = "${requests.count { it.status == RequestStatus.ACCEPTED || it.status == RequestStatus.IN_PROGRESS }}",
            color = IndigoNavy,
            bgColor = IndigoLight,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            label = "Rating",
            value = "${provider.rating} ★",
            color = SaffronPrimary,
            bgColor = SaffronLight,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            label = "Completed",
            value = "${requests.count { it.status == RequestStatus.COMPLETED }}",
            color = EmeraldTrust,
            bgColor = EmeraldLight,
            modifier = Modifier.weight(1f)
          )
        }

        // Online Duty Switch
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .background(if (provider.isAvailable) EmeraldTrust else Color.Gray, RoundedCornerShape(5.dp))
            )
            Text(
              text = if (provider.isAvailable) "Shop is ONLINE (Accepting Orders)" else "Shop is OFFLINE",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = if (provider.isAvailable) EmeraldTrust else SlateMuted
            )
          }
          Switch(
            checked = provider.isAvailable,
            onCheckedChange = { onToggleAvailability() },
            colors = SwitchDefaults.colors(checkedThumbColor = EmeraldTrust, checkedTrackColor = EmeraldLight),
            modifier = Modifier.testTag("provider_online_switch")
          )
        }
      }
    }

    // Tab Navigation
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

    // Content based on tab
    when (selectedTab) {
      0 -> ProviderRequestsTab(
        requests = requests,
        onAccept = onAcceptRequest,
        onRejectPrompt = { requestToReject = it },
        onStart = onStartService,
        onComplete = onCompleteService
      )
      1 -> ProviderServicesTab(
        services = services,
        onAddClick = { showAddServiceSheet = true },
        onDeleteService = onDeleteService
      )
      2 -> ProviderComplianceTab(
        provider = provider,
        onOpenFssaiDialog = { showFssaiDialog = true },
        onOpenDrugLicenceDialog = { showDrugLicenceDialog = true }
      )
    }
  }

  // Reject Reason Dialog
  if (requestToReject != null) {
    AlertDialog(
      onDismissRequest = { requestToReject = null },
      title = { Text("Reject Service Request", fontWeight = FontWeight.Bold) },
      text = {
        Column {
          Text("Provide a reason to the customer for declining this order:", fontSize = 13.sp)
          OutlinedTextField(
            value = rejectionReason,
            onValueChange = { rejectionReason = it },
            placeholder = { Text("e.g. Schedule fully booked, parts out of stock") },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 10.dp)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onRejectRequest(requestToReject!!.id, rejectionReason.ifBlank { "Provider currently unavailable for this slot" })
            requestToReject = null
            rejectionReason = ""
          },
          colors = ButtonDefaults.buttonColors(containerColor = RoseDanger)
        ) {
          Text("Reject Order")
        }
      },
      dismissButton = {
        TextButton(onClick = { requestToReject = null }) {
          Text("Back")
        }
      }
    )
  }

  // Add Service Sheet
  if (showAddServiceSheet) {
    AddServiceBottomSheet(
      onDismiss = { showAddServiceSheet = false },
      onAdd = { title, desc, price, unit, time ->
        onAddService(title, desc, price, unit, time)
        showAddServiceSheet = false
      }
    )
  }

  // FSSAI Submission Dialog
  if (showFssaiDialog) {
    ComplianceInputDialog(
      title = "Submit FSSAI Food License",
      subtitle = "Mandatory for all food, sweets, catering & bakery businesses in India.",
      label = "14-Digit FSSAI License Number",
      initialValue = provider.fssaiNumber ?: "",
      onDismiss = { showFssaiDialog = false },
      onSubmit = {
        onSubmitFssai(it)
        showFssaiDialog = false
      }
    )
  }

  // Drug License Submission Dialog
  if (showDrugLicenceDialog) {
    ComplianceInputDialog(
      title = "Submit Drug / Pharmacy License",
      subtitle = "Mandatory for medical dispensaries and pharmacies in India.",
      label = "State Drug License Number (Form 20B/21B)",
      initialValue = provider.drugLicenceNumber ?: "",
      onDismiss = { showDrugLicenceDialog = false },
      onSubmit = {
        onSubmitDrugLicence(it)
        showDrugLicenceDialog = false
      }
    )
  }
}

@Composable
private fun StatCard(
  label: String,
  value: String,
  color: Color,
  bgColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = bgColor),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
      Text(text = label, fontSize = 10.sp, color = color.copy(alpha = 0.8f))
    }
  }
}

@Composable
private fun ProviderRequestsTab(
  requests: List<ServiceRequest>,
  onAccept: (String) -> Unit,
  onRejectPrompt: (ServiceRequest) -> Unit,
  onStart: (String) -> Unit,
  onComplete: (String) -> Unit
) {
  if (requests.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "No orders received yet. Make sure your shop status is ONLINE.",
        color = SlateMuted,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
    }
  } else {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(requests) { req ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
            .testTag("provider_req_card_${req.id}")
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = req.customerName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "Order #${req.id.takeLast(6)} • ${req.preferredDate} (${req.preferredTime})",
                  fontSize = 11.sp,
                  color = SlateMuted
                )
              }
              StatusChip(status = req.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "${req.serviceName} x ${req.quantity}",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = IndigoNavy
            )
            Text(
              text = "Total Payable: ₹${req.totalAmount.toInt()} (COD / Pay on Service)",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = SaffronPrimary
            )

            // Address & Customer Phone
            Column(
              verticalArrangement = Arrangement.spacedBy(4.dp),
              modifier = Modifier.padding(top = 6.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(14.dp))
                Text(text = req.customerAddress, fontSize = 12.sp, color = SlateMuted)
              }
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.Call, contentDescription = null, tint = SlateMuted, modifier = Modifier.size(14.dp))
                Text(text = req.customerPhone, fontSize = 12.sp, color = SlateMuted)
              }
              if (req.descriptionNotes.isNotBlank()) {
                Text(text = "Note: \"${req.descriptionNotes}\"", fontSize = 11.sp, color = IndigoNavy)
              }
            }

            // Action Buttons
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
              horizontalArrangement = Arrangement.End,
              verticalAlignment = Alignment.CenterVertically
            ) {
              when (req.status) {
                RequestStatus.PENDING -> {
                  OutlinedButton(
                    onClick = { onRejectPrompt(req) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RoseDanger),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                      .padding(end = 8.dp)
                      .testTag("reject_req_btn_${req.id}")
                  ) {
                    Text("Decline", fontSize = 12.sp)
                  }

                  Button(
                    onClick = { onAccept(req.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTrust),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("accept_req_btn_${req.id}")
                  ) {
                    Text("Accept Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
                RequestStatus.ACCEPTED -> {
                  Button(
                    onClick = { onStart(req.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoNavy),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("start_req_btn_${req.id}")
                  ) {
                    Text("Start Service / Out for Delivery", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
                RequestStatus.IN_PROGRESS -> {
                  Button(
                    onClick = { onComplete(req.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTrust),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("complete_req_btn_${req.id}")
                  ) {
                    Text("Mark Completed & Collect ₹${req.totalAmount.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
                else -> {
                  // No actions needed for completed/cancelled
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ProviderServicesTab(
  services: List<ServiceItem>,
  onAddClick: () -> Unit,
  onDeleteService: (ServiceItem) -> Unit
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
        text = "Your Menu / Service Offerings (${services.size})",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Button(
        onClick = onAddClick,
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.testTag("add_new_service_btn")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          Text("Add Service", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    LazyColumn(
      modifier = Modifier.padding(top = 10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(services) { service ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = service.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = service.description,
                fontSize = 12.sp,
                color = SlateMuted,
                lineHeight = 16.sp
              )
              Text(
                text = "₹${service.price.toInt()} / ${service.priceUnit} • ${service.estimatedTime}",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SaffronPrimary,
                modifier = Modifier.padding(top = 4.dp)
              )
            }

            IconButton(onClick = { onDeleteService(service) }) {
              Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RoseDanger, modifier = Modifier.size(20.dp))
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ProviderComplianceTab(
  provider: Provider,
  onOpenFssaiDialog: () -> Unit,
  onOpenDrugLicenceDialog: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Text(
      text = "Legal Compliance & Documents",
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = "OwnShop enforces mandatory verification for food and medical businesses to protect customers.",
      fontSize = 12.sp,
      color = SlateMuted,
      modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
    )

    // FSSAI CARD
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = if (provider.isFssaiVerified) EmeraldLight else SaffronLight),
      modifier = Modifier
        .fillMaxWidth()
        .border(
          1.dp,
          if (provider.isFssaiVerified) EmeraldTrust.copy(alpha = 0.3f) else SaffronPrimary.copy(alpha = 0.3f),
          RoundedCornerShape(12.dp)
        )
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Security, contentDescription = null, tint = if (provider.isFssaiVerified) EmeraldTrust else SaffronPrimary)
            Text(
              text = "FSSAI Food License",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
          if (provider.isFssaiVerified) {
            LegalBadgePill(text = "Verified", icon = Icons.Default.CheckCircle, color = EmeraldTrust, bgColor = Color.White)
          } else if (!provider.fssaiNumber.isNullOrBlank()) {
            LegalBadgePill(text = "Under Review", icon = Icons.Default.Security, color = AmberWarning, bgColor = AmberLight)
          }
        }

        Text(
          text = if (provider.fssaiNumber.isNullOrBlank()) "Required for Food & Sweets vendors. Submit your 14-digit FSSAI registration number."
          else "License Number: ${provider.fssaiNumber}",
          fontSize = 12.sp,
          color = SlateMuted,
          modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
          onClick = onOpenFssaiDialog,
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("submit_fssai_btn")
        ) {
          Text(if (provider.fssaiNumber.isNullOrBlank()) "Submit FSSAI Document" else "Update FSSAI Document", fontSize = 12.sp)
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // DRUG LICENCE CARD
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = if (provider.isDrugLicenceVerified) IndigoLight else SlateLight),
      modifier = Modifier
        .fillMaxWidth()
        .border(
          1.dp,
          if (provider.isDrugLicenceVerified) IndigoNavy.copy(alpha = 0.3f) else SlateBorder,
          RoundedCornerShape(12.dp)
        )
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Security, contentDescription = null, tint = IndigoNavy)
            Text(
              text = "State Drug / Pharmacy License",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
          if (provider.isDrugLicenceVerified) {
            LegalBadgePill(text = "Verified", icon = Icons.Default.CheckCircle, color = IndigoNavy, bgColor = Color.White)
          } else if (!provider.drugLicenceNumber.isNullOrBlank()) {
            LegalBadgePill(text = "Under Review", icon = Icons.Default.Security, color = AmberWarning, bgColor = AmberLight)
          }
        }

        Text(
          text = if (provider.drugLicenceNumber.isNullOrBlank()) "Required for medical stores & pharmacies. Submit Form 20B/21B license number."
          else "License Number: ${provider.drugLicenceNumber}",
          fontSize = 12.sp,
          color = SlateMuted,
          modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
          onClick = onOpenDrugLicenceDialog,
          colors = ButtonDefaults.buttonColors(containerColor = IndigoNavy),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("submit_drug_licence_btn")
        ) {
          Text(if (provider.drugLicenceNumber.isNullOrBlank()) "Submit Drug License" else "Update Drug License", fontSize = 12.sp)
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddServiceBottomSheet(
  onDismiss: () -> Unit,
  onAdd: (title: String, desc: String, price: Double, unit: String, time: String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var title by remember { mutableStateOf("") }
  var desc by remember { mutableStateOf("") }
  var priceStr by remember { mutableStateOf("") }
  var unit by remember { mutableStateOf("per unit") }
  var time by remember { mutableStateOf("45 mins") }
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
        .testTag("add_service_sheet")
    ) {
      Text(
        text = "Add Service or Product Offering",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Service Title (e.g. Split AC Jet Foam Clean)") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )

      OutlinedTextField(
        value = desc,
        onValueChange = { desc = it },
        label = { Text("Short Description") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        maxLines = 2
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = priceStr,
          onValueChange = { priceStr = it },
          label = { Text("Price (₹)") },
          modifier = Modifier.weight(1f),
          singleLine = true
        )
        OutlinedTextField(
          value = unit,
          onValueChange = { unit = it },
          label = { Text("Unit (e.g. per visit, 1 Kg)") },
          modifier = Modifier.weight(1f),
          singleLine = true
        )
      }

      OutlinedTextField(
        value = time,
        onValueChange = { time = it },
        label = { Text("Estimated Duration (e.g. 30 mins, 2 hours)") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
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
          val price = priceStr.toDoubleOrNull()
          if (title.isBlank() || price == null) {
            error = "Please provide a valid title and numeric price."
          } else {
            error = null
            onAdd(title, desc.ifBlank { "Doorstep local service" }, price, unit, time)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("Save Service to Catalog", fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun ComplianceInputDialog(
  title: String,
  subtitle: String,
  label: String,
  initialValue: String,
  onDismiss: () -> Unit,
  onSubmit: (String) -> Unit
) {
  var value by remember { mutableStateOf(initialValue) }
  var docUploaded by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
    text = {
      Column {
        Text(subtitle, fontSize = 12.sp, color = SlateMuted)
        OutlinedTextField(
          value = value,
          onValueChange = { value = it },
          label = { Text(label) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
          singleLine = true
        )

        Card(
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = SlateLight),
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = if (docUploaded) "✓ Certificate PDF attached" else "Attach Certificate PDF / Image",
              fontSize = 12.sp,
              color = if (docUploaded) EmeraldTrust else IndigoNavy,
              fontWeight = FontWeight.Bold
            )
            Button(
              onClick = { docUploaded = !docUploaded },
              colors = ButtonDefaults.buttonColors(containerColor = if (docUploaded) EmeraldTrust else IndigoNavy),
              shape = RoundedCornerShape(6.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(if (docUploaded) "Attached" else "Upload", fontSize = 10.sp)
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (value.isNotBlank()) onSubmit(value)
        },
        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
      ) {
        Text("Submit for Verification")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
