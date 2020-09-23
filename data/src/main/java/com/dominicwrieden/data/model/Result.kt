package com.dominicwrieden.data.model

import com.dominicwrieden.api.model.Response
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.rx3.asObservable
import com.squareup.sqldelight.runtime.rx3.mapToList
import com.squareup.sqldelight.runtime.rx3.mapToOne
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


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
    this.map { response ->
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
fun <T : Any, U : Any> Query<T>.queryToSingleResultMapToOne(transform: (input: T) -> U): Single<Result<U>> =
    this.asObservable()
        .mapToOne()
        .firstOrError()
        .map { query ->
            Result.Success(transform(query)) as Result<U>
        }
        .onErrorReturn { error ->
            when (error) {
                is NullPointerException -> Result.Failure(Error.DATABASE_ERROR)
                is IllegalStateException -> Result.Failure(Error.DATABASE_ERROR)
                else -> Result.Failure(Error.UNKNOWN)
            }
        }

@Suppress("USELESS_CAST", "UNCHECKED_CAST")
fun <T : Any, U : Any> Query<T>.queryToSingleResultMapToList(transform: (input: T) -> U): Single<Result<List<U>>> =
    this.asObservable()
        .mapToList()
        .firstOrError()
        .map { query ->
            Result.Success(query.map { transform(it) }) as Result<List<U>>
        }
        .onErrorReturn { error ->
            when (error) {
                is NullPointerException -> Result.Failure(Error.DATABASE_ERROR)
                is IllegalStateException -> Result.Failure(Error.DATABASE_ERROR)
                else -> Result.Failure(Error.UNKNOWN)
            }
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



@Suppress("USELESS_CAST")
fun <T : Any, U : Any> Query<T>.queryToObservableResultMapToList(transform: (input: List<T>) -> List<U>): Observable<Result<List<U>>> =
    this.asObservable()
        .mapToList()
        .map { query ->
            Result.Success(transform(query)) as Result<List<U>>
        }
        .onErrorReturn { error ->
            when (error) {
                is NullPointerException -> Result.Failure(Error.DATABASE_ERROR)
                is IllegalStateException -> Result.Failure(Error.DATABASE_ERROR)
                else -> Result.Failure(Error.UNKNOWN)
            }
        }