package com.example.groceryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONObject

class LoginFragment : Fragment() {

    var listener: OnFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        view.login_button.setOnClickListener {
            /*
            var email = login_edit_email.text.toString()
            var password = login_edit_password.text.toString()

            var params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password

            var jsonObject = JSONObject(params as Map<*, *>)

            var url = "https://grocery-second-app.herokuapp.com/api/auth/register"

            var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                {
                    Toast.makeText(view.context, "registered", Toast.LENGTH_SHORT).show()
                },
                {
                }
            )
            Volley.newRequestQueue(view.context).add(request)
            */
            listener?.onButtonClicked("category_display_fragment")
        }

        view.login_register.setOnClickListener {
            listener?.onButtonClicked("register_fragment")
            this.onDestroy()
            this.onDetach()
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