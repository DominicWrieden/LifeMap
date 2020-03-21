package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.Area
import com.dominicwrieden.User
import io.reactivex.Observable

interface UserRepository {

    fun getAllUser() : Observable<List<User>>

    fun getPermittedAreasForUser (userRemoteId: String): Observable<List<Area>>
}