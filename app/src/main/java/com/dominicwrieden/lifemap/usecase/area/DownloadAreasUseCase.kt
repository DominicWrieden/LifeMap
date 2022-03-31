package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface DownloadAreasUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadAreasUseCaseImpl : DownloadAreasUseCase {

    val areaRepository: AreaRepository by inject(AreaRepository::class.java)

    override fun invoke() = areaRepository.downloadAreas()
}