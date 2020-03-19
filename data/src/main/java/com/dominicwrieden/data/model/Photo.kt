package com.dominicwrieden.data.model

import android.net.Uri

data class Photo(
    val localId: Int,
    val remoteId: String?,
    val fileName: String?, //if it's on the server
    val filePath: Uri? //if it's on the device
)