package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.Category
import com.example.data.model.City
import com.example.data.model.Provider
import com.example.data.model.Review
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount

@Database(
  entities = [
    City::class,
    Category::class,
    Provider::class,
    ServiceItem::class,
    ServiceRequest::class,
    Review::class,
    UserAccount::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OwnShopDatabase : RoomDatabase() {

  abstract fun cityDao(): CityDao
  abstract fun categoryDao(): CategoryDao
  abstract fun providerDao(): ProviderDao
  abstract fun serviceDao(): ServiceDao
  abstract fun requestDao(): RequestDao
  abstract fun reviewDao(): ReviewDao
  abstract fun userDao(): UserDao

  companion object {
    @Volatile
    private var INSTANCE: OwnShopDatabase? = null

    fun getDatabase(context: Context): OwnShopDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          OwnShopDatabase::class.java,
          "ownshop_database"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
