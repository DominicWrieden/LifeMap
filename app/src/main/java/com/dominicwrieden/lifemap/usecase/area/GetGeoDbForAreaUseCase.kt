package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.Single
import java.io.File

interface GetGeoDbForAreaUseCase {
    operator fun invoke(areaId: String): Single<Result<File>>
}

class GetGeoDbForAreaUseCaseImpl(
    private val areaRepository: AreaRepository
) : GetGeoDbForAreaUseCase {
    override fun invoke(areaId: String) = areaRepository.getGeoDbForArea(areaId)
}