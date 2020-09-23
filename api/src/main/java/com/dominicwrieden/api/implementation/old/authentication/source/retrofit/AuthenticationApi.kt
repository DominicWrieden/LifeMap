package com.dominicwrieden.api.implementation.old.authentication.source.retrofit

import com.dominicwrieden.api.implementation.old.authentication.model.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthenticationApi {


    companion object{
        const val AUTHENTICATION_REQUEST_ENDPOINT = "/users/login"
        const val AUTHENTICATION_REQUEST_QUERY_EMAIL = "email"
        const val AUTHENTICATION_REQUEST_QUERY_PASSWORD = "password"
    }

    @POST(AUTHENTICATION_REQUEST_ENDPOINT)
    fun login(
        @Query(AUTHENTICATION_REQUEST_QUERY_EMAIL) email: String,
        @Query(AUTHENTICATION_REQUEST_QUERY_PASSWORD) password: String
    ): Single<Response<LoginResponse>>
}