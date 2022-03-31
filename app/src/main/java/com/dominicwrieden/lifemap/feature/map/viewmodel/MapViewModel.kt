package com.dominicwrieden.lifemap.feature.map.viewmodel

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.feature.map.model.MapContentState
import com.dominicwrieden.lifemap.feature.map.model.MapStates
import com.dominicwrieden.lifemap.usecase.area.GetGeoDbForAreaUseCase
import com.dominicwrieden.lifemap.usecase.area.GetSelectedAreaUseCase
import com.dominicwrieden.lifemap.usecase.item.GetItemsForAreaUseCase
import com.dominicwrieden.lifemap.util.toUnsubscribedLiveData
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.java.KoinJavaComponent.inject

class MapViewModel : BaseViewModel() {

    val getSelectedAreaUseCase: GetSelectedAreaUseCase by inject(GetSelectedAreaUseCase::class.java)
    val getGeoDbForAreaUseCase: GetGeoDbForAreaUseCase by inject(GetGeoDbForAreaUseCase::class.java)
    val getItemsForAreaUseCase: GetItemsForAreaUseCase by inject(GetItemsForAreaUseCase::class.java)

    private val mapStateRelay = BehaviorRelay.create<MapStates>()

    val mapState = mapStateRelay.toUnsubscribedLiveData(disposable)


    val mapContentState = getItemsForAreaUseCase.invoke(getSelectedAreaUseCase.invoke())
        .map { areaItemResult ->
            when (areaItemResult) {
                is Result.Success -> MapContentState.ItemList(areaItemResult.value)
                is Result.Failure -> MapContentState.Error
            }
        }.onErrorReturn { MapContentState.Error }
        .toUnsubscribedLiveData(disposable)


    init {
        getGeoDbForAreaUseCase.invoke(getSelectedAreaUseCase.invoke())
            .map { geoDbFileResult ->
                when (geoDbFileResult) {
                    is Result.Success -> MapStates.Init(geoDbFileResult.value)
                    is Result.Failure -> MapStates.Error
                }
            }
            .onErrorReturn { MapStates.Error }
            .subscribe(mapStateRelay)
            .addTo(disposable)
    }
}