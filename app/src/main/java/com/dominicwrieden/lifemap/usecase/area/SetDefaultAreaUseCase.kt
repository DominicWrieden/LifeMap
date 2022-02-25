package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.lifemap.usecase.item.GetItemsForAreaUseCase
import io.reactivex.rxjava3.core.Single


interface SetDefaultAreaUseCase {
    operator fun invoke(): Single<Task>
}

class SetDefaultAreaUseCaseImpl(
    private val getAreasForUserUseCase: GetAreasForUserUseCase,
    private val getItemsForAreaUseCase: GetItemsForAreaUseCase,
    private val setSelectedAreaUseCase: SetSelectedAreaUseCase,
) : SetDefaultAreaUseCase {
    override fun invoke(): Single<Task> =
        getAreasForUserUseCase.invoke().flatMap { resultAreasForUser ->
            when (resultAreasForUser) {
                is Result.Success -> {
                    if (resultAreasForUser.value.isEmpty()) {
                        return@flatMap Single.just(Task.Failure(Error.DATABASE_ERROR))
                    } else {
                        val permittedAreaIds = resultAreasForUser.value.map { it.remoteId }

                        Single.merge(permittedAreaIds.map { areaId ->
                            getItemsForAreaUseCase.invoke(areaId).map { itemResult ->
                                when (itemResult) {
                                    is Result.Success -> Pair(areaId, itemResult.value.size)
                                    is Result.Failure -> Pair(areaId, 0)
                                }
                            }.first(Pair(areaId, 0))
                        })
                            .toList()
                            .map { areaItemSizeList ->
                                val areaWithMostItems =
                                    areaItemSizeList.sortedByDescending { it.second }.first().first

                                setSelectedAreaUseCase.invoke(areaWithMostItems)
                                Task.Success
                            }
                    }
                }
                is Result.Failure -> Single.just(Task.Failure(resultAreasForUser.error))
            }

        }
}