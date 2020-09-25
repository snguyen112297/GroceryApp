package com.example.groceryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.groceryapp.R
import com.example.groceryapp.activities.MainActivity
import com.example.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.fragment_login_register.view.*

class LoginRegisterFragment : Fragment() {

    var listener: OnFragmentInteraction? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login_register, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        view.main_login_button.setOnClickListener {
            listener?.onButtonClicked("login_fragment")
            onDestroy()
            onDetach()
        }

        view.main_register_button.setOnClickListener {
            listener?.onButtonClicked("register_fragment")
            onDestroy()
            onDetach()
        }
    }
    interface OnFragmentInteraction{
        fun onButtonClicked(name: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MainActivity
    }

}