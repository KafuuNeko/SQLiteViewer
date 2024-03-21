package cc.kafuu.sqliteviewer.common.model

import cc.kafuu.sqliteviewer.common.utils.bytesCountToReadableFileSize
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

data class SQLiteFile(val name: String, val file: File, val tableCount: Int, val viewCount: Int) {
    fun getFileDetails(): String {
        val fileSize = file.length().bytesCountToReadableFileSize()
        val lastModified = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(file.lastModified())
        return "$fileSize $lastModified"
    }
}