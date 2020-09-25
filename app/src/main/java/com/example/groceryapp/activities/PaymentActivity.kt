package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_payment.*

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


        // Calculate delivery charges
        val totalNumberOfItems = dbHelper.getNumberOfItems()
        if (totalNumberOfItems > 5) {
            val deliveryCharge = "\$" + "0"
            payment_delivery.text = deliveryCharge
        } else {
            val deliveryCharge = "\$"+(3*dbHelper.getNumberOfItems()).toString()
            payment_delivery.text = deliveryCharge
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}