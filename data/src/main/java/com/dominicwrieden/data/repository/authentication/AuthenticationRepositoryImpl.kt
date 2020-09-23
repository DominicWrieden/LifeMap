package com.dominicwrieden.data.repository.authentication

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.AuthenticationStatus
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.User
import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.model.toTask
import com.dominicwrieden.data.util.SharedPreferencesUtil
import com.squareup.sqldelight.runtime.rx3.asObservable
import com.squareup.sqldelight.runtime.rx3.mapToOne
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AuthenticationRepositoryImpl(private val database: LifeMapDatabaseQueries, val api: Api, val sharedPreferencesUtil: SharedPreferencesUtil):
    AuthenticationRepository {
    override val authenticationStatus: Observable<AuthenticationStatus>
        get() = api.authenticationStatus

    override fun login(userName: String, password: String): Single<Task> =
        api.login(userName, password)
            .doOnSuccess { loginResponse ->
                loginResponse?.let {
                    when (it) {
                        is Response.Success -> {
                            saveLoggedInUserId(it.body.remoteId)
                        }
                    }
                }
            }
            .toTask()

    override fun logout(): Completable = api.logout()

    override fun getLoggedInUser(): Single<Result<User>> = database.getUser(getLoggedInUserId())
        .asObservable()
        .mapToOne()
        .firstOrError()
        .map {
            @Suppress("USELESS_CAST")
            Result.Success(
                User(
                    remoteId = it.remoteIdUser,
                    email = it.email,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    permittedAreaIds = it.group_concat.split(",")
                )
            ) as Result<User>
        }.onErrorReturn { Result.Failure(Error.DATABASE_ERROR) }

    private fun saveLoggedInUserId(userId: String) {
        sharedPreferencesUtil.saveToSharedPreferences(LOGGED_IN_USER_ID,userId)
    }

    private fun getLoggedInUserId() = sharedPreferencesUtil.retrieveFromSharedPreferences(
        LOGGED_IN_USER_ID)

    companion object {
        private val LOGGED_IN_USER_ID = "LOGGED_IN_USER_ID"
    }
}