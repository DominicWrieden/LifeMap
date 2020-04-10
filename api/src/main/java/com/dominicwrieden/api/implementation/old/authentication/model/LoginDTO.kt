package com.dominicwrieden.api.implementation.old.authentication.model

import com.dominicwrieden.api.model.User

internal data class LoginResponse(
    val user: LoginUserDTO,
    val token: String,
    val expirationDate: Long
)

internal data class LoginUserDTO(
    val _id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val abbreviation: String?,
    val loggedIn: Boolean,
    val role: String,
    val activated: Boolean,
    val __v: Int,
    val permittedAreas: List<String>
)

internal fun LoginUserDTO.toUser() = User(
    remoteId = this._id,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    permittedAreaIds = this.permittedAreas
)



