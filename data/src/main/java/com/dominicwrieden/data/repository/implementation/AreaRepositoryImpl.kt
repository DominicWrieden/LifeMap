package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Area
import com.dominicwrieden.data.repository.`interface`.AreaRepository

class AreaRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api:Api) : AreaRepository {

    override fun getAreas() = api.getAreas()

    override fun saveArea(area: Area) {
        saveAreas(listOf(area))
    }

    override fun saveAreas(areas: List<Area>) {
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