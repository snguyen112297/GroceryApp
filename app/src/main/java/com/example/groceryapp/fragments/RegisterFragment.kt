package com.example.groceryapp.fragments

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
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.json.JSONObject

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        view.register_button.setOnClickListener {
            var firstName = register_edit_name.text.toString()
            var email = register_edit_email.text.toString()
            var password = register_edit_password.text.toString()
            var confirmPassword = register_edit_confirm_password.text.toString()
            var mobile = register_edit_phone.text.toString()

            if (password == confirmPassword) {
                var params = HashMap<String, String>()
                params["firstName"] = firstName
                params["email"] = email
                params["password"] = password
                params["mobile"] = mobile

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
            }
        }
    }
}