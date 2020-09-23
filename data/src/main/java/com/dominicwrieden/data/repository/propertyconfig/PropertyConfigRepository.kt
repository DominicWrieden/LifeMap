package com.dominicwrieden.data.repository.propertyconfig

import com.dominicwrieden.data.model.Task
import io.reactivex.rxjava3.core.Single

interface PropertyConfigRepository {

    fun downloadPropertyConfigs(): Single<Task>
}