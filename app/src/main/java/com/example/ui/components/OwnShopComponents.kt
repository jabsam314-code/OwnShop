package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ApprovalStatus
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.ChakraNavy
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.IndiaGreen
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoNavy
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
import com.example.ui.theme.SlateTextPrimary

@Composable
fun OwnShopBrandEmblem(
  modifier: Modifier = Modifier,
  size: Int = 40
) {
  Box(
    modifier = modifier
      .size(size.dp)
      .clip(RoundedCornerShape((size * 0.25).dp))
      .background(Color.White)
      .border(1.dp, OwnNavyLight, RoundedCornerShape((size * 0.25).dp)),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_launcher_foreground),
      contentDescription = "OwnShop Brand Logo",
      modifier = Modifier.size((size * 1.1).dp)
    )
  }
}

@Composable
fun OwnShopFullBrandHeader(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("ownshop_full_brand_header"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // OS Emblem
      Box(
        modifier = Modifier
          .size(68.dp)
          .clip(CircleShape)
          .background(OwnNavyLight)
          .border(1.5.dp, OwnEmeraldLight, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_launcher_foreground),
          contentDescription = "OwnShop Emblem",
          modifier = Modifier.size(76.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // "OwnShop" Typography
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Own",
          fontWeight = FontWeight.Black,
          fontSize = 28.sp,
          color = OwnNavyPrimary,
          letterSpacing = (-0.5).sp
        )
        Text(
          text = "Shop",
          fontWeight = FontWeight.Black,
          fontSize = 28.sp,
          color = OwnEmeraldPrimary,
          letterSpacing = (-0.5).sp
        )
      }

      // Tagline with tricolor underlines
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
      ) {
        Box(modifier = Modifier.width(20.dp).height(2.dp).background(SaffronAccent))
        Text(
          text = "SHOP MORE. SAVE MORE. LIVE BETTER.",
          fontSize = 9.sp,
          fontWeight = FontWeight.Bold,
          color = OwnNavyPrimary,
          letterSpacing = 1.sp
        )
        Box(modifier = Modifier.width(20.dp).height(2.dp).background(OwnEmeraldPrimary))
      }

      // 4 Trust Pillars
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        TrustBadgeItem(
          icon = Icons.Default.Security,
          title = "TRUSTED",
          subtitle = "SELLERS",
          tint = OwnNavyPrimary
        )
        TrustBadgeDivider()
        TrustBadgeItem(
          icon = Icons.Default.LocalOffer,
          title = "BEST",
          subtitle = "PRICES",
          tint = OwnEmeraldPrimary
        )
        TrustBadgeDivider()
        TrustBadgeItem(
          icon = Icons.Default.LocalShipping,
          title = "FAST",
          subtitle = "DELIVERY",
          tint = OwnNavyPrimary
        )
        TrustBadgeDivider()
        TrustBadgeItem(
          icon = Icons.Default.Headphones,
          title = "24/7",
          subtitle = "SUPPORT",
          tint = OwnEmeraldPrimary
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Bottom Pill: "India's Local Marketplace"
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = OwnNavyPrimary,
        modifier = Modifier.fillMaxWidth(0.9f)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SaffronAccent))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "India's Local Marketplace",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndiaGreen))
        }
      }
    }
  }
}

@Composable
private fun TrustBadgeItem(
  icon: ImageVector,
  title: String,
  subtitle: String,
  tint: Color
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(horizontal = 2.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = "$title $subtitle",
      tint = tint,
      modifier = Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = title,
      fontSize = 9.sp,
      fontWeight = FontWeight.Black,
      color = SlateTextPrimary,
      textAlign = TextAlign.Center
    )
    Text(
      text = subtitle,
      fontSize = 8.sp,
      fontWeight = FontWeight.Bold,
      color = SlateMuted,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun TrustBadgeDivider() {
  Box(
    modifier = Modifier
      .height(26.dp)
      .width(1.dp)
      .background(SlateBorder)
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnShopTopBar(
  selectedCity: City?,
  currentUser: UserAccount?,
  onCityClick: () -> Unit,
  onRoleSwitchClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    modifier = modifier.testTag("ownshop_top_bar"),
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.surface,
      titleContentColor = MaterialTheme.colorScheme.onSurface
    ),
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // OwnShop Brand Logo Icon
        OwnShopBrandEmblem(size = 38)
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Own",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = OwnNavyPrimary
            )
            Text(
              text = "Shop",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = OwnEmeraldPrimary
            )
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(SaffronAccent))
            Text(
              text = "India's Local Marketplace",
              fontSize = 10.sp,
              color = SlateMuted,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    },
    actions = {
      // City Selector Button
      Surface(
        onClick = onCityClick,
        shape = RoundedCornerShape(20.dp),
        color = OwnNavyLight,
        modifier = Modifier
          .padding(end = 6.dp)
          .testTag("city_selector_top_button")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "City",
            tint = OwnNavyPrimary,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = selectedCity?.name ?: "Jaipur",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = OwnNavyPrimary
          )
          Text(
            text = "▾",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = OwnNavyPrimary
          )
        }
      }

      // Role Switcher Pill
      Surface(
        onClick = onRoleSwitchClick,
        shape = RoundedCornerShape(20.dp),
        color = when (currentUser?.role) {
          UserRole.ADMIN -> RoseLight
          UserRole.PROVIDER -> OwnEmeraldLight
          else -> SaffronLight
        },
        modifier = Modifier
          .padding(end = 8.dp)
          .testTag("role_switcher_top_button")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.SwapHoriz,
            contentDescription = "Switch Role",
            tint = when (currentUser?.role) {
              UserRole.ADMIN -> RoseDanger
              UserRole.PROVIDER -> OwnEmeraldPrimary
              else -> SaffronAccent
            },
            modifier = Modifier.size(14.dp)
          )
          Text(
            text = when (currentUser?.role) {
              UserRole.ADMIN -> "Admin"
              UserRole.PROVIDER -> "Seller"
              else -> "Customer"
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = when (currentUser?.role) {
              UserRole.ADMIN -> RoseDanger
              UserRole.PROVIDER -> OwnEmeraldPrimary
              else -> SaffronAccent
            }
          )
        }
      }
    }
  )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelectorBottomSheet(
  cities: List<City>,
  selectedCity: City?,
  onCitySelected: (City) -> Unit,
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
        .padding(bottom = 32.dp)
        .testTag("city_selector_bottom_sheet")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = SaffronPrimary,
          modifier = Modifier.size(24.dp)
        )
        Text(
          text = "Select Your City",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
      Text(
        text = "OwnShop connects you with verified businesses in your local neighborhood.",
        fontSize = 13.sp,
        color = SlateMuted,
        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
      )

      cities.forEach { city ->
        val isSelected = city.id == selectedCity?.id
        Card(
          onClick = {
            onCitySelected(city)
            onDismiss()
          },
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SaffronLight else SlateLight
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = if (isSelected) SaffronPrimary else SlateBorder,
              shape = RoundedCornerShape(12.dp)
            )
            .testTag("city_item_${city.id}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = city.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${city.state} • ${city.areas.size} local areas active",
                fontSize = 12.sp,
                color = SlateMuted
              )
            }
            if (isSelected) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Selected",
                tint = SaffronPrimary,
                modifier = Modifier.size(22.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun StatusChip(
  status: RequestStatus,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, label) = when (status) {
    RequestStatus.PENDING -> Triple(AmberLight, AmberWarning, "Pending")
    RequestStatus.ACCEPTED -> Triple(IndigoLight, IndigoNavy, "Accepted")
    RequestStatus.IN_PROGRESS -> Triple(Color(0xFFF3E8FF), Color(0xFF7E22CE), "In Progress")
    RequestStatus.COMPLETED -> Triple(EmeraldLight, EmeraldTrust, "Completed")
    RequestStatus.REJECTED -> Triple(RoseLight, RoseDanger, "Rejected")
    RequestStatus.CANCELLED -> Triple(Color(0xFFF1F5F9), SlateMuted, "Cancelled")
  }

  Surface(
    shape = RoundedCornerShape(12.dp),
    color = bgColor,
    modifier = modifier
  ) {
    Text(
      text = label,
      color = textColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}

@Composable
fun ApprovalStatusBadge(
  status: ApprovalStatus,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, label) = when (status) {
    ApprovalStatus.APPROVED -> Triple(EmeraldLight, EmeraldTrust, "Approved & Listed")
    ApprovalStatus.PENDING -> Triple(AmberLight, AmberWarning, "Pending Admin Verification")
    ApprovalStatus.REJECTED -> Triple(RoseLight, RoseDanger, "Application Rejected")
    ApprovalStatus.SUSPENDED -> Triple(RoseLight, RoseDanger, "Account Suspended")
  }

  Surface(
    shape = RoundedCornerShape(12.dp),
    color = bgColor,
    modifier = modifier
  ) {
    Text(
      text = label,
      color = textColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProviderCard(
  provider: Provider,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onClick,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      .testTag("provider_card_${provider.id}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        // Store Avatar Icon
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(48.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(IndigoLight),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Store,
              contentDescription = null,
              tint = IndigoNavy,
              modifier = Modifier.size(28.dp)
            )
          }
          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(
                text = provider.businessName,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              if (provider.approvalStatus == ApprovalStatus.APPROVED) {
                Icon(
                  imageVector = Icons.Default.Verified,
                  contentDescription = "Verified Provider",
                  tint = EmeraldTrust,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
            Text(
              text = "By ${provider.ownerName} • ${provider.experienceYears} yrs exp",
              fontSize = 12.sp,
              color = SlateMuted
            )
          }
        }

        // Rating Star Chip
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SaffronLight
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Rating",
              tint = SaffronPrimary,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "${provider.rating}",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = SaffronPrimary
            )
            if (provider.reviewCount > 0) {
              Text(
                text = "(${provider.reviewCount})",
                fontSize = 10.sp,
                color = SlateMuted
              )
            }
          }
        }
      }

      Text(
        text = provider.description,
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
      )

      // Service Areas
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(bottom = 8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = SlateMuted,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = provider.serviceAreas.take(3).joinToString(", ") +
              if (provider.serviceAreas.size > 3) " +${provider.serviceAreas.size - 3} more" else "",
          fontSize = 11.sp,
          color = SlateMuted,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      // Legal Badges Row
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(bottom = 10.dp)
      ) {
        if (provider.isFssaiVerified) {
          LegalBadgePill(
            text = "FSSAI Verified",
            icon = Icons.Default.Security,
            color = EmeraldTrust,
            bgColor = EmeraldLight
          )
        }
        if (provider.isDrugLicenceVerified) {
          LegalBadgePill(
            text = "Drug Licence Verified",
            icon = Icons.Default.Security,
            color = IndigoNavy,
            bgColor = IndigoLight
          )
        }
        if (provider.isAvailable) {
          LegalBadgePill(
            text = "Available Today",
            icon = Icons.Default.CheckCircle,
            color = EmeraldTrust,
            bgColor = EmeraldLight
          )
        }
      }

      // Bottom Price & Action Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Starting from",
            fontSize = 10.sp,
            color = SlateMuted
          )
          Text(
            text = "₹${provider.startingPrice.toInt()}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = SaffronPrimary
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = IndigoNavy,
          modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ) {
          Text(
            text = "View & Request",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }
    }
  }
}

@Composable
fun LegalBadgePill(
  text: String,
  icon: ImageVector,
  color: Color,
  bgColor: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = bgColor,
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(12.dp)
      )
      Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = color
      )
    }
  }
}

@Composable
fun StarRatingBar(
  rating: Int,
  onRatingChanged: (Int) -> Unit = {},
  isEditable: Boolean = false,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 1..5) {
      val isFilled = i <= rating
      Icon(
        imageVector = if (isFilled) Icons.Default.Star else Icons.Outlined.Star,
        contentDescription = "Star $i",
        tint = if (isFilled) SaffronPrimary else SlateBorder,
        modifier = Modifier
          .size(24.dp)
          .then(
            if (isEditable) {
              Modifier.clickable { onRatingChanged(i) }
            } else Modifier
          )
      )
    }
  }
}
