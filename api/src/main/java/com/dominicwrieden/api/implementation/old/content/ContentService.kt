package com.dominicwrieden.api.implementation.old.content

import android.content.Context
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationService
import com.dominicwrieden.api.implementation.old.authentication.source.local.AuthenticationSharedPreferences
import com.dominicwrieden.api.implementation.old.content.model.*
import com.dominicwrieden.api.implementation.old.content.retrofit.ContentApi
import com.dominicwrieden.api.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


//TODO: Es heißt hier immer getStatuses, aber man bekommt States zurück
internal class ContentService(private val contentApi: ContentApi,
                     private val authenticationSharedPreferences: AuthenticationSharedPreferences) {


    fun getAreas(): Single<Response<List<Area>>> = contentApi.getAreas()
        .subscribeOn(Schedulers.io())
        .map {
            evaluateResponse(it) { areasDTO: List<AreaDTO> ->
                areasDTO.map { areaDTO -> areaDTO.toArea() }
            }
        }
        .onErrorReturn { evaluateErrorResponse(it) }


    fun getSpecies(): Single<Response<List<ItemType>>> = contentApi.getSpecies()
        .subscribeOn(Schedulers.io())
        .map {
            evaluateResponse(it) { speciesDTO: List<SpecieDTO> ->
                speciesDTO.map { specieDTO -> specieDTO.toItemType() }
            }
        }
        .onErrorReturn { evaluateErrorResponse(it) }


    fun getUsers(): Single<Response<List<User>>> =
        contentApi.getUsers(authenticationSharedPreferences.getToken())
            .subscribeOn(Schedulers.io())
            .map {
                evaluateResponse(it) { usersDTO: List<UserDTO> ->
                    usersDTO.map { userDTO -> userDTO.toUser() }
                }
            }
            .onErrorReturn { evaluateErrorResponse(it) }


    fun getStatuses(): Single<Response<List<State>>> = contentApi.getStatuses()
        .subscribeOn(Schedulers.io())
        .map {
            evaluateResponse(it) { statusDTO: List<StatusDTO> ->
                statusDTO.map { statusesDTO -> statusesDTO.toState() }
            }
        }
        .onErrorReturn { evaluateErrorResponse(it) }


    fun getItems(areaId: String) =
        contentApi.getClutches(authenticationSharedPreferences.getToken(), areaId)
            .subscribeOn(Schedulers.io())
            .map { evaluateResponse(it) { it } }
            .flatMap { clutchesResponse ->
                when (clutchesResponse) {
                    is Response.Success -> {
                        return@flatMap getUsers().map { usersResponse ->
                            when (usersResponse) {
                                is Response.Success -> {
                                    Response.Success(
                                        clutchesResponse.body.map { clutchResponse ->
                                            convertClutchHistoryToItem(
                                                clutchResponse,
                                                usersResponse.body
                                            )
                                        }.filterNotNull()
                                    )
                                }
                                else -> Response.UnknownError(Exception("Clutches Request succeed, but could not get users for clutches request"))
                            }
                        }
                    }
                    is Response.ServerError -> Single.just(clutchesResponse)
                    is Response.AuthenticationError -> Single.just(clutchesResponse)
                    is Response.NetworkError -> Single.just(clutchesResponse)
                    is Response.UnknownError -> Single.just(clutchesResponse)
                }

            }
            .onErrorReturn { evaluateErrorResponse(it) }

    fun getPropertyConfigs() = Single.just(
        Response.Success(
            listOf(
                PropertyConfig(
                    remoteId = "numberOfEggs",
                    name = "Anzahl Eier",
                    description = null,
                    propertyType = PropertyType.INT
                ),
                PropertyConfig(
                    remoteId = "dateOfHatching",
                    name = "Schlüpft am",
                    description = null,
                    propertyType = PropertyType.DATE
                )
            )
        ) as Response<List<PropertyConfig>>
    )
        .subscribeOn(Schedulers.io())
}