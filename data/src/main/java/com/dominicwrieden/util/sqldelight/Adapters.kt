package com.dominicwrieden.util.sqldelight

import android.location.Location
import android.net.Uri
import com.dominicwrieden.api.model.PropertyType
import com.squareup.sqldelight.ColumnAdapter
import org.threeten.bp.OffsetDateTime

class OffsetDateTimeColumnAdapter: ColumnAdapter<OffsetDateTime, String> {
    override fun decode(databaseValue: String) = OffsetDateTime.parse(databaseValue)
    override fun encode(value: OffsetDateTime) = value.toString()
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
