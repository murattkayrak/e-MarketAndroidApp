package com.example.e_marketapp.data.domain

import com.example.e_marketapp.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getProduct(): Response<ArrayList<Product>>
}