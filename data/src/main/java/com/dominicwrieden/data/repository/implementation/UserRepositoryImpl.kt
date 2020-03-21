package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.Area
import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.User
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.data.repository.`interface`.UserRepository
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable

class UserRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) : UserRepository {



    override fun getUsers() = api.getUsers()

    override fun getAllUser(): Observable<List<User>> =
        database.getAllUser().asObservable().mapToList()

    override fun getPermittedAreasForUser(userRemoteId: String): Observable<List<Area>> =
        database.getPermittedAreasForUser(userRemoteId).asObservable().mapToList()

    override fun saveUser(user: com.dominicwrieden.api.model.User) {
        saveUsers(listOf(user))
    }

    override fun saveUsers(users: List<com.dominicwrieden.api.model.User>) {
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
                    database.insertUserIsPermittedForArea(user.remoteId, areaId)
                }
            }
        }

    }


}