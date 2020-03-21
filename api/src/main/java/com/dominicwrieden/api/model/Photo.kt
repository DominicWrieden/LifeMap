package com.dominicwrieden.api.model

import android.net.Uri

/**
 * Photo model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * Photo represents a photo, take while an assessment {@link com.dominicwrieden.api.model.ItemEntry} of an Item
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class Photo(
    val localId: Long,
    val remoteId: String?,
    val fileName: String?, //if it's on the server
    val filePath: Uri? //if it's on the device
)