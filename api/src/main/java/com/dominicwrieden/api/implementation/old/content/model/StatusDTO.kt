package com.dominicwrieden.api.implementation.old.content.model

import com.dominicwrieden.api.model.State

internal data class StatusDTO(
    val _id: String,
    val name: String,
    val description: String,
    val __v: Int
)


internal fun StatusDTO.toState() =
    State(
        remoteId = this._id,
        name = this.name,
        description = this.description
    )