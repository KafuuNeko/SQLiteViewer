package cc.kafuu.sqliteviewer.common.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.OpenableColumns

@SuppressLint("Range")
fun Uri.getFileName(): String? {
    CommonLibs.context.contentResolver.query(
        this,
        arrayOf(OpenableColumns.DISPLAY_NAME),
        null,
        null,
        null
    )?.use {
        if (it.moveToFirst()) {
            return it.getString(0)
        }
    }
    return null
}