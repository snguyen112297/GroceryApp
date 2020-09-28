package com.example.groceryapp.models

data class OrderUser(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
)

data class OrderShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)

data class OrderProduct(
    val _id: String,
    val image: String,
    val mrp: Double,
    val price: Double,
    val productName: String,
    val quantity: Int
)

data class OrderPayment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class OrderOrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class OrderData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderOrderSummary,
    val payment: OrderPayment,
    val products: List<OrderProduct>,
    val shippingAddress: OrderShippingAddress,
    val user: OrderUser,
    val userId: String
)

data class OrdersResponse(
    val count: Int,
    val data: ArrayList<OrderData>,
    val error: Boolean
)