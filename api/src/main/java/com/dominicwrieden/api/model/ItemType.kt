package com.dominicwrieden.api.model


/**
 * Item type model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * ItemType will represent an type of an item {@link com.dominicwrieden.api.model.Item} e.g. a specie of a bird.
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class ItemType(
    val remoteId: String,
    val name: String,
    val description: String?
)