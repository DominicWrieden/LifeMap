package com.dominicwrieden.api.implementation.old

import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.implementation.old.authentication.LoginService
import com.dominicwrieden.api.implementation.old.content.DownloadService
import com.dominicwrieden.api.model.*
import io.reactivex.Completable
import io.reactivex.Single

class ApiImpl(
    private val loginRepository: LoginService,
    private val downloadService: DownloadService
) : Api {

    override fun login(
        userName: String,
        password: String
    ): Single<Response<Nothing>> =
        loginRepository.login(userName, password)

    override fun logout(): Completable {
        loginRepository.logout()

        return Completable.complete()
    }

    override fun getItemTypes(): Single<Response<List<ItemType>>> =
        downloadService.getSpecies()


    override fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>>  = downloadService.getPropertyConfigs()

    override fun getStates(): Single<Response<List<State>>> = downloadService.getStatuses()

    override fun getAreas(): Single<Response<List<Area>>> = downloadService.getAreas()


    override fun getUsers(): Single<Response<List<User>>> = downloadService.getUsers()

    override fun getItems(areaId: String): Single<Response<List<Item>>>  = downloadService.getItems(areaId)
}