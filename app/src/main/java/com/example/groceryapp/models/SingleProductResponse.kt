package com.example.groceryapp.models

data class SingleProductResponse(
    var data: Product,
    val error: Boolean
)