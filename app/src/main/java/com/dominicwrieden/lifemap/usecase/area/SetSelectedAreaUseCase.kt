package com.dominicwrieden.lifemap.usecase.area

import com.dominicwrieden.data.repository.area.AreaRepository

//TODO with updated version of rx-preferences (migrated to rxjava3), make observable?!?!?
interface SetSelectedAreaUseCase {
    operator fun invoke(areaId: String)
}

class SetSelectedAreaUseCaseImpl(
    private val areaRepository: AreaRepository
) : SetSelectedAreaUseCase {
    override fun invoke(areaId: String) = areaRepository.setSelectedArea(areaId)
}