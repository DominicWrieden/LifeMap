package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.State
import com.dominicwrieden.data.repository.`interface`.StateRepository

class StateRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) : StateRepository {


    override fun getStates() = api.getStates()

    override fun saveState(state: State) {
        saveStates(listOf(state))
    }

    override fun saveStates(states: List<State>) {
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