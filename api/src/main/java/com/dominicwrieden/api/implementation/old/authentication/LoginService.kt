package com.dominicwrieden.api.implementation.old.authentication

import android.content.Context
import com.dominicwrieden.api.implementation.old.authentication.retrofit.LoginApi
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.evaluateErrorResponse
import com.dominicwrieden.api.model.evaluateResponse
import com.dominicwrieden.api.util.SharedPreferencesUtil
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LoginService(private val loginApi: LoginApi, val context: Context) {

    // TODO bessere Lösung finden
    companion object {
        private val SHARED_PREFERENCES_TOKEN_KEY = "SHARED_PREFERENCES_OLD_API_TOKEN_KEY"

        fun getToken(context: Context) =
            SharedPreferencesUtil(context).retrieveFromSharedPreferences(
                SHARED_PREFERENCES_TOKEN_KEY
            )

        fun saveToken(context: Context, token: String) =
            SharedPreferencesUtil(context).saveToSharedPreferences(
                SHARED_PREFERENCES_TOKEN_KEY, token
            )

        fun clearToken(context: Context) {
            SharedPreferencesUtil(context).clearInSharedPreferences(SHARED_PREFERENCES_TOKEN_KEY)
        }
    }


    //TODO was Besseres zurück geben als einfach nur Reponse<Nothing>?
    fun login(username: String, password: String): Single<Response<Nothing>> =
        loginApi.login(username, password)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {loginResponse ->
                if (loginResponse.isSuccessful && loginResponse.body() != null && !loginResponse.body()!!.token.isBlank()) {
                    saveToken(context, loginResponse.body()!!.token)
                }
            }
            .map {
                evaluateResponse(it){} as Response<Nothing>
            }
            .onErrorReturn { evaluateErrorResponse(it) }

    fun logout() {
        clearToken(context)
    }
}