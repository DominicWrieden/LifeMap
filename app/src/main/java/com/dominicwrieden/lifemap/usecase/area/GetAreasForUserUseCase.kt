package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCase
import io.reactivex.Single

interface GetAreasForUserUseCase {
    operator fun invoke(): Single<Result<List<Area>>>
}

class GetAreasForUserUseCaseImpl(
    private val getAreaUseCase: GetAreaUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : GetAreasForUserUseCase {
    override fun invoke() = getLoggedInUserUseCase.invoke()
        .flatMap { userResult ->
            when (userResult) {
                is Result.Success -> Single.merge(userResult.value.permittedAreaIds.map {
                    getAreaUseCase.invoke(
                        it
                    )
                })
                    .toList()
                    .map { areaResults ->
                        if (areaResults.all { it is Result.Success }) {
                            return@map Result.Success(areaResults.map { (it as Result.Success).value }) as Result<List<Area>>
                        } else {
                            val error =
                                (areaResults.firstOrNull { it is Result.Failure } as Result.Failure).error
                            return@map Result.Failure<List<Area>>(error)
                        }
                    }

                is Result.Failure -> Single.just(Result.Failure(userResult.error))
            }
        }
        .onErrorReturn { Result.Failure(Error.UNKNOWN) }
}