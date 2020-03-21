package com.dominicwrieden.api.model

import android.location.Location
import com.dominicwrieden.api.model.Property
import org.threeten.bp.OffsetDateTime


/**
 * Item entry model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * ItemEntry will represent an historical assessment of an item {@link com.dominicwrieden.api.model.Item} (e.g. finding of a bird nest)
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class ItemEntry(
    val localId: Long?,
    val remoteId: String?,
    val itemId: String,
    val reporterId: String,
    val reportDate: OffsetDateTime,
    val itemTypeId: String?,
    val location: Location?,
    val stateId: String?,
    val note: String?,
    val photoIds: List<String>,
    val properties: List<com.dominicwrieden.api.model.Property<Any>>
)