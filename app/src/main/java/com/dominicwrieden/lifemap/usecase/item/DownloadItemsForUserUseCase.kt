package com.dominicwrieden.lifemap.usecase.item

import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.item.ItemRepository
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCase
import io.reactivex.rxjava3.core.Single


interface DownloadItemsUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadItemsUseCaseImpl(
    private val itemRepository: ItemRepository,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) :
    DownloadItemsUseCase {
    override fun invoke() = getLoggedInUserUseCase.invoke()
        .flatMap { resultUser ->
            when (resultUser) {
                is Result.Success -> {
                    Single.concat(resultUser.value.permittedAreaIds
                        .map { areaId ->
                            itemRepository.downloadItemsForArea(areaId)
                        })
                        .toList()
                        .map { downloadItemTasks ->
                            if (downloadItemTasks.all { it is Task.Success }) {
                                return@map Task.Success
                            } else {
                                return@map downloadItemTasks.first { it !is Task.Success }
                                    ?: Task.Failure(Error.UNKNOWN)
                            }
                        }
                }
                is Result.Failure -> return@flatMap Single.just(Task.Failure(resultUser.error))
            }
        }
        .onErrorReturn {
            Task.Failure(Error.UNKNOWN)
        }
}