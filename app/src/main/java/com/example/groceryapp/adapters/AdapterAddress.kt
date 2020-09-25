package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.PaymentActivity
import com.example.groceryapp.models.Address
import com.example.groceryapp.models.PaymentResponse
import com.example.groceryapp.models.ShippingAddress
import kotlinx.android.synthetic.main.linear_address_adapter.view.*
import java.io.Serializable

class AdapterAddress(var context: Context): RecyclerView.Adapter<AdapterAddress.ViewHolder>(){
    lateinit var paymentResponse: PaymentResponse
    private var mList: ArrayList<Address> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.linear_address_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterAddress.ViewHolder, position: Int) {
        var address = mList[position]
        holder.bind(address)
    }

    fun setData(l: ArrayList<Address>){
        mList = l
        notifyDataSetChanged()
    }

    fun setPayment(p: PaymentResponse)
    {
        paymentResponse = p
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(address: Address){
            var city = address.city
            var cityText = "City: $city"
            itemView.address_city.text = cityText


            var houseNo = address.houseNo
            var houseNoText = "No.: $houseNo"
            itemView.address_house_number.text = houseNoText


            var streetName = address.streetName
            var streetNameText = "Street: $streetName"
            itemView.address_street_name.text = streetNameText


            var type = address.type
            var typeText = "Type: $type"
            itemView.address_type.text = typeText


            var pinCode = address.pincode
            var pinCodeText = "Pin code: $pinCode"
            itemView.address_pincode.text = pinCodeText

            itemView.setOnClickListener{
                var shippingAddress = ShippingAddress(city, houseNo, pinCode, streetName)
                paymentResponse.shippingAddress = shippingAddress
                var intent = Intent(itemView.context, PaymentActivity::class.java)
                intent.putExtra(PaymentResponse.KEY_PAYMENT, paymentResponse as Serializable)
                itemView.context.startActivity(intent)
            }
        }
    }
}