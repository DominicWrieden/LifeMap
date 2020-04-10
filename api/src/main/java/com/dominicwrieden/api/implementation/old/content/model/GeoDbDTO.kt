package com.dominicwrieden.api.implementation.old.content.model

import org.threeten.bp.OffsetDateTime


internal data class GeoDbDTO(
    val _id: String,
    val name: String,
    val filePath: String,
    val dateCreated: OffsetDateTime
)