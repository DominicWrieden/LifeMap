package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.User
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {


    fun getPermittedAreasForUser(userRemoteId: String): Observable<List<Area>>

    fun saveUser(user: User)

    fun saveUsers(users: List<User>)

    fun getUsers(): Single<Response<List<User>>>

    fun getAllUser(): Observable<List<com.dominicwrieden.User>>
}