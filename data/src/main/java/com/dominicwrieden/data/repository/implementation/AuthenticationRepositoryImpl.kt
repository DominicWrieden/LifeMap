package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.repository.`interface`.AuthenticationRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthenticationRepositoryImpl(val api: Api): AuthenticationRepository {

    override fun login(userName: String, password: String): Single<Response<Nothing>> =
        api.login(userName, password)

    override fun logout(): Completable = api.logout()
}