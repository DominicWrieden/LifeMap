package com.dominicwrieden.data.repository.authentication

import com.dominicwrieden.api.model.AuthenticationStatus
import com.dominicwrieden.api.model.User
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AuthenticationRepository {
    val authenticationStatus: Observable<AuthenticationStatus>
    fun login(userName: String, password: String): Single<Task>
    fun logout(): Completable
    fun getLoggedInUser(): Single<Result<User>>
}