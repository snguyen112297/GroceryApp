package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.ProductResponse
import com.example.groceryapp.models.SingleProductResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.grid_photos_adapter.view.*

class DetailActivity : AppCompatActivity() {
    var productId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        productId = intent.getStringExtra("productId")
        Log.d("abc", productId.toString())

        init(productId)
    }

    private fun init(productId: String?){
        val request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductByProductId(productId),
            {
                val gson = Gson()
                val product = gson.fromJson(it, SingleProductResponse::class.java)
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

            },
            {

            }
        )
        Volley.newRequestQueue(this).add(request)
    }
}