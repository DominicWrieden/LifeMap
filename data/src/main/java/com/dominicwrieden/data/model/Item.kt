package com.dominicwrieden.data.model

import org.threeten.bp.OffsetDateTime

data class Item(
    val localId: Int,
    val remoteId: String?,
    val area: Area,
    val createDate: OffsetDateTime,
    val reporter: User,
    val history: List<ItemEntry>

)