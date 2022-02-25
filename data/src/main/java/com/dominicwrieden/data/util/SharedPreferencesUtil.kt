package com.dominicwrieden.data.util

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesUtil(context: Context) {

    companion object {
        const val SHARED_PREFERENCES_LIFE_MAP_DATA = "SHARED_PREFERENCES_LIFE_MAP_DATA"
    }

    private val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            SHARED_PREFERENCES_LIFE_MAP_DATA,
            Context.MODE_PRIVATE
        )

    fun retrieveFromSharedPreferences(key: String): String =
        sharedPreferences.getString(key, "") ?: ""

    fun saveToSharedPreferences(key: String, value: String): Unit {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun clearInSharedPreferences(key: String): Unit {
        saveToSharedPreferences(key, "")
    }
}