package com.dominicwrieden.lifemap.feature.map.model

import com.dominicwrieden.data.repository.item.model.AreaItem
import java.io.File

sealed class MapStates {
    data class Init(val mapFile: File) : MapStates()
    object Error : MapStates()
}

sealed class MapContentState {
    data class ItemList(val items: List<AreaItem>) : MapContentState()
    object Error : MapContentState()
}

