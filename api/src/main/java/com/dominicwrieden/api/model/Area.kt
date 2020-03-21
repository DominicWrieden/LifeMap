package com.dominicwrieden.api.model

import org.threeten.bp.OffsetDateTime

/**
 * Area model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * Area will provide a map in form of a ArcGIS or Open-Street-Map.
 * A project can have multiple areas.
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class Area(
    val remoteId: String,
    val name: String,
    val geoDbId: String,
    val geoDbName: String,
    val geoDbFilePath: String?,
    val geoDbCreateDate: OffsetDateTime
)