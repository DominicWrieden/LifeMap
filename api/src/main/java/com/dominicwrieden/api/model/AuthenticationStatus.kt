package com.dominicwrieden.api.model

sealed class AuthenticationStatus {
    data class LoggedIn(
        val firstName: String,
        val lastName: String,
        val username: String
    ) : AuthenticationStatus()

    object LoggedOut : AuthenticationStatus()
}