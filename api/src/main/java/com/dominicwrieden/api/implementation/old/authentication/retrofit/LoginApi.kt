package com.dominicwrieden.api.implementation.old.authentication.retrofit

import com.dominicwrieden.api.implementation.old.authentication.response.LoginResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {

@POST("/users/login")
fun login(@Query("email") email: String, @Query("password")password:String): Single<Response<LoginResponse>>
}