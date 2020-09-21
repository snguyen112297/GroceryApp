package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.groceryapp.fragments.LoginFragment
import com.example.groceryapp.R
import com.example.groceryapp.fragments.CategoryDisplayFragment
import com.example.groceryapp.fragments.LoginRegisterFragment
import com.example.groceryapp.fragments.RegisterFragment

class MainActivity : AppCompatActivity(),
    LoginRegisterFragment.OnFragmentInteraction,
    LoginFragment.OnFragmentInteraction{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        var loginRegisterFragment = LoginRegisterFragment()
        fragmentTransaction.add(R.id.main_fragment_container, loginRegisterFragment).commit()
    }

    override fun onButtonClicked(fragment_name: String) {
        when (fragment_name){
            "login_fragment" -> {
                var loginFragment = LoginFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, loginFragment).commit()
            }
            "register_fragment" -> {
                var registerFragment = RegisterFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, registerFragment).commit()
            }
            "category_display_fragment" -> {
                var categoryDisplayFragment = CategoryDisplayFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, categoryDisplayFragment).commit()
            }
        }
    }
}