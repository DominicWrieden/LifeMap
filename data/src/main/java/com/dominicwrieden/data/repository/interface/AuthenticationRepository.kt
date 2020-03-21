package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.Response
import io.reactivex.Completable
import io.reactivex.Single

interface AuthenticationRepository {
    fun login(userName: String, password: String): Single<Response<Nothing>>
    fun logout(): Completable
}