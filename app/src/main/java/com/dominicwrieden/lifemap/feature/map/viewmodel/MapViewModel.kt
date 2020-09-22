package com.dominicwrieden.lifemap.feature.map.viewmodel

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.feature.map.model.MapContentState
import com.dominicwrieden.lifemap.feature.map.model.MapStates
import com.dominicwrieden.lifemap.usecase.area.GetGeoDbForAreaUseCase
import com.dominicwrieden.lifemap.usecase.item.GetItemsForAreaUseCase
import com.dominicwrieden.lifemap.util.toLiveData
import com.dominicwrieden.lifemap.util.toUnsubscribedLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.rxkotlin.addTo

class MapViewModel(private val getGeoDbForAreaUseCase: GetGeoDbForAreaUseCase, private val getItemsForAreaUseCase: GetItemsForAreaUseCase) : BaseViewModel() {

    private val blockland2019AreaId = "5c69387f556af7fd0a916f9d"

    private val mapStateRelay = BehaviorRelay.create<MapStates>()

    val mapState = mapStateRelay.toUnsubscribedLiveData(disposable)


    val mapContentState = getItemsForAreaUseCase.invoke(blockland2019AreaId)
        .map { areaItemResult ->
            when (areaItemResult) {
                is Result.Success -> MapContentState.ItemList(areaItemResult.value)
                is Result.Failure -> MapContentState.Error
            }
        }.onErrorReturn { MapContentState.Error }
        .toUnsubscribedLiveData(disposable)


    init {
        getGeoDbForAreaUseCase.invoke(blockland2019AreaId) //TODO make blockland2019AreaId dynamic
            .map{geoDbFileResult ->
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