package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIconHelper {
  fun getIcon(iconName: String): ImageVector {
    return when (iconName) {
      "ac_unit" -> Icons.Default.AcUnit
      "bolt" -> Icons.Default.Bolt
      "plumbing" -> Icons.Default.Plumbing
      "restaurant" -> Icons.Default.Restaurant
      "medical_services" -> Icons.Default.MedicalServices
      "cleaning_services" -> Icons.Default.CleaningServices
      "face" -> Icons.Default.Face
      "directions_car" -> Icons.Default.DirectionsCar
      "tv" -> Icons.Default.Tv
      "devices" -> Icons.Default.Devices
      "shopping_cart" -> Icons.Default.ShoppingCart
      "format_paint" -> Icons.Default.FormatPaint
      "local_shipping" -> Icons.Default.LocalShipping
      "camera_alt" -> Icons.Default.CameraAlt
      "school" -> Icons.Default.School
      "home_repair_service" -> Icons.Default.HomeRepairService
      "build" -> Icons.Default.Build
      else -> Icons.Default.Category
    }
  }
}
