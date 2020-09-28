package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {
    lateinit var dbHelper:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init(){
        val toolbar = toolbar
        toolbar.title = "Checkout"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        dbHelper = DBHelper(this)
        var subTotal = dbHelper.getTotal().toString()
        payment_sub_total.text = subTotal
        var discount = 0.0
        var total = subTotal.toDouble()-discount
        payment_display_total.text = total.toString()
        payment_order_amount.text = total.toString()

        var paymentResponse = intent.getSerializableExtra(PaymentResponse.KEY_PAYMENT) as PaymentResponse

        // Calculate delivery charges
        val totalNumberOfItems = dbHelper.getNumberOfItems()
        if (totalNumberOfItems > 5) {
            val deliveryCharge = "\$" + "0"
            payment_delivery.text = deliveryCharge
            paymentResponse.orderSummary.deliveryCharges = 0
        } else {
            val deliveryCharge = "\$"+(3*dbHelper.getNumberOfItems()).toString()
            payment_delivery.text = deliveryCharge
            paymentResponse.orderSummary.deliveryCharges = 3*dbHelper.getNumberOfItems()
        }

        paymentResponse.orderSummary.discount = discount
        paymentResponse.orderSummary.orderAmount = subTotal.toDouble()
        paymentResponse.orderSummary.ourPrice = total
        paymentResponse.orderSummary.totalAmount = total + paymentResponse.orderSummary.deliveryCharges.toDouble()

        // Payment buttons
        payment_online.setOnClickListener {
            processPayment(paymentResponse, 1)
        }

        payment_cash_on_delivery.setOnClickListener {
            processPayment(paymentResponse, 2)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun processPayment(paymentResponse: PaymentResponse, mode: Int){
        when (mode) {
            1 -> paymentResponse.payment.paymentMode = "online"
            2 -> paymentResponse.payment.paymentMode = "cash"
        }
        paymentResponse.payment.paymentStatus = "completed"

        var gson = Gson()
        gson.toJson(paymentResponse)

        var jsonString = gson.toJson(paymentResponse)//JSONObject(params as Map<*, *>)
        var jsonObject = JSONObject(jsonString)//JSONObject(params as Map<*, *>)
        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postOrders(),
            jsonObject,
            {
                Log.d("TAG", paymentResponse.toString())
                Toast.makeText(this, "Order submitted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OrderSubmission::class.java))
            },
            {
                Toast.makeText(this, "Order not submitted", Toast.LENGTH_SHORT).show()
                Log.d("TAG", paymentResponse.toString())
            }
        )
        Volley.newRequestQueue(this).add(request)
    }
}