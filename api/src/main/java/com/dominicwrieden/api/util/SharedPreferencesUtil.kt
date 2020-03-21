package com.dominicwrieden.api.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(
    private val context: Context
) {

    companion object {
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES_LIFE_MAP"
    }

    val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun retrieveFromSharedPreferences(key: String): String =
        sharedPreferences.getString(key, "") ?: ""

    fun saveToSharedPreferences(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun clearInSharedPreferences(key: String) {
        saveToSharedPreferences(key, "")
    }
}