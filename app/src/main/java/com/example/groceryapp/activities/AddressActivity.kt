package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterAddress
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.AddressResponse
import com.example.groceryapp.models.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.fragment_category_display.view.*

class AddressActivity : AppCompatActivity() {
    private lateinit var adapterAddress: AdapterAddress
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Address"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        address_add_new_button.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        getData()

        adapterAddress = AdapterAddress(this)
        address_recycler_view.layoutManager = LinearLayoutManager(this)
        address_recycler_view.adapter = adapterAddress
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
            Endpoints.getAddressByUserId(userId),
            {
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                adapterAddress?.setData(addressResponse.data)
            },
            {
                it.message?.let { it1 -> Log.d("abc", it1) }
            })
        Volley.newRequestQueue(this).add(request)
    }

    override fun onRestart(){
        getData()
        adapterAddress.notifyDataSetChanged()
        super.onRestart()
    }

}