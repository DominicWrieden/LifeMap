package com.dominicwrieden.data.model

import org.threeten.bp.OffsetDateTime

interface Property<out T> {
    val localId: Int
    val propertyType: PropertyType
    val value: T
}

data class PropertyString(
    override val localId: Int,
    override val propertyType: PropertyType,
    override val value: String
): Property<String>

data class PropertyInt(
    override val localId: Int,
    override val propertyType: PropertyType,
    override val value: Int
): Property<Int>

data class PropertyDate(
    override val localId: Int,
    override val propertyType: PropertyType,
    override val value: OffsetDateTime
): Property<OffsetDateTime>