package com.dominicwrieden.data.repository.state

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.api.model.State
import com.dominicwrieden.data.model.toTask

class StateRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) :
    StateRepository {


    override fun downloadStates() = api.getStates()
        .doOnSuccess { stateResponse ->
            stateResponse?.let {
                when (it) {
                    is Response.Success -> saveStates(it.body)
                }
            }
        }
        .toTask()

    private fun saveState(state: State) {
        saveStates(listOf(state))
    }

    private fun saveStates(states: List<State>) {
        database.transaction {
            states.forEach { state ->
                database.insertState(
                    remoteIdState = state.remoteId,
                    name = state.name,
                    description = state.description
                )
            }
        }
    }


}