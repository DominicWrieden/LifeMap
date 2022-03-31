package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.Area
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.area.AreaRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface GetAreaUseCase {
    operator fun invoke(areaId: String): Single<Result<Area>>
}

class GetAreaUseCaseImpl() : GetAreaUseCase {

    val areaRepository: AreaRepository by inject(AreaRepository::class.java)

    override fun invoke(areaId: String) = areaRepository.getArea(areaId)
}