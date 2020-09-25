package com.example.groceryapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.example.groceryapp.R
import com.example.groceryapp.fragments.CategoryDisplayFragment
import com.example.groceryapp.fragments.LoginFragment
import com.example.groceryapp.fragments.LoginRegisterFragment
import com.example.groceryapp.fragments.RegisterFragment
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.User
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.view.*

class MainActivity : AppCompatActivity(),
    LoginRegisterFragment.OnFragmentInteraction,
    LoginFragment.OnFragmentInteraction, NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var user: User? = null

    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Grocery"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        setupToolbar()
        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        sessionManager = SessionManager(this)
        Log.d("TAG", sessionManager.getQuickLogin().toString())
        if (sessionManager.getQuickLogin()) {
            fragmentTransaction.add(R.id.main_fragment_container, CategoryDisplayFragment()).commit()
        } else {
            var loginRegisterFragment = LoginRegisterFragment()
            fragmentTransaction.add(R.id.main_fragment_container, loginRegisterFragment).commit()
        }


        // Navigation Bar
        drawerLayout = main_drawer_layout
        navView = main_navigation_view
        if (sessionManager.getQuickLogin()){
           user = User(0, sessionManager.getUserId(), "", sessionManager.getUserEmail(), sessionManager.getUserFirstName(), sessionManager.getUserMobile(), "")
            Log.d("TAG", user.toString())
        } else {
            user = User(0, "0", "", "", "User", "", "")
        }
        var headerView = navView.getHeaderView(0)
        headerView.navigation_header_name.text = user!!.firstName
        headerView.navigation_header_email.text = user!!.email
        headerView.navigation_header_mobile.text = user!!.mobile

        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigation_menu_item_account -> Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
            R.id.navigation_menu_item_orders -> Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show()
            R.id.navigation_menu_item_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            R.id.navigation_menu_item_logout -> dialogueLogout()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                var intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
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

    override fun onLoggedIn(user: User) {
        var headerView = main_navigation_view.getHeaderView(0)
        headerView.navigation_header_name.text = user.firstName
        headerView.navigation_header_email.text = user.email
        headerView.navigation_header_mobile.text = user.mobile
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun dialogueLogout(){
        var builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes", object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                sessionManager.logout()
                var loginRegisterFragment = LoginRegisterFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, loginRegisterFragment).commit()
                var emptyUser = User(0, "0", "", "", "User", "", "")
                var headerView = navView.getHeaderView(0)
                headerView.navigation_header_name.text = emptyUser!!.firstName
                headerView.navigation_header_email.text = emptyUser!!.email
                headerView.navigation_header_mobile.text = emptyUser!!.mobile
            }
        })

        builder.setNegativeButton("No", object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        var alertDialog = builder.create()
        alertDialog.show()
    }

}