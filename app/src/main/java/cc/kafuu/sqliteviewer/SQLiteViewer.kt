package cc.kafuu.sqliteviewer

import android.app.Application
import cc.kafuu.sqliteviewer.common.utils.CommonLibs

class SQLiteViewer : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonLibs.init(this)
    }
}