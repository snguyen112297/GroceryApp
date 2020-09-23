package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.CartActivity
import com.example.groceryapp.activities.DetailActivity
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_product_adapter.view.*
import kotlinx.android.synthetic.main.grid_product_adapter.view.*

class AdapterCartProduct(private var mContext: Context, private var mList: ArrayList<Product>): RecyclerView.Adapter<AdapterCartProduct.MyViewHolder>(){
    val listener = mContext as CartActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.cart_product_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int{
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    interface OnTotalChanged{
        fun totalChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(product: Product){
            var dbHelper = DBHelper(itemView.context)
            val imageUrl:String = product.image
            Picasso.get().load("http://rjtmobile.com/grocery/images/$imageUrl")
                .resize(320, 320)
                .centerCrop().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.cart_product_adapter_image)

            itemView.cart_product_adapter_name.text = product.productName

            var price = product.price.toString()
            var priceText = "Price: $price"

            itemView.cart_product_adapter_price.text = priceText

            var quantity = product.quantity.toString()
            var quantityText = "Quantity: $quantity"
            itemView.cart_product_adapter_quantity.text = quantityText
            itemView.setOnClickListener{
                var intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("productId", product._id)
                itemView.context.startActivity(intent)
            }
            itemView.cart_product_adapter_increase.setOnClickListener {
                dbHelper.updateProduct(product, 1)
                product.quantity += 1
                notifyDataSetChanged()
                listener.totalChanged()
            }
            itemView.cart_product_adapter_remove.setOnClickListener {
                dbHelper.deleteProduct(product._id)
                mList.removeAt(position)
                notifyDataSetChanged()
                listener.totalChanged()
            }
            itemView.cart_product_adapter_decrease.setOnClickListener {
                dbHelper.updateProduct(product, 2)
                product.quantity -= 1
                if (product.quantity == 0){
                    mList.removeAt(position)
                }
                notifyDataSetChanged()
                listener.totalChanged()
            }
        }
    }
}