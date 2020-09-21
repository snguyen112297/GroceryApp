package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterSubcategory
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Subcategory
import com.example.groceryapp.models.SubcategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {
    private lateinit var adapterSubcategory: AdapterSubcategory
    var mList: ArrayList<Subcategory> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        init()
    }

    private fun init(){
        val catId: Int = intent.getIntExtra("catId", 1)
        adapterSubcategory = AdapterSubcategory(supportFragmentManager)
        getData(catId)
    }

    private fun getData(catId: Int){
        val request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catId),
            {

                var gson = Gson()
                var subcategoryResponse = gson.fromJson(it, SubcategoryResponse::class.java)
                mList.addAll(subcategoryResponse.data)
                for (i in 0 until mList.size){
                    adapterSubcategory.addFragment(mList[i])
                }
                view_pager.adapter = adapterSubcategory
                tab_layout.setupWithViewPager(view_pager)
            },
            {
                it.message?.let { it1 -> Log.d("abc", it1) }
            })
        Volley.newRequestQueue(this).add(request)
    }
}