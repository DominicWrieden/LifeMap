package com.dominicwrieden.data.repository.area

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import io.reactivex.rxjava3.core.Single
import java.io.File

interface AreaRepository {

    fun downloadAreas(): Single<Task>

    fun downloadGeoDBForArea(
        areaId: String,
        geoDbFileName: String
    ): Single<Task> //TODO only areaId should be used to get the GeoDB

    fun getArea(areaId: String): Single<Result<Area>>

    fun getAreas(): Single<Result<List<Area>>>

    fun getGeoDbForArea(areaId: String): Single<Result<File>>
    fun setSelectedArea(areaId: String)
    fun getSelectedArea(): String
}