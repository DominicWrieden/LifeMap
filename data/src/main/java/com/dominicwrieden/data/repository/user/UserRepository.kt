package com.dominicwrieden.data.repository.user

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface UserRepository {

    fun downloadUsers(): Single<Task>
    fun getPermittedAreasForUser(userRemoteId: String): Single<Result<List<Area>>>
}