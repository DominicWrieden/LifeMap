package com.dominicwrieden.data.repository.state

import com.dominicwrieden.data.model.Task
import io.reactivex.rxjava3.core.Single

interface StateRepository {

    fun downloadStates(): Single<Task>
}