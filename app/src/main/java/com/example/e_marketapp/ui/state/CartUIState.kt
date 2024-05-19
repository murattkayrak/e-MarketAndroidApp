package com.example.e_marketapp.ui.state

import com.example.e_marketapp.data.model.Product

data class CartUIState(
    val productList: ArrayList<Product> = arrayListOf(),
    val total: String? = null,
)
