package com.dominicwrieden.data.model

import com.dominicwrieden.api.model.Response
import io.reactivex.rxjava3.core.Single

/**
 * For calls, where it is just important if it's successful or not
 */
sealed class Task {
    object Success : Task()
    data class Failure(val error: Error) : Task()
}



fun <T : Any> Single<Response<T>>.toTask(): Single<Task> =
    this.map {
        when (it) {
            is Response.Success -> Task.Success
            is Response.ServerError -> Task.Failure(Error.SERVER_ERROR)
            is Response.AuthenticationError -> Task.Failure(Error.AUTHENTICATION_ERROR)
            is Response.NetworkError -> Task.Failure(Error.NETWORK_ERROR)
            is Response.UnknownError -> Task.Failure(Error.UNKNOWN)
        }
    }.onErrorReturn {
        Task.Failure(Error.UNKNOWN)
    }
