package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.local.OwnShopDatabase
import com.example.data.repository.AuthRepository
import com.example.data.repository.OwnShopRepository
import com.example.ui.main.MainScaffold
import com.example.ui.theme.OwnShopTheme
import com.example.ui.viewmodel.AdminViewModel
import com.example.ui.viewmodel.AuthViewModel
import com.example.ui.viewmodel.MarketplaceViewModel
import com.example.ui.viewmodel.ProviderPortalViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = OwnShopDatabase.getDatabase(applicationContext)
    val ownShopRepository = OwnShopRepository(database)
    val authRepository = AuthRepository(database)

    val marketplaceViewModel = MarketplaceViewModel(ownShopRepository)
    val authViewModel = AuthViewModel(authRepository)
    val providerPortalViewModel = ProviderPortalViewModel(ownShopRepository)
    val adminViewModel = AdminViewModel(ownShopRepository)

    setContent {
      OwnShopTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          MainScaffold(
            marketplaceViewModel = marketplaceViewModel,
            authViewModel = authViewModel,
            providerPortalViewModel = providerPortalViewModel,
            adminViewModel = adminViewModel
          )
        }
      }
    }
  }
}

