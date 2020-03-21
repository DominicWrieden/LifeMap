package com.dominicwrieden.api.implementation.old.content.response

import com.dominicwrieden.api.model.User

data class UserDTO(
    val _id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val abbreviation: String,
    val loggedIn: Boolean,
    val role: String,
    val __v: Int,
    val activated: Boolean,
    val permittedAreas: List<String>
)


fun UserDTO.toUser() = User(
    remoteId = this._id,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    permittedAreaIds = this.permittedAreas
)
