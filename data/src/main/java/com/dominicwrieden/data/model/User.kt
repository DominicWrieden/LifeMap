package com.dominicwrieden.data.model

data class User(
    val remoteId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val permittedAreas: List<Area>
)