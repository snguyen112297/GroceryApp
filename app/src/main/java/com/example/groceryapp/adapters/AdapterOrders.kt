package com.example.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.models.OrderData
import kotlinx.android.synthetic.main.linear_order_adapter.view.*

class AdapterOrders(var context: Context): RecyclerView.Adapter<AdapterOrders.ViewHolder>(){
    private var mList: ArrayList<OrderData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.linear_order_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterOrders.ViewHolder, position: Int) {
        var order = mList[position]
        holder.bind(order)
    }

    fun setData(l: ArrayList<OrderData>){
        mList = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(orderData: OrderData){
            val orderId = "Order ID: " + orderData._id
            itemView.order_id.text = orderId
            val orderDate = "Order date: " + orderData.date
            itemView.order_date.text = orderDate
            val orderShipping = "Shipping: " + orderData.shippingAddress.city
            itemView.order_shipping.text = orderShipping
            val orderStatus = "Status: " + orderData.orderStatus
            itemView.order_status.text = orderStatus
            val orderSummary = "Summary: " + orderData.orderSummary.totalAmount.toString()
            itemView.order_summary.text = orderSummary
            val orderUser = "User: " + orderData.user.name
            itemView.order_user.text = orderUser

        }
    }
}