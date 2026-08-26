package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

  val currentUser: StateFlow<UserAccount?> = authRepository.currentUser
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  private val _authError = MutableStateFlow<String?>(null)
  val authError: StateFlow<String?> = _authError.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  fun clearError() {
    _authError.value = null
  }

  fun switchRole(role: UserRole) {
    viewModelScope.launch {
      _isLoading.value = true
      authRepository.switchRole(role)
      _isLoading.value = false
    }
  }

  fun login(email: String, pass: String, onSuccess: () -> Unit) {
    viewModelScope.launch {
      _isLoading.value = true
      _authError.value = null
      val result = authRepository.login(email, pass)
      _isLoading.value = false
      if (result.isSuccess) {
        onSuccess()
      } else {
        _authError.value = result.exceptionOrNull()?.message ?: "Login failed"
      }
    }
  }

  fun registerCustomer(
    name: String,
    email: String,
    phone: String,
    cityId: String,
    cityName: String,
    address: String,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      _authError.value = null
      val result = authRepository.registerCustomer(name, email, phone, cityId, cityName, address)
      _isLoading.value = false
      if (result.isSuccess) {
        onSuccess()
      } else {
        _authError.value = result.exceptionOrNull()?.message ?: "Registration failed"
      }
    }
  }

  fun registerProvider(
    businessName: String,
    ownerName: String,
    email: String,
    phone: String,
    cityId: String,
    cityName: String,
    serviceAreas: List<String>,
    categoryIds: List<String>,
    description: String,
    startingPrice: Double,
    experienceYears: Int,
    address: String,
    fssaiNumber: String?,
    drugLicenceNumber: String?,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      _authError.value = null
      val result = authRepository.registerProvider(
        businessName = businessName,
        ownerName = ownerName,
        email = email,
        phone = phone,
        cityId = cityId,
        cityName = cityName,
        serviceAreas = serviceAreas,
        categoryIds = categoryIds,
        description = description,
        startingPrice = startingPrice,
        experienceYears = experienceYears,
        address = address,
        fssaiNumber = fssaiNumber,
        drugLicenceNumber = drugLicenceNumber
      )
      _isLoading.value = false
      if (result.isSuccess) {
        onSuccess()
      } else {
        _authError.value = result.exceptionOrNull()?.message ?: "Provider registration failed"
      }
    }
  }

  fun logout() {
    authRepository.logout()
  }
}
