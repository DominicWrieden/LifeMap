package com.dominicwrieden.data.repository.propertyconfig

import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface PropertyConfigRepository {

    fun downloadPropertyConfigs(): Single<Task>
}