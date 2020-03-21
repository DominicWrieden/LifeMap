package com.dominicwrieden.api.implementation.old.content.response

import com.dominicwrieden.api.model.ItemType

data class SpecieDTO(
    val _id: String,
    val name: String,
    val description: String?,
    val __v: Int,
    val abbreviation: String?,
    val createdBy: CreatedByDTO?
)

data class CreatedByDTO(
    val email: String,
    val firstName: String,
    val lastName: String
)

fun SpecieDTO.toItemType() = ItemType(
    remoteId = this._id,
    name = this.name,
    description = this.description
)