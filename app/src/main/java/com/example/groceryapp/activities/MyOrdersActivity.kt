package com.example.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterOrders
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.AddressResponse
import com.example.groceryapp.models.OrdersResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_my_orders.*

class MyOrdersActivity : AppCompatActivity() {
    private lateinit var adapterOrders: AdapterOrders
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        init()
    }

    private fun init(){
        val toolbar = toolbar
        toolbar.title = "Order History"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getData()
        adapterOrders = AdapterOrders(this)
        orders_recycler_view.layoutManager = LinearLayoutManager(this)
        orders_recycler_view.adapter = adapterOrders
        orders_home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun getData(){
        sessionManager = SessionManager(this)
        val userId = sessionManager.getUserId()
        val request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrderByUserId(userId),
            {
                var gson = Gson()
                var orderResponse = gson.fromJson(it, OrdersResponse::class.java)
                Log.d("TAG", orderResponse.toString())
                adapterOrders?.setData(orderResponse.data)
            },
            {
                it.message?.let { it1 -> Log.d("abc", it1) }
            })
        Volley.newRequestQueue(this).add(request)
    }
}