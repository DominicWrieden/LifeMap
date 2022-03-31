package com.dominicwrieden.api.implementation.old.authentication

import com.dominicwrieden.api.implementation.old.authentication.model.toUser
import com.dominicwrieden.api.implementation.old.authentication.source.retrofit.AuthenticationApi
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.User
import com.dominicwrieden.api.model.evaluateErrorResponse
import com.dominicwrieden.api.model.evaluateResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

internal class AuthenticationService{

    val authenticationApi: AuthenticationApi by inject(AuthenticationApi::class.java)
    val authenticationManager: AuthenticationManager by inject(AuthenticationManager::class.java)


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
            .map { loginResponse ->
                evaluateResponse(loginResponse) {it.user.toUser()}
            }
            .onErrorReturn { evaluateErrorResponse(it) }

    fun logout() {
        authenticationManager.logout()
    }
}