package com.example.e_marketapp.ui.state

import com.example.e_marketapp.data.model.Product

data class HomeUIState(
    val productList: ArrayList<Product> = arrayListOf(),
    val backupProductList: ArrayList<Product> = arrayListOf(),
    val title: String? = "null",
)
