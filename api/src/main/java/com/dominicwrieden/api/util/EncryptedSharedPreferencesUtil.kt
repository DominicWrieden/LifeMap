package com.dominicwrieden.api.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPreferencesUtil(
    context: Context
) {

    companion object {
        private const val SHARED_PREFERENCES_LIFE_MAP_API = "SHARED_PREFERENCES_LIFE_MAP_API"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        SHARED_PREFERENCES_LIFE_MAP_API,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
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