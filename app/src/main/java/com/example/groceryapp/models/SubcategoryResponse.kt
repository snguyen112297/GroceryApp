package com.example.groceryapp.models

data class Subcategory(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)

data class SubcategoryResponse(
    val count: Int,
    val data: ArrayList<Subcategory>,
    val error: Boolean
)