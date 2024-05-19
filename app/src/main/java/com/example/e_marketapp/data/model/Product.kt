package com.example.e_marketapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    val id: String?,
    val brand: String?,
    val model: String?,
    val description: String?,
    val price: String?,
    val image: String?,
    val name: String?,
    val createdAt: String?,
)
