package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.State
import io.reactivex.Single

interface StateRepository {

    fun saveState(state: State)

    fun saveStates(states: List<State>)

    fun getStates(): Single<Response<List<State>>>
}