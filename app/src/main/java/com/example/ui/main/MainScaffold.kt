package com.example.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.Provider
import com.example.data.model.UserRole
import com.example.ui.components.CitySelectorBottomSheet
import com.example.ui.components.FilterBottomSheet
import com.example.ui.components.OwnShopTopBar
import com.example.ui.components.RoleSwitcherBottomSheet
import com.example.ui.screens.admin_portal.AdminPortalScreen
import com.example.ui.screens.auth.ProfileScreen
import com.example.ui.screens.auth.RegisterProviderScreen
import com.example.ui.screens.categories.CategoriesScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.nearby.NearbyProvidersScreen
import com.example.ui.screens.provider_detail.ProviderDetailScreen
import com.example.ui.screens.provider_portal.ProviderPortalScreen
import com.example.ui.screens.requests.CustomerRequestsScreen
import com.example.ui.screens.search.SearchExploreScreen
import com.example.ui.theme.OwnEmeraldLight
import com.example.ui.theme.OwnEmeraldPrimary
import com.example.ui.theme.OwnNavyLight
import com.example.ui.theme.OwnNavyPrimary
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateMuted
import com.example.ui.viewmodel.AdminViewModel
import com.example.ui.viewmodel.AuthViewModel
import com.example.ui.viewmodel.MarketplaceViewModel
import com.example.ui.viewmodel.ProviderPortalViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(
  marketplaceViewModel: MarketplaceViewModel,
  authViewModel: AuthViewModel,
  providerPortalViewModel: ProviderPortalViewModel,
  adminViewModel: AdminViewModel
) {
  val selectedCity by marketplaceViewModel.selectedCity.collectAsState()
  val cities by marketplaceViewModel.cities.collectAsState()
  val categories by marketplaceViewModel.categories.collectAsState()
  val filteredProviders by marketplaceViewModel.filteredProviders.collectAsState()
  val searchFilter by marketplaceViewModel.searchFilter.collectAsState()
  val currentUser by authViewModel.currentUser.collectAsState()

  // Provider Portal States
  val currentProvider by providerPortalViewModel.providerDetails.collectAsState()
  val providerRequests by providerPortalViewModel.providerRequests.collectAsState()
  val providerServices by providerPortalViewModel.providerServices.collectAsState()

  // Admin Portal States
  val adminStats by adminViewModel.stats.collectAsState()
  val adminProviders by adminViewModel.allProviders.collectAsState()
  val adminRequests by adminViewModel.allRequests.collectAsState()
  val adminCities by adminViewModel.allCities.collectAsState()
  val adminCategories by adminViewModel.allCategories.collectAsState()
  val adminUsers by adminViewModel.allUsers.collectAsState()

  // Customer Requests
  val customerRequests by marketplaceViewModel.getCustomerRequests(currentUser?.id ?: "user_cust_1")
    .collectAsState(initial = emptyList())

  // Navigation State
  var currentCustomerTab by remember { mutableIntStateOf(0) }
  var showCitySelector by remember { mutableStateOf(false) }
  var showRoleSwitcher by remember { mutableStateOf(false) }
  var showFilterSheet by remember { mutableStateOf(false) }

  // Overlay Screens
  var activeDetailProvider by remember { mutableStateOf<Provider?>(null) }
  var showRegisterProviderScreen by remember { mutableStateOf(false) }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  // When currentUser changes to Provider, sync providerPortalViewModel
  androidx.compose.runtime.LaunchedEffect(currentUser) {
    if (currentUser?.role == UserRole.PROVIDER) {
      providerPortalViewModel.setProviderFromUser(currentUser)
    }
  }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      if (activeDetailProvider == null && !showRegisterProviderScreen) {
        OwnShopTopBar(
          selectedCity = selectedCity,
          currentUser = currentUser,
          onCityClick = { showCitySelector = true },
          onRoleSwitchClick = { showRoleSwitcher = true }
        )
      }
    },
    bottomBar = {
      if (activeDetailProvider == null && !showRegisterProviderScreen && currentUser?.role == UserRole.CUSTOMER) {
        NavigationBar(
          containerColor = MaterialTheme.colorScheme.surface,
          tonalElevation = 8.dp,
          modifier = Modifier.testTag("customer_bottom_bar")
        ) {
          NavigationBarItem(
            selected = currentCustomerTab == 0,
            onClick = { currentCustomerTab = 0 },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = OwnNavyPrimary,
              selectedTextColor = OwnNavyPrimary,
              indicatorColor = OwnNavyLight
            ),
            modifier = Modifier.testTag("nav_item_home")
          )
          NavigationBarItem(
            selected = currentCustomerTab == 1,
            onClick = { currentCustomerTab = 1 },
            icon = { Icon(Icons.Default.Category, contentDescription = "Categories") },
            label = { Text("Categories", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = OwnEmeraldPrimary,
              selectedTextColor = OwnEmeraldPrimary,
              indicatorColor = OwnEmeraldLight
            ),
            modifier = Modifier.testTag("nav_item_categories")
          )
          NavigationBarItem(
            selected = currentCustomerTab == 2,
            onClick = { currentCustomerTab = 2 },
            icon = { Icon(Icons.Default.Search, contentDescription = "Explore") },
            label = { Text("Search", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = OwnNavyPrimary,
              selectedTextColor = OwnNavyPrimary,
              indicatorColor = OwnNavyLight
            ),
            modifier = Modifier.testTag("nav_item_search")
          )
          NavigationBarItem(
            selected = currentCustomerTab == 3,
            onClick = { currentCustomerTab = 3 },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Nearby") },
            label = { Text("Nearby", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = OwnEmeraldPrimary,
              selectedTextColor = OwnEmeraldPrimary,
              indicatorColor = OwnEmeraldLight
            ),
            modifier = Modifier.testTag("nav_item_nearby")
          )
          NavigationBarItem(
            selected = currentCustomerTab == 4,
            onClick = { currentCustomerTab = 4 },
            icon = { Icon(Icons.Default.ReceiptLong, contentDescription = "Orders") },
            label = { Text("My Orders", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = OwnNavyPrimary,
              selectedTextColor = OwnNavyPrimary,
              indicatorColor = OwnNavyLight
            ),
            modifier = Modifier.testTag("nav_item_orders")
          )
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      // OVERLAY 1: Active Detail Provider
      if (activeDetailProvider != null) {
        val prov = activeDetailProvider!!
        val services by marketplaceViewModel.getServicesForProvider(prov.id).collectAsState(initial = emptyList())
        val reviews by marketplaceViewModel.getReviewsForProvider(prov.id).collectAsState(initial = emptyList())

        ProviderDetailScreen(
          provider = prov,
          categories = categories,
          services = services,
          reviews = reviews,
          currentUser = currentUser,
          onBack = { activeDetailProvider = null },
          onBookService = { srv, cat, name, phone, address, date, time, quantity, notes ->
            val userAcc = currentUser ?: com.example.data.model.UserAccount(
              id = "user_guest",
              name = name,
              email = "guest@ownshop.com",
              phone = phone,
              role = UserRole.CUSTOMER,
              cityId = prov.cityId,
              cityName = prov.cityName,
              address = address
            )
            marketplaceViewModel.createServiceRequest(
              customer = userAcc,
              provider = prov,
              category = cat,
              service = srv,
              quantity = quantity,
              preferredDate = date,
              preferredTime = time,
              notes = notes,
              onSuccess = { req ->
                activeDetailProvider = null
                currentCustomerTab = 4 // switch to My Orders
                scope.launch {
                  snackbarHostState.showSnackbar("Request sent to ${prov.businessName}! Track status in My Orders.")
                }
              }
            )
          },
          onSubmitReview = { rating, comment ->
            if (currentUser != null) {
              marketplaceViewModel.submitReview(
                customer = currentUser!!,
                providerId = prov.id,
                requestId = "rev_${System.currentTimeMillis()}",
                rating = rating,
                comment = comment,
                onComplete = {
                  scope.launch {
                    snackbarHostState.showSnackbar("Thank you! Your verified review has been posted.")
                  }
                }
              )
            }
          }
        )
      }
      // OVERLAY 2: Register Provider Screen
      else if (showRegisterProviderScreen) {
        RegisterProviderScreen(
          cities = cities,
          categories = categories,
          onRegister = { bName, oName, email, phone, cityId, cityName, areas, catIds, desc, price, exp, addr, fssai, drug ->
            authViewModel.registerProvider(
              businessName = bName,
              ownerName = oName,
              email = email,
              phone = phone,
              cityId = cityId,
              cityName = cityName,
              serviceAreas = areas,
              categoryIds = catIds,
              description = desc,
              startingPrice = price,
              experienceYears = exp,
              address = addr,
              fssaiNumber = fssai,
              drugLicenceNumber = drug,
              onSuccess = {
                showRegisterProviderScreen = false
                scope.launch {
                  snackbarHostState.showSnackbar("Seller account created! Submitted for Admin verification.")
                }
              }
            )
          },
          onBack = { showRegisterProviderScreen = false }
        )
      }
      // ROLE BASED ROOT SCREENS
      else {
        when (currentUser?.role) {
          UserRole.PROVIDER -> {
            ProviderPortalScreen(
              provider = currentProvider,
              requests = providerRequests,
              services = providerServices,
              onAcceptRequest = { providerPortalViewModel.acceptRequest(it) },
              onRejectRequest = { id, reason -> providerPortalViewModel.rejectRequest(id, reason) },
              onStartService = { providerPortalViewModel.startService(it) },
              onCompleteService = { providerPortalViewModel.completeService(it) },
              onToggleAvailability = { providerPortalViewModel.toggleAvailability() },
              onAddService = { title, desc, price, unit, time ->
                providerPortalViewModel.addService(title, desc, price, unit, time)
              },
              onDeleteService = { providerPortalViewModel.deleteService(it) },
              onSubmitFssai = { providerPortalViewModel.submitFssai(it) },
              onSubmitDrugLicence = { providerPortalViewModel.submitDrugLicence(it) }
            )
          }

          UserRole.ADMIN -> {
            AdminPortalScreen(
              stats = adminStats,
              providers = adminProviders,
              requests = adminRequests,
              cities = adminCities,
              categories = adminCategories,
              users = adminUsers,
              onApproveProvider = { adminViewModel.approveProvider(it) },
              onRejectProvider = { adminViewModel.rejectProvider(it) },
              onSuspendProvider = { adminViewModel.suspendProvider(it) },
              onVerifyFssai = { id, verified -> adminViewModel.verifyFssai(id, verified) },
              onVerifyDrugLicence = { id, verified -> adminViewModel.verifyDrugLicence(id, verified) },
              onAddCity = { name, state, areas -> adminViewModel.addNewCity(name, state, areas) },
              onAddAreaToCity = { city, area -> adminViewModel.addAreaToCity(city, area) },
              onToggleCityActive = { adminViewModel.toggleCityActive(it) },
              onAddCategory = { name, desc, icon -> adminViewModel.addNewCategory(name, desc, icon) },
              onToggleCategoryActive = { adminViewModel.toggleCategoryActive(it) }
            )
          }

          else -> {
            // CUSTOMER VIEW
            when (currentCustomerTab) {
              0 -> HomeScreen(
                selectedCity = selectedCity,
                categories = categories,
                providers = filteredProviders,
                searchQuery = searchFilter.searchQuery,
                onSearchQueryChange = { marketplaceViewModel.updateSearchQuery(it) },
                onSearchSubmit = { currentCustomerTab = 2 },
                onCategoryClick = { cat ->
                  marketplaceViewModel.updateCategoryFilter(cat.id)
                  currentCustomerTab = 2
                },
                onProviderClick = { activeDetailProvider = it },
                onViewAllCategories = { currentCustomerTab = 1 },
                onViewAllProviders = { currentCustomerTab = 2 },
                onFilterClick = { showFilterSheet = true },
                onCityClick = { showCitySelector = true },
                onBecomeProviderClick = { showRegisterProviderScreen = true }
              )

              1 -> CategoriesScreen(
                selectedCity = selectedCity,
                categories = categories,
                onCategorySelected = { cat ->
                  marketplaceViewModel.updateCategoryFilter(cat.id)
                  currentCustomerTab = 2
                }
              )

              2 -> SearchExploreScreen(
                selectedCity = selectedCity,
                categories = categories,
                searchFilter = searchFilter,
                providers = filteredProviders,
                onSearchQueryChange = { marketplaceViewModel.updateSearchQuery(it) },
                onCategoryFilterChange = { marketplaceViewModel.updateCategoryFilter(it) },
                onAreaFilterChange = { marketplaceViewModel.updateAreaFilter(it) },
                onFilterClick = { showFilterSheet = true },
                onClearFilters = { marketplaceViewModel.clearFilters() },
                onProviderClick = { activeDetailProvider = it }
              )

              3 -> NearbyProvidersScreen(
                selectedCity = selectedCity,
                providers = filteredProviders,
                onProviderClick = { activeDetailProvider = it }
              )

              4 -> CustomerRequestsScreen(
                requests = customerRequests,
                onCancelRequest = { id, reason -> marketplaceViewModel.cancelRequest(id, reason) },
                onSubmitReview = { pId, rId, rating, comment ->
                  if (currentUser != null) {
                    marketplaceViewModel.submitReview(
                      customer = currentUser!!,
                      providerId = pId,
                      requestId = rId,
                      rating = rating,
                      comment = comment,
                      onComplete = {
                        scope.launch {
                          snackbarHostState.showSnackbar("Review submitted successfully!")
                        }
                      }
                    )
                  }
                }
              )
            }
          }
        }
      }
    }
  }

  // City Selector Sheet
  if (showCitySelector) {
    CitySelectorBottomSheet(
      cities = cities,
      selectedCity = selectedCity,
      onCitySelected = { city ->
        marketplaceViewModel.selectCity(city)
      },
      onDismiss = { showCitySelector = false }
    )
  }

  // Role Switcher Sheet
  if (showRoleSwitcher) {
    RoleSwitcherBottomSheet(
      currentUser = currentUser,
      onSelectRole = { role ->
        authViewModel.switchRole(role)
      },
      onDismiss = { showRoleSwitcher = false }
    )
  }

  // Filter Sheet
  if (showFilterSheet) {
    FilterBottomSheet(
      currentFilter = searchFilter,
      selectedCity = selectedCity,
      categories = categories,
      onApplyFilter = { newFilter ->
        marketplaceViewModel.updateAreaFilter(newFilter.area)
        marketplaceViewModel.updateCategoryFilter(newFilter.categoryId)
        marketplaceViewModel.updateRatingFilter(newFilter.minRating)
        marketplaceViewModel.updatePriceFilter(newFilter.maxPrice)
        marketplaceViewModel.toggleAvailabilityOnly(newFilter.onlyAvailable)
      },
      onResetFilter = { marketplaceViewModel.clearFilters() },
      onDismiss = { showFilterSheet = false }
    )
  }
}
