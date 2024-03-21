package cc.kafuu.sqliteviewer.common.utils

import android.database.sqlite.SQLiteDatabase
import java.io.Closeable
import java.io.File

class SQLiteUtils(file: File) : Closeable {
    companion object {
        const val ENTITY_TABLE = "table"
        const val ENTITY_VIEW = "view"
    }

    private val sqlite = SQLiteDatabase.openOrCreateDatabase(file, null)
    override fun close() {
        if (sqlite.isOpen) {
            sqlite.close()
        }
    }

    fun getEntityCount(type: String): Int {
        sqlite.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type=?", arrayOf(type)).use {
            it.moveToFirst()
            return it.getInt(0)
        }
    }
}