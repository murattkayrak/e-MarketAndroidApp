package com.example.e_marketapp.data.repository

import com.example.e_marketapp.data.domain.ApiService
import com.example.e_marketapp.data.model.Product

class ProductRepository(
    private val apiService: ApiService
) {
    suspend fun getProduct(): ArrayList<Product> {
        return (apiService.getProduct().body() ?: emptyList()) as ArrayList<Product>
    }
}
