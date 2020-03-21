package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.PropertyConfig
import com.dominicwrieden.data.repository.`interface`.PropertyConfigRepository

class PropertyConfigRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api):PropertyConfigRepository {

    override fun getPropertyConfigs() = api.getPropertyConfigs()

    override fun savePropertyConfig(propertyConfig: PropertyConfig) {
        savePropertyConfigs(listOf(propertyConfig))
    }

    override fun savePropertyConfigs(propertyConfigs: List<PropertyConfig>) {
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