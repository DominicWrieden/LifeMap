package com.dominicwrieden.data.util

import android.content.Context
import java.io.File
import java.io.InputStream

class FileManager(private val context: Context) {

    companion object {
        private const val  FILE_DIR_NAME = "lifemap_files"
    }

    fun saveFile(fileName: String, inputStream: InputStream) {
        val fileDir = context.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE)

        if (!fileDir.exists()) {
            fileDir.mkdir()
        }

        File(fileDir, fileName).outputStream().use {
            inputStream.copyTo(it)
        }

        inputStream.close()
    }

    fun getFile(fileName: String): File = File(context.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE),fileName)
}