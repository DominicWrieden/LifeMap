package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject
import java.io.File

interface GetGeoDbForAreaUseCase {
    operator fun invoke(areaId: String): Single<Result<File>>
}

class GetGeoDbForAreaUseCaseImpl() : GetGeoDbForAreaUseCase {

    val areaRepository: AreaRepository by inject(AreaRepository::class.java)

    override fun invoke(areaId: String) = areaRepository.getGeoDbForArea(areaId)
}