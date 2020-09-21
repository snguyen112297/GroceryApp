package com.example.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_category_display.view.*

class CategoryDisplayFragment : Fragment() {
    var adapterCategory: AdapterCategory? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_category_display, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        getData(view)
        adapterCategory = AdapterCategory(view.context)
        view.category_recycler_view.layoutManager = GridLayoutManager(view.context, 3)
        view.category_recycler_view.adapter = adapterCategory
    }

    private fun getData(view: View){
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory?.setData(categoryResponse.data)
            },
            {
                it.message?.let { it1 -> Log.d("abc", it1) }
            })
        Volley.newRequestQueue(view.context).add(request)
    }
}