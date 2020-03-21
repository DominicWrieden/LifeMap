package com.dominicwrieden.util.sqldelight

import android.location.Location
import android.net.Uri
import com.squareup.sqldelight.ColumnAdapter
import org.threeten.bp.OffsetDateTime

class OffsetDateTimeColumnAdapter: ColumnAdapter<OffsetDateTime, String> {
    override fun decode(databaseValue: String) = OffsetDateTime.parse(databaseValue)
    override fun encode(value: OffsetDateTime) = value.toString()
}

class LocationColumnAdapter : ColumnAdapter<Location, String> {
    override fun decode(databaseValue: String) = Location(databaseValue.split(",")[0]).apply {
        val location: List<String> = databaseValue.split(",")
        latitude = location[1].toDouble()
        longitude = location[2].toDouble()
    }
    override fun encode(value: Location) = "${value.provider},${value.latitude},${value.longitude}"
}

class UriColumnAdapter : ColumnAdapter<Uri, String> {
    override fun decode(databaseValue: String) = Uri.parse(databaseValue)
    override fun encode(value: Uri) = value.toString()
}
