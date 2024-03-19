package cc.kafuu.sqliteviewer.common.utils

import android.database.sqlite.SQLiteDatabase
import java.io.Closeable

class SQLiteUtils(path: String) : Closeable {
    private val sqlite = SQLiteDatabase.openOrCreateDatabase(path, null)
    override fun close() {
        if (sqlite.isOpen) {
            sqlite.close()
        }
    }


}