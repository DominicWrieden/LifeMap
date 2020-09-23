package com.dominicwrieden.data.repository.area

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.*
import com.dominicwrieden.data.util.FileManager
import io.reactivex.rxjava3.core.Single
import java.io.File

class AreaRepositoryImpl(private val database: LifeMapDatabaseQueries,
                         private val fileManager: FileManager,
                         private val api: Api) :
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

    override fun downloadGeoDBForArea(areaId: String, geoDbFileName: String): Single<Task> =
        api.getGeoDB(geoDbFileName)
            .doOnSuccess { geoDBFileResponse ->
                if (geoDBFileResponse is Response.Success) {
                    fileManager.saveFile(geoDbFileName,geoDBFileResponse.body)
                }


            }.toTask()

    override fun getArea(areaId: String): Single<Result<Area>> =
        database.getArea(areaId)
            .queryToSingleResultMapToOne { areaDTO ->
                Area(
                    remoteId = areaDTO.remoteIdArea,
                    name = areaDTO.name,
                    geoDbId = areaDTO.geoDbID,
                    geoDbName = areaDTO.geoDbName,
                    geoDbFileName = areaDTO.geoDbFileName,
                    geoDbFilePath = areaDTO.geoDbFilePath,
                    geoDbCreateDate = areaDTO.geoDbCreateDate
                )
            }


    override fun getAreas() = api.getAreas().toResult()

    override fun getGeoDbForArea(areaId: String): Single<Result<File>>
            = database.getGeoDbFileName(areaId)
        .queryToSingleResultMapToOne { it }
        .flatMap { geoDbFileNameResult ->
            when (geoDbFileNameResult) {
                is Result.Success -> Single.just(Result.Success(fileManager.getFile(geoDbFileNameResult.value)))
                is Result.Failure -> Single.just(Result.Failure<File>(geoDbFileNameResult.error))
            }
        }.onErrorReturn {Result.Failure(Error.UNKNOWN)}

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
                    geoDbFileName = area.geoDbFileName,
                    geoDbFilePath = area.geoDbFilePath,
                    geoDbCreateDate = area.geoDbCreateDate
                )
            }
        }
    }

}