package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.Area
import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCase
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface GetAreasForUserUseCase {
    operator fun invoke(): Single<Result<List<Area>>>
}

class GetAreasForUserUseCaseImpl : GetAreasForUserUseCase {

    val getAreaUseCase: GetAreaUseCase by inject(GetAreaUseCase::class.java)
    val getLoggedInUserUseCase: GetLoggedInUserUseCase by inject(GetLoggedInUserUseCase::class.java)

    override fun invoke(): Single<Result<List<Area>>> = getLoggedInUserUseCase.invoke()
        .flatMap { userResult ->
            when (userResult) {
                is Result.Success -> {
                    val permittedAreaIds = userResult.value.permittedAreaIds

                    Single.merge(permittedAreaIds.map { getAreaUseCase.invoke(it) })
                        .toList()
                        .map { areaResults ->
                            if (areaResults.all { it is Result.Success }) {
                                return@map Result.Success(areaResults.map { (it as Result.Success).value })
                            } else {
                                val error =
                                    (areaResults.firstOrNull { it is Result.Failure } as Result.Failure).error
                                return@map Result.Failure<List<Area>>(error)
                            }
                        }
                }
                is Result.Failure -> Single.just(Result.Failure(userResult.error))
            }
        }
        .onErrorReturn { Result.Failure(Error.UNKNOWN) }
}