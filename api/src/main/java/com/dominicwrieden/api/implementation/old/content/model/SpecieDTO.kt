package com.dominicwrieden.api.implementation.old.content.model

import com.dominicwrieden.api.model.ItemType

internal data class SpecieDTO(
    val _id: String,
    val name: String,
    val description: String?,
    val __v: Int,
    val abbreviation: String?,
    val createdBy: CreatedByDTO?
)

internal data class CreatedByDTO(
    val email: String,
    val firstName: String,
    val lastName: String
)

internal fun SpecieDTO.toItemType() = ItemType(
    remoteId = this._id,
    name = this.name,
    description = this.description
)