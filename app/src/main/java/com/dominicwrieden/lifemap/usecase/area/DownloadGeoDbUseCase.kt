package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.rxjava3.core.Single


interface DownloadGeoDbForAreaUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadGeoDbForAreaUseCaseImpl(
    private val areaRepository: AreaRepository,
    private val getAreasForUserUseCase: GetAreasForUserUseCase
) : DownloadGeoDbForAreaUseCase {
    override fun invoke() = getAreasForUserUseCase.invoke()
        .flatMap { areasResult ->
            when (areasResult) {
                is Result.Success -> {
                    Single.concat(areasResult.value
                        .map {
                            areaRepository.downloadGeoDBForArea(it.remoteId,it.geoDbFileName)
                        })
                        .toList()
                        .map { downloadedAreaGeoDBs ->
                            if (downloadedAreaGeoDBs.all { it is Task.Success }) {
                                return@map Task.Success
                            } else {
                                return@map downloadedAreaGeoDBs.first { it !is Task.Success }
                                    ?: Task.Failure(Error.UNKNOWN)
                            }
                        }
                }
                is Result.Failure -> Single.just(Task.Failure(areasResult.error))
            }
        }.onErrorReturn { Task.Failure(Error.UNKNOWN) }
}