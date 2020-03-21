package com.dominicwrieden.data.model

import org.threeten.bp.OffsetDateTime



/**
 * Property* model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * This represents a String|Int|Date property, which can be edited while an
 * assessment {@link com.dominicwrieden.data.model.ItemEntry}. It is corresponding to the
 * configured property from the server {@link com.dominicwrieden.data.model.PropertyConfig}.
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */

interface Property<out T> {
    val localId: Int
    val propertyType: PropertyConfig
    val value: T
}

data class PropertyString(
    override val localId: Int,
    override val propertyType: PropertyConfig,
    override val value: String
): Property<String>

data class PropertyInt(
    override val localId: Int,
    override val propertyType: PropertyConfig,
    override val value: Int
): Property<Int>

data class PropertyDate(
    override val localId: Int,
    override val propertyType: PropertyConfig,
    override val value: OffsetDateTime
): Property<OffsetDateTime>