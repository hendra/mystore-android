package com.example.myapplication.Services

import com.example.myapplication.Models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("stores/86/products")
    fun getProducts(): Call<List<Product>>
}