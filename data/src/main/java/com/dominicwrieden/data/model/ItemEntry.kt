package com.dominicwrieden.data.model

import android.location.Location
import org.threeten.bp.OffsetDateTime

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