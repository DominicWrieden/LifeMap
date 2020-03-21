package com.dominicwrieden.data.model

/**
 * State model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * State represents the current status of an Item {@link com.dominicwrieden.data.model.Item},
 * while an assessment {@link com.dominicwrieden.data.model.ItemEntry}
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class State(
    val remoteId: String,
    val name: String,
    val description: String
)