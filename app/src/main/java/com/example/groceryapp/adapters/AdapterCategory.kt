package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.SubCategoryActivity
import com.example.groceryapp.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_photos_adapter.view.*

class AdapterCategory(var context: Context): RecyclerView.Adapter<AdapterCategory.ViewHolder>(){

    private var mList: ArrayList<Category> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.grid_photos_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterCategory.ViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    fun setData(l: ArrayList<Category>){
        mList = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            val imageUrl:String = category.catImage
            Picasso.get().load("http://rjtmobile.com/grocery/images/$imageUrl")
                .resize(320, 320)
                .centerCrop().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.image_view)
            itemView.text_view.text = category.catName
            itemView.setOnClickListener {
                var intent = Intent(context, SubCategoryActivity::class.java)
                intent.putExtra(Category.KEY_CATEGORY, category)
                context.startActivity(intent)
            }
        }
    }
}