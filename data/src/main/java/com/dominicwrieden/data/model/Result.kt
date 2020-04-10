package com.dominicwrieden.data.model

import com.dominicwrieden.api.model.Response
import io.reactivex.Observable
import io.reactivex.Single


/**
 * For calls, where the result can have a value
 */

sealed class Result<T> {

    data class Success<T>(val value: T) : Result<T>()

    data class Failure<T>(val error: Error) : Result<T>()

}

//TODO log errors

@Suppress("USELESS_CAST")
fun <T : Any> Observable<Response<T>>.toResult(): Observable<Result<T>> =
    this.map {response ->
        when (response) {
            is Response.Success -> Result.Success(response.body) as Result<T>
            is Response.ServerError -> Result.Failure(Error.SERVER_ERROR)
            is Response.AuthenticationError -> Result.Failure(Error.AUTHENTICATION_ERROR)
            is Response.NetworkError -> Result.Failure(Error.NETWORK_ERROR)
            is Response.UnknownError -> Result.Failure(Error.UNKNOWN)
            else -> Result.Failure(Error.UNKNOWN)
        }
    }.onErrorReturn {
        Result.Failure(Error.UNKNOWN)
    }


@Suppress("USELESS_CAST")
fun <T : Any> Single<Response<T>>.toResult(): Single<Result<T>> =
    this.map {
        when (it) {
            is Response.Success -> Result.Success(it.body) as Result<T>
            is Response.ServerError -> Result.Failure(Error.SERVER_ERROR)
            is Response.AuthenticationError -> Result.Failure(Error.AUTHENTICATION_ERROR)
            is Response.NetworkError -> Result.Failure(Error.NETWORK_ERROR)
            is Response.UnknownError -> Result.Failure(Error.UNKNOWN)
        }
    }.onErrorReturn {
        Result.Failure(Error.UNKNOWN)
    }