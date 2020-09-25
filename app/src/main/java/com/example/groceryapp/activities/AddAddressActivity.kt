package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterAddress
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Add address"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        add_address_submit_button.setOnClickListener {
            var pinCode = add_address_pincode.text.toString()
            var streetName = add_address_street.text.toString()
            var type = add_address_type.text.toString()
            var houseNo = add_address_number.text.toString()
            var city = add_address_city.text.toString()

            sessionManager = SessionManager(this)

            var userId = sessionManager.getUserId()

            var params = HashMap<String, String>()
            params["pincode"] = pinCode
            params["streetName"] = streetName
            params["city"] = city
            params["houseNo"] = houseNo
            params["type"] = type
            params["userId"] = userId

            var jsonObject = JSONObject(params as Map<*, *>)

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.saveAddress(),
                jsonObject,
                {
                    Toast.makeText(this, "New address submitted", Toast.LENGTH_SHORT).show()
                },
                {
                }
            )
            Volley.newRequestQueue(this).add(request)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}