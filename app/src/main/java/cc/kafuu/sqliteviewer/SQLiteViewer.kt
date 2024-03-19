package cc.kafuu.sqliteviewer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cc.kafuu.sqliteviewer.common.utils.CommonLibs

class SQLiteViewer: Application() {
    override fun onCreate() {
        super.onCreate()
        CommonLibs.init(this)
    }
}