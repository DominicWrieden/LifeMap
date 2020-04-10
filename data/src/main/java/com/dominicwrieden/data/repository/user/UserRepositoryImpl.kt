package com.dominicwrieden.data.repository.user

import com.dominicwrieden.Area
import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.toTask
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable

class UserRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) :
    UserRepository {

    override fun downloadUsers()
            = api.getUsers()
        .doOnSuccess { usersResponse ->
            usersResponse?.let {
                when (it) {
                    is Response.Success -> saveUsers(it.body)
                }
            }
        }.toTask()

    override fun getPermittedAreasForUser(userRemoteId: String): Observable<List<Area>> =
        database.getPermittedAreasForUser(userRemoteId).asObservable().mapToList()

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