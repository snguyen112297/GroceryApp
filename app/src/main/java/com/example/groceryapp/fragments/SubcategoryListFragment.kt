package com.example.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterProduct
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.ProductResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_subcategory_list.view.*

private const val ARG_PARAM1 = "param1"

class SubcategoryListFragment : Fragment() {
    var productList: ArrayList<Product> = ArrayList()
    private lateinit var adapterProduct: AdapterProduct
    private var subId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_subcategory_list, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        adapterProduct = AdapterProduct(view.context)
        generateData(view, subId)
        view.sub_recycler_view.layoutManager = GridLayoutManager(view.context, 2)
        view.sub_recycler_view.adapter = adapterProduct
    }

    private fun generateData(view: View, subId: Int){
        productList.clear()
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(subId),
            {
                var gson = Gson()
                var productResponse = gson.fromJson(it, ProductResponse::class.java)
                Log.d("ProductResponse", productResponse.toString())
                adapterProduct.setData(productResponse.data)
            },
            {

            }
        )
        Volley.newRequestQueue(activity).add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(subId: Int) =
            SubcategoryListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, subId)
                }
            }
    }
}