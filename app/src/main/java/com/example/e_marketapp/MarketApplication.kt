package com.example.e_marketapp

import android.app.Application
import androidx.room.Room
import com.example.e_marketapp.data.database.AppDatabase

class MarketApplication: Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database.db"
        ).build()
    }
}