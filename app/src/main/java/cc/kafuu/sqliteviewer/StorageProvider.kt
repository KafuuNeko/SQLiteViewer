package cc.kafuu.sqliteviewer

import android.database.Cursor
import android.database.MatrixCursor
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.DocumentsProvider
import android.util.Log
import cc.kafuu.sqliteviewer.common.utils.CommonLibs
import java.io.File
import java.io.FileNotFoundException
import java.util.Locale

class StorageProvider : DocumentsProvider() {
    companion object {
        private const val TAG = "StorageProvider"

        private val defaultDocumentProjection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_FLAGS,
            DocumentsContract.Document.COLUMN_SIZE,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_LAST_MODIFIED
        )

        private val defaultRootProjection = arrayOf(
            DocumentsContract.Root.COLUMN_ROOT_ID,
            DocumentsContract.Root.COLUMN_TITLE,
            DocumentsContract.Root.COLUMN_FLAGS,
            DocumentsContract.Root.COLUMN_DOCUMENT_ID,
            DocumentsContract.Root.COLUMN_ICON
        )
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun queryRoots(projection: Array<out String>?): Cursor {
        Log.d(TAG, "queryRoots: ")
        val result = MatrixCursor(projection ?: defaultRootProjection)

        result.newRow().apply {
            add(DocumentsContract.Root.COLUMN_ROOT_ID, "/share")
            add(DocumentsContract.Root.COLUMN_TITLE, CommonLibs.getString(R.string.app_name))
            add(DocumentsContract.Root.COLUMN_FLAGS, DocumentsContract.Root.FLAG_SUPPORTS_CREATE or DocumentsContract.Root.FLAG_SUPPORTS_RECENTS or DocumentsContract.Root.FLAG_SUPPORTS_SEARCH)
            add(DocumentsContract.Root.COLUMN_DOCUMENT_ID, "/share")
            add(DocumentsContract.Root.COLUMN_ICON, R.drawable.ic_launcher)
        }

        return result
    }


    override fun queryDocument(documentId: String?, projection: Array<out String>?) =
        MatrixCursor(projection ?: defaultDocumentProjection).apply {
            if (documentId == null) {
                return@apply
            }
            val file = File(CommonLibs.rootDir, documentId)
            Log.d(TAG, "queryDocument: $file")
            newRow().apply {
                fileRow(file, "$documentId")
            }
        }


    override fun queryChildDocuments(
        parentDocumentId: String?,
        projection: Array<out String>?,
        sortOrder: String?
    ) = MatrixCursor(projection ?: defaultDocumentProjection).apply {
        if (parentDocumentId == null) {
            return@apply
        }
        Log.d(TAG, "queryChildDocuments: $parentDocumentId")
        File(CommonLibs.rootDir, parentDocumentId).listFiles()?.forEach {
            newRow().apply {
                fileRow(it, "$parentDocumentId/${it.name}")
            }
        }
    }

    override fun openDocument(
        documentId: String,
        mode: String,
        signal: CancellationSignal?
    ): ParcelFileDescriptor {
        val file = File(CommonLibs.rootDir, documentId)

        if (mode.contains("w") && !file.exists()) {
            file.createNewFile()
        }

        return ParcelFileDescriptor.open(file, modeToAccessMode(mode))
    }

    override fun createDocument(
        parentDocumentId: String,
        mimeType: String,
        displayName: String
    ): String {
        val parentFile = File(CommonLibs.rootDir, parentDocumentId)
        if (!parentFile.isDirectory) {
            throw FileNotFoundException("Parent document is not a directory")
        }
        val file = File(parentFile, displayName)
        if (DocumentsContract.Document.MIME_TYPE_DIR == mimeType) {
            file.mkdir()
        } else {
            file.createNewFile()
        }
        return "${parentDocumentId}/${displayName}"
    }


    override fun deleteDocument(documentId: String) {
        val file = File(CommonLibs.rootDir, documentId)
        if (!file.delete()) {
            throw FileNotFoundException("Failed to delete file: $documentId")
        }
    }


    private fun modeToAccessMode(mode: String): Int {
        var accessMode = ParcelFileDescriptor.MODE_READ_ONLY
        if (mode.contains("w")) {
            accessMode = ParcelFileDescriptor.MODE_READ_WRITE
            if (mode.contains("a")) {
                accessMode = accessMode or ParcelFileDescriptor.MODE_APPEND
            }
            if (mode.contains("t")) {
                accessMode = accessMode or ParcelFileDescriptor.MODE_TRUNCATE
            }
        }
        return accessMode
    }


    private fun getTypeForFile(file: File): String {
        if (!file.isFile) {
            return DocumentsContract.Document.MIME_TYPE_DIR
        }
        val name = file.name.lowercase(Locale.ROOT)
        return when {
            name.endsWith(".txt") -> "text/plain"
            name.endsWith(".html") || name.endsWith(".htm") -> "text/html"
            name.endsWith(".jpg") || name.endsWith(".jpeg") -> "image/jpeg"
            name.endsWith(".png") -> "image/png"
            name.endsWith(".gif") -> "image/gif"
            name.endsWith(".pdf") -> "application/pdf"
            name.endsWith(".doc") || name.endsWith(".docx") -> "application/msword"
            name.endsWith(".xls") || name.endsWith(".xlsx") -> "application/vnd.ms-excel"
            name.endsWith(".ppt") || name.endsWith(".pptx") -> "application/vnd.ms-powerpoint"
            name.endsWith(".zip") -> "application/zip"
            name.endsWith(".rar") -> "application/x-rar-compressed"
            name.endsWith(".sqlite") || name.endsWith(".sdb") || name.endsWith(".db") -> "application/x-sqlite3"
            else -> "application/octet-stream"
        }
    }

    private fun MatrixCursor.RowBuilder.fileRow(file: File, documentId: String) {
        val flags = DocumentsContract.Document.FLAG_SUPPORTS_COPY
            .or(DocumentsContract.Document.FLAG_DIR_SUPPORTS_CREATE)
            .or(DocumentsContract.Document.FLAG_SUPPORTS_WRITE)
            .or(DocumentsContract.Document.FLAG_SUPPORTS_DELETE)
            .or(DocumentsContract.Document.FLAG_SUPPORTS_RENAME)
            .or(DocumentsContract.Document.FLAG_SUPPORTS_MOVE)
            .or(DocumentsContract.Document.FLAG_SUPPORTS_REMOVE)

        add(DocumentsContract.Document.COLUMN_DOCUMENT_ID, documentId)
        add(DocumentsContract.Document.COLUMN_MIME_TYPE, getTypeForFile(file))
        add(DocumentsContract.Document.COLUMN_FLAGS, flags)
        add(DocumentsContract.Document.COLUMN_DISPLAY_NAME, file.name)
        add(DocumentsContract.Document.COLUMN_LAST_MODIFIED, file.lastModified())
        add(DocumentsContract.Document.COLUMN_SIZE, if (file.isFile) file.length() else 0)
    }

}