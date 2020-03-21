package com.dominicwrieden.data.model

import android.location.Location
import org.threeten.bp.OffsetDateTime


/**
 * Item entry model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * ItemEntry will represent an historical assessment of an item {@link com.dominicwrieden.data.model.Item} (e.g. finding of a bird nest)
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class ItemEntry(
    val localId: Int,
    val remoteId: String?,
    val reporter: User,
    val createDate: OffsetDateTime,
    val itemType: ItemType?,
    val location: Location?,
    val state: State?,
    val note: String?,
    val photos: List<Photo>,
    val properties: List<Property<Any>>
)