package com.dominicwrieden.data.repository.area

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.*
import com.dominicwrieden.data.util.FileManager
import com.dominicwrieden.data.util.SharedPreferencesUtil
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject
import java.io.File

class AreaRepositoryImpl: AreaRepository {

    val database: LifeMapDatabaseQueries by inject(LifeMapDatabaseQueries::class.java)
    val fileManager: FileManager by inject(FileManager::class.java)
    val sharedPreferencesUtil: SharedPreferencesUtil by inject(SharedPreferencesUtil::class.java)
    val api: Api by inject(Api::class.java)

    companion object {
        private const val SELECTED_AREA_ID = "SELECTED_AREA_ID"
    }

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
                    fileManager.saveFile(geoDbFileName, geoDBFileResponse.body)
                }


            }.toTask()

    override fun getArea(areaId: String): Single<Result<com.dominicwrieden.Area>> =
        database.getArea(areaId)
            .queryToSingleResultMapToOne{it}


    override fun getAreas() = api.getAreas().toResult()

    override fun getGeoDbForArea(areaId: String): Single<Result<File>> =
        database.getGeoDbFileName(areaId)
            .queryToSingleResultMapToOne { it }
            .flatMap { geoDbFileNameResult ->
                when (geoDbFileNameResult) {
                    is Result.Success -> Single.just(
                        Result.Success(
                            fileManager.getFile(
                                geoDbFileNameResult.value
                            )
                        )
                    )
                    is Result.Failure -> Single.just(Result.Failure(geoDbFileNameResult.error))
                }
            }.onErrorReturn { Result.Failure(Error.UNKNOWN) }

    override fun setSelectedArea(areaId: String) {
        sharedPreferencesUtil.saveToSharedPreferences(SELECTED_AREA_ID, areaId)
    }

    override fun getSelectedArea(): String =
        sharedPreferencesUtil.retrieveFromSharedPreferences(SELECTED_AREA_ID)


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