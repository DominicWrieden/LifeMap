package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.Area
import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.User
import com.dominicwrieden.data.repository.`interface`.UserRepository
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable

class UserRepositoryImpl(val database: LifeMapDatabaseQueries) : UserRepository {


    override fun getAllUser(): Observable<List<User>> =
        database.getAllUser().asObservable().mapToList()

    override fun getPermittedAreasForUser(userRemoteId: String): Observable<List<Area>> =
        database.getPermittedAreasForUser(userRemoteId).asObservable().mapToList()
}