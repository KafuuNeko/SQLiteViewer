package cc.kafuu.sqliteviewer.common.utils

import android.provider.DocumentsContract
import java.io.File
import java.util.Locale

object MimeTypeUtils {

    private val EXTENSION_TO_MIME_TYPE_MAP = mapOf(
        "txt" to "text/plain",
        "html" to "text/html",
        "htm" to "text/html",
        "jpg" to "image/jpeg",
        "jpeg" to "image/jpeg",
        "png" to "image/png",
        "gif" to "image/gif",
        "pdf" to "application/pdf",
        "doc" to "application/msword",
        "docx" to "application/msword",
        "xls" to "application/vnd.ms-excel",
        "xlsx" to "application/vnd.ms-excel",
        "ppt" to "application/vnd.ms-powerpoint",
        "pptx" to "application/vnd.ms-powerpoint",
        "zip" to "application/zip",
        "rar" to "application/x-rar-compressed",
        "sqlite" to "application/x-sqlite3",
        "sdb" to "application/x-sqlite3",
        "db" to "application/x-sqlite3"
    )

    fun getTypeForFileBySuffix(file: File): String {
        if (!file.isFile) {
            return DocumentsContract.Document.MIME_TYPE_DIR
        }
        val extension = file.name.substringAfterLast('.', "").lowercase(Locale.ROOT)
        return EXTENSION_TO_MIME_TYPE_MAP[extension] ?: "application/octet-stream"
    }
}
