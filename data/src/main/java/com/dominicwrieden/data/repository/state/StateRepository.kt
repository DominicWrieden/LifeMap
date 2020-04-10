package com.dominicwrieden.data.repository.state

import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface StateRepository {

    fun downloadStates(): Single<Task>
}