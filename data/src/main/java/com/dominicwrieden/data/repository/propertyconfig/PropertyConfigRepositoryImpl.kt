package com.dominicwrieden.data.repository.propertyconfig

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.PropertyConfig
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.toTask
import org.koin.java.KoinJavaComponent.inject

class PropertyConfigRepositoryImpl:PropertyConfigRepository {

    val database: LifeMapDatabaseQueries by inject(LifeMapDatabaseQueries::class.java)
    val api: Api by inject(Api::class.java)

    override fun downloadPropertyConfigs() = api.getPropertyConfigs()
        .doOnSuccess { propertyConfigsResponse ->
            propertyConfigsResponse?.let {
                when (it) {
                    is Response.Success -> savePropertyConfigs(it.body)
                }
            }
        }
        .toTask()

    private fun savePropertyConfig(propertyConfig: PropertyConfig) {
        savePropertyConfigs(listOf(propertyConfig))
    }

    private fun savePropertyConfigs(propertyConfigs: List<PropertyConfig>) {
       database.transaction {
           propertyConfigs.forEach {propertyConfig ->
               database.insertPropertyConfig(
                   remoteIdPropertyConfig =propertyConfig.remoteId,
                   name = propertyConfig.name,
                   description = propertyConfig.description,
                   propertyType = propertyConfig.propertyType
               )
           }
       }
    }
}