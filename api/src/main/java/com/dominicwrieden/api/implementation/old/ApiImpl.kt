package com.dominicwrieden.api.implementation.old

import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationManager
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationService
import com.dominicwrieden.api.implementation.old.content.ContentService
import com.dominicwrieden.api.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.InputStream

internal class ApiImpl(
    private val authenticationService: AuthenticationService,
    private val contentService: ContentService,
    private val authenticationManager: AuthenticationManager
) : Api {
    override val authenticationStatus: Observable<AuthenticationStatus>
        get() = authenticationManager.authenticationStatus

    override fun login(
        userName: String,
        password: String
    ): Single<Response<User>> =
        authenticationService.login(userName, password)

    override fun logout(): Completable {
        authenticationService.logout()

        return Completable.complete()
    }

    override fun getItemTypes(): Single<Response<List<ItemType>>> =
        contentService.getSpecies()


    override fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>> =
        contentService.getPropertyConfigs()

    override fun getStates(): Single<Response<List<State>>> = contentService.getStatuses()

    override fun getAreas(): Single<Response<List<Area>>> = contentService.getAreas()
    override fun getGeoDB(areaId: String): Single<Response<InputStream>> =
        contentService.getGeoDBForArea(areaId)

    override fun getUsers(): Single<Response<List<User>>> = contentService.getUsers()

    override fun getItems(areaId: String): Single<Response<List<Item>>> =
        contentService.getItems(areaId)
}