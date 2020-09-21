package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.DetailActivity
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_product_adapter.view.*

class AdapterProduct(private var mContext: Context): RecyclerView.Adapter<AdapterProduct.MyViewHolder>(){

    var mList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.grid_product_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int{
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    fun setData(l: ArrayList<Product>){
        mList = l
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(product: Product){

            val imageUrl:String = product.image
            Picasso.get().load("http://rjtmobile.com/grocery/images/$imageUrl")
                .resize(320, 320)
                .centerCrop().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.row_adapter_product_image)

            itemView.row_adapter_product_name.text = product.productName
            itemView.row_adapter_product_price.text = product.price.toString()

            itemView.setOnClickListener{
                var intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("productId", product._id)
                itemView.context.startActivity(intent)
            }
        }
    }
}