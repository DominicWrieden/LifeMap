package com.dominicwrieden.data.util

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesUtil(
    private val context: Context
) {

    companion object {
        const val SHARED_PREFERENCES_BVL_CONNECT = "SHARED_PREFERENCES_BVL_CONNECT"
    }

    val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(SHARED_PREFERENCES_BVL_CONNECT,
            Context.MODE_PRIVATE)

    fun retrieveFromSharedPreferences(key: String): String = sharedPreferences.getString(key, "")?:""

    fun saveToSharedPreferences(key: String, value: String): Unit {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun clearInSharedPrefrences(key: String): Unit {
        saveToSharedPreferences(key, "")
    }
}