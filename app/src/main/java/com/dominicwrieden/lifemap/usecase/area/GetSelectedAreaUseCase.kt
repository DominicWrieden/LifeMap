package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.repository.area.AreaRepository

//TODO with updated version of rx-preferences (migrated to rxjava3), make observable
interface GetSelectedAreaUseCase {
    operator fun invoke(): String
}

class GetSelectedAreaUseCaseImpl(
    private val areaRepository: AreaRepository
) : GetSelectedAreaUseCase {
    override fun invoke() = areaRepository.getSelectedArea()
}