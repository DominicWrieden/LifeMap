package com.dominicwrieden.data.model

import org.threeten.bp.OffsetDateTime


/**
 * Item model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * Item will represent for example a finding of a bird nest or plant
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class Item(
    val localId: Int,
    val remoteId: String?,
    val area: Area,
    val createDate: OffsetDateTime,
    val reporter: User,
    val history: List<ItemEntry>

)