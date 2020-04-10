package com.dominicwrieden.api.implementation.old.content.model

import com.dominicwrieden.api.model.Area


internal data class AreaDTO (
    val _id : String,
     val name : String,
    val __v : Int,
    val geoDBs : List<GeoDbDTO>
)

internal fun AreaDTO.toArea() = Area(
    remoteId = this._id,
    name = this.name,
    geoDbId = this.geoDBs.first()._id,
    geoDbName = this.geoDBs.first().name,
    geoDbFilePath = this.geoDBs.first().filePath,
    geoDbCreateDate = this.geoDBs.first().dateCreated
)