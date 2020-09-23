package com.dominicwrieden.api.`interface`

import com.dominicwrieden.api.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.InputStream

interface Api {

    val authenticationStatus: Observable<AuthenticationStatus>

    fun login(userName: String, password: String): Single<Response<User>>

    fun logout(): Completable

    fun getItemTypes(): Single<Response<List<ItemType>>>

    fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>>

    fun getStates(): Single<Response<List<State>>>

    fun getAreas(): Single<Response<List<Area>>>

    fun getGeoDB(areaId: String): Single<Response<InputStream>>

    fun getUsers(): Single<Response<List<User>>>

    fun getItems(areaId: String): Single<Response<List<Item>>>
}