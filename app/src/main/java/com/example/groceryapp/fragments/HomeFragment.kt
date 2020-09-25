package com.example.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    lateinit var adapterCategory: AdapterCategory
    var mList: ArrayList<Category> = ArrayList()
    var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        getData(view)
        progressBar = view.home_progress_bar
        adapterCategory = AdapterCategory(activity!!)
        view.home_recycler_view.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        view.home_recycler_view.adapter = adapterCategory
    }

    private fun getData(view: View){
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            {
                home_progress_bar?.visibility = View.GONE
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory?.setData(categoryResponse.data)
            },
            {
                it.message?.let { it1 -> Log.d("abc", it1) }
            })
        Volley.newRequestQueue(activity!!).add(request)
    }

}