package com.dominicwrieden.api.implementation.old.authentication.source.local

import android.content.Context
import com.dominicwrieden.api.util.EncryptedSharedPreferencesUtil

internal class AuthenticationSharedPreferences(private val context: Context) {

    companion object {
        private val SHARED_PREFERENCES_TOKEN_KEY = "SHARED_PREFERENCES_OLD_API_TOKEN_KEY"
        private val SHARED_PREFERENCES_PASSWORD = "SHARED_PREFERENCES_OLD_API_PASSWORD"
        private val SHARED_PREFERENCES_USER_NAME = "SHARED_PREFERENCES_USER_NAME"
        private val SHARED_PREFERENCES_FIRST_NAME = "SHARED_PREFERENCES_FIRST_NAME"
        private val SHARED_PREFERENCES_LAST_NAME = "SHARED_PREFERENCES_LAST_NAME"
    }


    fun getToken() =
        EncryptedSharedPreferencesUtil(context).retrieveFromSharedPreferences(
            SHARED_PREFERENCES_TOKEN_KEY
        )

    fun saveToken(token: String) =
        EncryptedSharedPreferencesUtil(context).saveToSharedPreferences(
            SHARED_PREFERENCES_TOKEN_KEY, token
        )

    fun clearToken() {
        EncryptedSharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_TOKEN_KEY)
    }

    fun getPassword() =
        EncryptedSharedPreferencesUtil(context).retrieveFromSharedPreferences(
            SHARED_PREFERENCES_PASSWORD
        )

    fun savePassword(password: String) {
        EncryptedSharedPreferencesUtil(context).saveToSharedPreferences(
            SHARED_PREFERENCES_PASSWORD, password
        )
    }

    fun clearPassword() {
        EncryptedSharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_PASSWORD)
    }

    fun getUserName() =
        EncryptedSharedPreferencesUtil(context).retrieveFromSharedPreferences(
            SHARED_PREFERENCES_USER_NAME
        )

    fun saveUserName(username: String) {
        EncryptedSharedPreferencesUtil(context).saveToSharedPreferences(
            SHARED_PREFERENCES_USER_NAME, username
        )
    }

    fun clearUserName() {
        EncryptedSharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_USER_NAME)
    }

    fun getFirstName() =
        EncryptedSharedPreferencesUtil(context).retrieveFromSharedPreferences(
            SHARED_PREFERENCES_FIRST_NAME
        )

    fun saveFirstName(firstName: String) =
        EncryptedSharedPreferencesUtil(context).saveToSharedPreferences(
            SHARED_PREFERENCES_FIRST_NAME, firstName
        )

    fun clearFirstName() {
        EncryptedSharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_FIRST_NAME)
    }

    fun getLastName() =
        EncryptedSharedPreferencesUtil(context).retrieveFromSharedPreferences(
            SHARED_PREFERENCES_LAST_NAME
        )

    fun saveLastName(lastName: String) =
        EncryptedSharedPreferencesUtil(context).saveToSharedPreferences(
            SHARED_PREFERENCES_LAST_NAME, lastName
        )

    fun clearLastName() {
        EncryptedSharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_LAST_NAME)
    }

}