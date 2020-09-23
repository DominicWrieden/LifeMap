package com.dominicwrieden.data.repository.authentication

import com.dominicwrieden.api.model.AuthenticationStatus
import com.dominicwrieden.api.model.User
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AuthenticationRepository {
    val authenticationStatus: Observable<AuthenticationStatus>
    fun login(userName: String, password: String): Single<Task>
    fun logout(): Completable
    fun getLoggedInUser(): Single<Result<User>>
}