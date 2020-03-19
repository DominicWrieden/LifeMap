package com.dominicwrieden.data.model

import org.threeten.bp.OffsetDateTime

data class Area(
    val remoteId: String,
    val name: String,
    val geoDbId: String,
    val geoDbName: String,
    val geoDbFilePath: String?,
    val geoDbCreateDate: OffsetDateTime
)