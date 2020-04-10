package com.dominicwrieden.data.repository.user

import com.dominicwrieden.Area
import com.dominicwrieden.data.model.Task
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {

    fun downloadUsers(): Single<Task>
    fun getPermittedAreasForUser(userRemoteId: String): Observable<List<Area>>
}