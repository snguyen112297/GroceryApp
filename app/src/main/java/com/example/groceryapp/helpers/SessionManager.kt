package com.example.groceryapp.helpers

import android.content.Context
import android.util.Log
import com.example.groceryapp.models.User

class SessionManager(var mContext: Context) {
    private val NAME = "registered_user"
    private val KEY_TOKEN = "token"
    private val KEY_NAME = "firstName"
    private val KEY_ID = "_id"
    private val KEY_MOBILE = "mobile"
    private val KEY_EMAIL = "email"

    var sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUser(user: User) {
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_NAME, user.firstName)
        editor.putString(KEY_MOBILE, user.mobile)
        editor.putString(KEY_EMAIL, user.email)
        editor.commit()

    }

    fun saveToken(token: String){
        editor.putString(KEY_TOKEN, token)
        editor.commit()
    }

    fun getUserId(): String{
        return sharedPreferences.getString(KEY_ID, null).toString()
    }

    fun getUserFirstName(): String{
        return sharedPreferences.getString(KEY_NAME, null).toString()
    }

    fun getUserEmail(): String{
        return sharedPreferences.getString(KEY_EMAIL, null).toString()
    }

    fun getUserMobile(): String{
        return sharedPreferences.getString(KEY_MOBILE, null).toString()
    }


    fun getQuickLogin(): Boolean{
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token != null
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }
}