package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.ProductResponse
import com.example.groceryapp.models.SingleProductResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.grid_photos_adapter.view.*

class DetailActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    lateinit var product: SingleProductResponse
    var productId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        productId = intent.getStringExtra("productId")
        Log.d("abc", productId.toString())

        init(productId)
    }

    private fun init(productId: String?){
        dbHelper = DBHelper(this)
        val request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductByProductId(productId),
            {
                val gson = Gson()
                product = gson.fromJson(it, SingleProductResponse::class.java)
                product.data.quantity = 0
                val imageUrl:String = product.data.image
                Picasso.get().load("http://rjtmobile.com/grocery/images/$imageUrl")
                    .resize(320, 320)
                    .centerCrop().placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(details_image_view)
                details_name.text = product.data.productName
                val price = product.data.price.toString() + "$"
                details_price.text = price
                details_description.text = product.data.description


                var dbQuantity = dbHelper.getProductQuantity(product.data._id)
                if (dbQuantity == 0){
                    setVisibility(View.VISIBLE, View.GONE)
                } else {
                    setVisibility(View.GONE, View.VISIBLE)
                    details_cart_number.text = dbQuantity.toString()
                }
            },
            {

            }
        )
        Volley.newRequestQueue(this).add(request)
        button_add.setOnClickListener {
            dbHelper.addProduct(product.data)
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            product.data.quantity = 1
            setVisibility(View.GONE, View.VISIBLE)
            details_cart_number.text = "1"
        }

        button_increase.setOnClickListener {
            dbHelper.updateProduct(product.data, 1)
            product.data.quantity = dbHelper.getProductQuantity(product.data._id)
            details_cart_number.text = product.data.quantity.toString()
        }

        button_decrease.setOnClickListener {
            dbHelper.updateProduct(product.data, 2)
            product.data.quantity = dbHelper.getProductQuantity(product.data._id)
            details_cart_number.text = product.data.quantity.toString()
            if (product.data.quantity == 0){
                dbHelper.deleteProduct(product.data._id)
                setVisibility(View.VISIBLE, View.GONE)
            }
        }

        details_jump_to_cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }
    }

    private fun setVisibility(firstMode: Int, secondMode: Int){
        button_add.visibility = firstMode
        button_decrease.visibility = secondMode
        button_increase.visibility = secondMode
        details_cart_number.visibility = secondMode
    }
}