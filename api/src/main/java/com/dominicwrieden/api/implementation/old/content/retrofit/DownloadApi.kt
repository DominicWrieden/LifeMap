package com.dominicwrieden.api.implementation.old.content.retrofit

import com.dominicwrieden.api.implementation.old.content.response.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DownloadApi {

    @GET("/areas")
    fun getAreas(): Single<Response<List<AreaDTO>>>

    @GET("/species")
    fun getSpecies(): Single<Response<List<SpecieDTO>>>

    @GET("/users/{token}")
    fun getUsers(@Path("token") token: String): Single<Response<List<UserDTO>>>

    @GET("/statuses")
    fun getStatuses(): Single<Response<List<StatusDTO>>>


    @GET("/clutches/{token}?withhistory=true")
    fun getClutches(@Path("token") token: String, @Query("area") areaId: String): Single<Response<List<List<ClutchDTO>>>>

}