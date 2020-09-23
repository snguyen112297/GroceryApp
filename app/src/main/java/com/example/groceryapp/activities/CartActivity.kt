package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCartProduct
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), AdapterCartProduct.OnTotalChanged {
    lateinit var dbHelper: DBHelper
    var productList: ArrayList<Product> = ArrayList()
    lateinit var adapterCartProduct: AdapterCartProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
    }

    private fun init(){
        dbHelper = DBHelper(this)
        productList = dbHelper.getProduct()
        adapterCartProduct = AdapterCartProduct(this, productList)
        cart_recycler_view.layoutManager = LinearLayoutManager(this)
        cart_recycler_view.adapter = adapterCartProduct
        var toolbar = toolbar
        toolbar.title = "Cart"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        displayTotal()
    }

    private fun displayTotal(){
        if (dbHelper.getSize() == 0){
            layout_cart_total.visibility = View.GONE
            cart_checkout_button.visibility = View.GONE
        } else {
            layout_cart_total.visibility = View.VISIBLE
            cart_checkout_button.visibility = View.VISIBLE
        }
        var subTotal = dbHelper.getTotal().toString()
        cart_sub_total.text = subTotal
        var discount = cart_discount.text.toString().toDouble()
        var total = subTotal.toDouble()-discount
        cart_total.text = total.toString()
    }

    private fun refreshTextViews(){
        cart_total.refreshDrawableState()
        cart_sub_total.refreshDrawableState()
        cart_discount.refreshDrawableState()
    }

    override fun onRestart(){
        productList.clear()
        productList.addAll(dbHelper.getProduct())
        adapterCartProduct.notifyDataSetChanged()
        displayTotal()
        refreshTextViews()
        super.onRestart()
    }

    override fun totalChanged(){
        displayTotal()
        refreshTextViews()
    }
}
