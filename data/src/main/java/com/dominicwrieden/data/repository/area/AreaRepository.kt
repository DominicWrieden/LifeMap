package com.dominicwrieden.data.repository.area

import com.dominicwrieden.api.model.Area
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface AreaRepository {

    fun downloadAreas(): Single<Task>


    fun getAreas(): Single<Response<List<Area>>>
}