package com.example.groceryapp.models

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: User
)