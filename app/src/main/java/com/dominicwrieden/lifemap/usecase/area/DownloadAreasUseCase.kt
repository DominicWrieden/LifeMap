package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.Single

interface DownloadAreasUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadAreasUseCaseImpl(private val areaRepository: AreaRepository): DownloadAreasUseCase {
    override fun invoke() = areaRepository.downloadAreas()
}