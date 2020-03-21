package com.dominicwrieden.api.`interface`

import com.dominicwrieden.api.model.*
import io.reactivex.Completable
import io.reactivex.Single

interface Api {

    fun login(userName: String, password: String): Single<Response<Nothing>>

    fun logout(): Completable

    fun getItemTypes(): Single<Response<List<ItemType>>>

    fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>>

    fun getStates(): Single<Response<List<State>>>

    fun getAreas(): Single<Response<List<Area>>>

    fun getUsers(): Single<Response<List<User>>>

    fun getItems(areaId: String): Single<Response<List<Item>>>

    //TODO get one item
    //fun getItem(itemId: String, areaId: String): Single<Response<Item>>
}