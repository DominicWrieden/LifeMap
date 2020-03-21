package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.PropertyConfig
import com.dominicwrieden.api.model.Response
import io.reactivex.Single

interface PropertyConfigRepository {

    fun savePropertyConfig(propertyConfig: PropertyConfig)

    fun savePropertyConfigs(propertyConfigs: List<PropertyConfig>)

    fun getPropertyConfigs(): Single<Response<List<PropertyConfig>>>
}