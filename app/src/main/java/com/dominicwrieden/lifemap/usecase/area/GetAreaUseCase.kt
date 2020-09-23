package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.rxjava3.core.Single

interface GetAreaUseCase {
    operator fun invoke(areaId: String): Single<Result<Area>>
}

class GetAreaUseCaseImpl(private val areaRepository: AreaRepository) : GetAreaUseCase {
    override fun invoke(areaId: String) = areaRepository.getArea(areaId)
}