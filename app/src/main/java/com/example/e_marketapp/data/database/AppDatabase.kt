package com.example.e_marketapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_marketapp.data.dao.ProductDao
import com.example.e_marketapp.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
