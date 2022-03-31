package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.repository.area.AreaRepository
import org.koin.java.KoinJavaComponent.inject

//TODO with updated version of rx-preferences (migrated to rxjava3), make observable
interface GetSelectedAreaUseCase {
    operator fun invoke(): String
}

class GetSelectedAreaUseCaseImpl: GetSelectedAreaUseCase {

    val areaRepository: AreaRepository by inject(AreaRepository::class.java)

    override fun invoke() = areaRepository.getSelectedArea()
}