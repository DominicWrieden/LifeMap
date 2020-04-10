package com.dominicwrieden.data.repository.area

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.model.toTask
import io.reactivex.Single

class AreaRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) :
    AreaRepository {

    override fun downloadAreas(): Single<Task> = api.getAreas()
        .doOnSuccess { areaResponse ->
            areaResponse?.let {
                when (it) {
                    is Response.Success -> saveAreas(it.body)
                }
            }
        }
        .toTask()





    override fun getAreas() = api.getAreas()


    private fun saveArea(area: Area) {
        saveAreas(listOf(area))
    }

    private fun saveAreas(areas: List<Area>) {
        database.transaction {
            areas.forEach { area ->
                database.insertArea(
                    remoteIdArea = area.remoteId,
                    name = area.name,
                    geoDbName = area.geoDbName,
                    geoDbID = area.geoDbId,
                    geoDbFilePath = area.geoDbFilePath,
                    geoDbCreateDate = area.geoDbCreateDate
                )
            }
        }
    }

}