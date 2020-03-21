package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import io.reactivex.Single

interface AreaRepository {

    fun saveArea(area: Area)

    fun saveAreas(areas: List<Area>)
    fun getAreas(): Single<Response<List<Area>>>
}