package com.example.calc.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object AuthUtils {
    private const val PREFS_NAME = "AuthPrefs"
    private const val TOKEN_KEY = "token"

    fun storeToken(context: Context, token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("authToken").apply()
        Log.d("AuthUtils", "Token cleared from SharedPreferences")
    }

}
