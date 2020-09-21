package com.example.groceryapp.models

data class Category(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
)

data class CategoryResponse(
    val count: Int,
    val data: ArrayList<Category>,
    val error: Boolean
)
