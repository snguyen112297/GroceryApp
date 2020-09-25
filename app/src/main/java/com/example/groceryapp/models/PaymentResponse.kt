package com.example.groceryapp.models

import java.io.Serializable

data class OrderSummary(
    var deliveryCharges: Int,
    var discount: Double,
    var orderAmount: Double,
    var ourPrice: Double,
    var totalAmount: Double
): Serializable

data class Payment(
    var paymentMode: String,
    var paymentStatus: String
): Serializable

data class PaymentProduct(
    var _id: String,
    var image: String,
    var mrp: Double,
    var price: Double,
    var quantity: Int
): Serializable

data class ShippingAddress(
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String
): Serializable

data class PaymentUser(
    var _id: String,
    var email: String,
    var mobile: String,
    var name: String
): Serializable

data class PaymentResponse(
    var date: String,
    var orderStatus: String,
    var orderSummary: OrderSummary,
    var payment: Payment,
    var products: ArrayList<PaymentProduct>,
    var shippingAddress: ShippingAddress,
    var user: PaymentUser,
    var userId: String
): Serializable {
    companion object{
        const val KEY_PAYMENT = "payment_object"
    }
}