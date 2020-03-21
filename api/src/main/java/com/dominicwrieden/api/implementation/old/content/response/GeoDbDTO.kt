package com.dominicwrieden.api.implementation.old.content.response

import org.threeten.bp.OffsetDateTime


data class GeoDbDTO(
    val _id: String,
    val name: String,
    val filePath: String,
    val dateCreated: OffsetDateTime
)