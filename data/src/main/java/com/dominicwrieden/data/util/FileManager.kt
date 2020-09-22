package com.dominicwrieden.data.util

import android.content.Context
import java.io.File

class FileManager(private val context: Context) {

    companion object {
        private const val  FILE_DIR_NAME = "lifemap"
    }

    fun saveFile(fileName: String, file: File) {
        val fileDir = context.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE)

        if (!fileDir.exists()) {
            fileDir.mkdir()
        }

        File(fileDir, fileName).outputStream().use {
            it.write(file.readBytes())
        }

        file.deleteOnExit()
        file.delete()
    }

    fun getFile(fileName: String): File = File(context.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE),fileName)
}