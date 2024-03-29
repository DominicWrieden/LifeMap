package com.dominicwrieden.data.repository.user

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.queryToSingleResultMapToList
import com.dominicwrieden.data.model.toTask
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

class UserRepositoryImpl: UserRepository {

    val database: LifeMapDatabaseQueries by inject(LifeMapDatabaseQueries::class.java)
    val api: Api by inject(Api::class.java)

    override fun downloadUsers()
            = api.getUsers()
        .doOnSuccess { usersResponse ->
            usersResponse?.let {
                when (it) {
                    is Response.Success -> saveUsers(it.body)
                }
            }
        }.toTask()

    override fun getPermittedAreasForUser(userRemoteId: String): Single<Result<List<Area>>> =
        database.getPermittedAreasForUser(userRemoteId)
            .queryToSingleResultMapToList {
                Area(
                    remoteId = it.remoteIdArea,
                    name = it.name,
                    geoDbId = it.geoDbID,
                    geoDbName = it.geoDbName,
                    geoDbFileName = it.geoDbName,
                    geoDbFilePath = it.geoDbFilePath,
                    geoDbCreateDate = it.geoDbCreateDate
                )
            }

    private fun saveUser(user: com.dominicwrieden.api.model.User) {
        saveUsers(listOf(user))
    }

    private fun saveUsers(users: List<com.dominicwrieden.api.model.User>) {
        database.transaction {
            users.forEach { user ->
                database.insertUser(
                    remoteIdUser = user.remoteId,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName
                )
            }
        }

        database.transaction {
            users.forEach { user ->
                user.permittedAreaIds.forEach { areaId ->
                    database.insertUserArea(user.remoteId, areaId)
                }
            }
        }

    }


}