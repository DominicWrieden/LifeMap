package com.dominicwrieden.api.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.OffsetDateTime

object MoshiAdapters {


    @FromJson
    fun offsetDateTimeFromJson(value: String): OffsetDateTime {
        return OffsetDateTime.parse(value)
    }

    @ToJson
    fun offsetDateTimeToJson(value: OffsetDateTime): String {
        return value.toInstant().toString()
    }
}