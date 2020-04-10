package com.dominicwrieden.api.implementation.old.authentication

import com.dominicwrieden.api.implementation.old.authentication.model.toUser
import com.dominicwrieden.api.implementation.old.authentication.source.retrofit.AuthenticationApi
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.User
import com.dominicwrieden.api.model.evaluateErrorResponse
import com.dominicwrieden.api.model.evaluateResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class AuthenticationService(
    private val authenticationApi: AuthenticationApi,
    private val authenticationManager: AuthenticationManager
) {


    fun login(username: String, password: String): Single<Response<User>> =
        authenticationApi.login(username, password)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { loginResponse ->
                if (loginResponse.isSuccessful && loginResponse.body() != null
                    && !loginResponse.body()!!.token.isBlank()) {
                    
                    loginResponse.body()?.run {
                        authenticationManager.login(
                            username = user.email,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            password = password,
                            token = token
                        )
                    }
                }
            }
            .map {
                evaluateResponse(it) {it.user.toUser()} as Response<User>
            }
            .onErrorReturn { evaluateErrorResponse(it) }

    fun logout() {
        authenticationManager.logout()
    }
}