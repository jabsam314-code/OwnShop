package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.ApprovalStatus
import com.example.data.model.RequestStatus
import com.example.data.model.UserRole

class Converters {
  @TypeConverter
  fun fromStringList(value: List<String>?): String {
    return value?.joinToString(separator = "||") ?: ""
  }

  @TypeConverter
  fun toStringList(value: String?): List<String> {
    if (value.isNullOrEmpty()) return emptyList()
    return value.split("||").filter { it.isNotBlank() }
  }

  @TypeConverter
  fun fromUserRole(role: UserRole?): String {
    return role?.name ?: UserRole.CUSTOMER.name
  }

  @TypeConverter
  fun toUserRole(value: String?): UserRole {
    return try {
      if (value != null) UserRole.valueOf(value) else UserRole.CUSTOMER
    } catch (e: Exception) {
      UserRole.CUSTOMER
    }
  }

  @TypeConverter
  fun fromApprovalStatus(status: ApprovalStatus?): String {
    return status?.name ?: ApprovalStatus.PENDING.name
  }

  @TypeConverter
  fun toApprovalStatus(value: String?): ApprovalStatus {
    return try {
      if (value != null) ApprovalStatus.valueOf(value) else ApprovalStatus.PENDING
    } catch (e: Exception) {
      ApprovalStatus.PENDING
    }
  }

  @TypeConverter
  fun fromRequestStatus(status: RequestStatus?): String {
    return status?.name ?: RequestStatus.PENDING.name
  }

  @TypeConverter
  fun toRequestStatus(value: String?): RequestStatus {
    return try {
      if (value != null) RequestStatus.valueOf(value) else RequestStatus.PENDING
    } catch (e: Exception) {
      RequestStatus.PENDING
    }
  }
}
