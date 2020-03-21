package com.dominicwrieden.data.model

/**
 * User model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * Represents a user of the project with given permitted areas, on which she/he can work with.
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class User(
    val remoteId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val permittedAreas: List<Area>
)