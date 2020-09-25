package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCartProduct
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.*
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_cart.*
import java.io.Serializable

class CartActivity : AppCompatActivity(), AdapterCartProduct.OnTotalChanged, AdapterCartProduct.OnFinish {
    lateinit var dbHelper: DBHelper
    var productList: ArrayList<Product> = ArrayList()
    lateinit var adapterCartProduct: AdapterCartProduct
    lateinit var paymentResponse: PaymentResponse
    lateinit var sessionManager: SessionManager

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

        cart_checkout_button.setOnClickListener {
            var intent = Intent(this, AddressActivity::class.java)
            var paymentProductList:ArrayList<PaymentProduct> = ArrayList()
            for (i in 0 until productList.size){
                val productId = productList[i]._id
                val productImage = productList[i].image
                val productMrp = productList[i].mrp
                val productPrice = productList[i].price
                val productQuantity = productList[i].quantity
                var paymentProduct = PaymentProduct(productId, productImage, productMrp, productPrice, productQuantity)
                paymentProductList.add(paymentProduct)
            }
            sessionManager = SessionManager(this)
            val __v: Int = 0
            val _id: String = ""
            val date: String = ""
            val orderStatus: String = ""
            val orderSummary: OrderSummary = OrderSummary(
                0,
                0.0,
                0.0,
                0.0,
                0.0)
            val payment: Payment = Payment("",
                "")
            val products: ArrayList<PaymentProduct> = paymentProductList
            val shippingAddress: ShippingAddress = ShippingAddress("",
                "",
                -1,
                "")
            val user: PaymentUser = PaymentUser(sessionManager.getUserId(),
                sessionManager.getUserEmail(),
                sessionManager.getUserMobile(),
                sessionManager.getUserFirstName())
            val userId: String = sessionManager.getUserId()

            paymentResponse = PaymentResponse(date,
                orderStatus, orderSummary, payment,
                products, shippingAddress, user, userId)
            intent.putExtra(PaymentResponse.KEY_PAYMENT, paymentResponse as Serializable)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
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

    override fun cartFinish(){
        this.finish()
    }
}
