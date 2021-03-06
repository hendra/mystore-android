package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class Product(val id: Int,
                   val name : String,
                   val description : String,
                   val color : String,
                   val size : String,

                   @SerializedName("instock")
                   val inStock : String,
                   val price : Int,

                   @SerializedName("created_at")
                   val createdAt : String,

                   @SerializedName("updated_at")
                   val updatedAt : String,

                   val images: ArrayList<Image>)

data class Image(val filename: String,

                  @SerializedName("url")
                  val imageUrl: String)