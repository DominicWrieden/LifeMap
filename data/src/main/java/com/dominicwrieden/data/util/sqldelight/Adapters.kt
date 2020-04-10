package com.dominicwrieden.data.util.sqldelight

import android.location.Location
import android.net.Uri
import com.dominicwrieden.api.model.PropertyType
import com.squareup.sqldelight.ColumnAdapter
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class OffsetDateTimeColumnAdapter: ColumnAdapter<OffsetDateTime, Long> {
    override fun decode(databaseValue: Long) = OffsetDateTime.ofInstant(Instant.ofEpochSecond(databaseValue), ZoneOffset.UTC)
    override fun encode(value: OffsetDateTime) = value.toEpochSecond()
}

class PropertyTypeColumnAdapter : ColumnAdapter<PropertyType, String> {
    override fun decode(databaseValue: String) = PropertyType.valueOf(databaseValue)
    override fun encode(value: PropertyType) = value.name
}

class LocationColumnAdapter : ColumnAdapter<Location, String> {
    override fun decode(databaseValue: String) = Location("").apply {
        val location: List<String> = databaseValue.split(",")
        latitude = location[0].toDouble()
        longitude = location[1].toDouble()
    }
    override fun encode(value: Location) = "${value.latitude},${value.longitude}"
}

class UriColumnAdapter : ColumnAdapter<Uri, String> {
    override fun decode(databaseValue: String) = Uri.parse(databaseValue)
    override fun encode(value: Uri) = value.toString()
}
