package com.dominicwrieden.api.implementation.old.authentication.response

data class LoginResponse(
    val user: User,
    val token: String,
    val expirationDate: Long
)

data class User(
    val _id: String,
    val email: String,
    val firstName: String,
    val abbreviation: String?,
    val loggedIn: Boolean,
    val role: String,
    val activated: Boolean,
    val __v: Int,
    val permittedAreas: List<String>
)
