package com.example.groceryapp.models

data class Address(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
)

data class AddressResponse(
    val count: Int,
    val data: ArrayList<Address>,
    val error: Boolean
)