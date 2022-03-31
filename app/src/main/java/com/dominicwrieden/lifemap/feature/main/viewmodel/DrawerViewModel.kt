package com.dominicwrieden.lifemap.feature.main.viewmodel

import com.dominicwrieden.Area
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.usecase.area.GetAreasForUserUseCase
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

class DrawerViewModel: BaseViewModel() {

    val getAreasForUserUseCase: GetAreasForUserUseCase by inject(GetAreasForUserUseCase::class.java)

    val areas: Single<List<Area>> = getAreasForUserUseCase.invoke().map {
        when (it) {
            is Result.Failure -> emptyList()
            is Result.Success -> it.value
        }
    }
}