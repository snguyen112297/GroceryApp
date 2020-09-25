package com.example.groceryapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.LoginResponse
import com.example.groceryapp.models.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import org.json.JSONObject

class LoginFragment : Fragment() {

    var listener: OnFragmentInteraction? = null

    lateinit var  sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        sessionManager = SessionManager(view.context)
        init(view)
        return view
    }

    private fun init(view: View){
        view.login_button.setOnClickListener {
            var email = login_edit_email.text.toString()
            var password = login_edit_password.text.toString()

            var params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password

            var jsonObject = JSONObject(params as Map<*, *>)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                jsonObject,
                {
                    val gson = Gson()
                    var loginResponse = gson.fromJson(it.toString(), LoginResponse::class.java)
                    sessionManager.saveUser(loginResponse.user)
                    sessionManager.saveToken(loginResponse.token)
                    Log.d("TAG", loginResponse.user.toString())
                    Toast.makeText(view.context, "Logged In", Toast.LENGTH_SHORT).show()
                    listener?.onLoggedIn(loginResponse.user)
                    listener?.onButtonClicked("category_display_fragment")
                },
                {
                }
            )
            Volley.newRequestQueue(view.context).add(request)
        }

        view.login_register.setOnClickListener {
            listener?.onButtonClicked("register_fragment")
            this.onDestroy()
            this.onDetach()
        }
    }


    interface OnFragmentInteraction{
        fun onButtonClicked(name: String)
        fun onLoggedIn(user: User)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MainActivity
    }
}