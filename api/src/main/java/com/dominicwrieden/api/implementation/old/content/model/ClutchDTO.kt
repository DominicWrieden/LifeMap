package com.dominicwrieden.api.implementation.old.content.model

import android.location.Location
import com.dominicwrieden.api.model.*
import org.threeten.bp.OffsetDateTime


internal data class ClutchDTO(
    val _id : String,
    val id : String,
    val numberOfEggs : Int,
    val reportDate : OffsetDateTime,
    val note : String,
    val location : List<Double>,
    val reportedBy : ClutchReporterDTO,
    val species : ClutchSpecieDTO,
    val status : ClutchStatusDTO,
    val dateOfHatching: OffsetDateTime?,
    val area : ClutchAreaDTO,
    val __v : Int,
    val imageUrls : List<String>

)

internal data class ClutchReporterDTO(
    val email:String,
    val firstName: String,
    val lastName: String
)

internal data class ClutchSpecieDTO(
    val _id: String,
    val name: String,
    val description:String
)

internal data class ClutchStatusDTO(
    val _id: String,
    val name: String,
    val description: String
)

internal data class ClutchAreaDTO(
    val _id: String,
    val name: String
)

internal fun convertClutchHistoryToItem(
    clutchHistories: List<ClutchDTO>,
    users: List<User>
): Item? {
    val firstItem = clutchHistories.minByOrNull { it.reportDate.toEpochSecond() }

    return firstItem?.let {
        Item(
            localId = null,
            remoteId = firstItem.id,
            areaId = firstItem.area._id,
            createDate = firstItem.reportDate,
            reporterId = users.firstOrNull { user -> user.email == firstItem.reportedBy.email }?.remoteId
                ?: "",
            history = clutchHistories.map { clutchHistory ->
                ItemEntry(
                    localId = null,
                    itemId = null,
                    remoteId = clutchHistory._id,
                    reporterId = users.firstOrNull { user -> user.email == clutchHistory.reportedBy.email }?.remoteId
                        ?: "",
                    reportDate = clutchHistory.reportDate,
                    itemTypeId = clutchHistory.species._id,
                    location = Location("").apply {
                        longitude = clutchHistory.location[0]
                        latitude = clutchHistory.location[1]
                    },
                    stateId = clutchHistory.status._id,
                    note = clutchHistory.note,
                    photoIds = clutchHistory.imageUrls,
                    properties = ArrayList<Property<Any>>().apply {
                        if (clutchHistory.dateOfHatching != null) {
                            this.add(
                                PropertyDate(
                                    localId = null,
                                    itemEntryId = null,
                                    propertyConfigId = "dateOfHatching",
                                    value = clutchHistory.dateOfHatching
                                )
                            )
                        }

                        this.add(
                            PropertyInt(
                                localId = null,
                                itemEntryId = null,
                                propertyConfigId = "numberOfEggs",
                                value = clutchHistory.numberOfEggs
                            )
                        )
                    }
                )
            }
        )
    }
}