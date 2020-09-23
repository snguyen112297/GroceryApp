package com.example.groceryapp.models

data class Product(
    var quantity: Int = 0,
    var description: String,
    val status: Boolean,
    val position: Int,
    val created: String,
    val _id: String,
    val catId: Int,
    val subId: Int,
    val productName: String,
    val image: String,
    val unit: String,
    val price: Double,
    val mrp: Double,
    val __v: Int
)

data class ProductResponse(
    val count: Int,
    val data: ArrayList<Product>,
    val error: Boolean
)