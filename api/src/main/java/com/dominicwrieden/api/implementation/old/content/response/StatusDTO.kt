package com.dominicwrieden.api.implementation.old.content.response

import com.dominicwrieden.api.model.State

data class StatusDTO(
    val _id: String,
    val name: String,
    val description: String,
    val __v: Int
)


fun StatusDTO.toState() =
    State(
        remoteId = this._id,
        name = this.name,
        description = this.description
    )