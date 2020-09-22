package com.dominicwrieden.api.`interface`

import com.dominicwrieden.api.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

interface Api {

    val authenticationStatus: Observable<AuthenticationStatus>

    fun login(userName: String, password: String): Single<Response<User>>

    fun logout(): Completable

    fun getItemTypes(): Single<Response<List<ItemType>>>

    fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>>

    fun getStates(): Single<Response<List<State>>>

    fun getAreas(): Single<Response<List<Area>>>

    fun getGeoDB(areaId: String): Single<Response<File>>

    fun getUsers(): Single<Response<List<User>>>

    fun getItems(areaId: String): Single<Response<List<Item>>>
}