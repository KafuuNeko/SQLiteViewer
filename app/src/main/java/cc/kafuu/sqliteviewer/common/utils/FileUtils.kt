package cc.kafuu.sqliteviewer.common.utils

import android.net.Uri
import android.provider.OpenableColumns
import kotlin.math.log10
import kotlin.math.pow

object FileUtils {

    /**
     * 将字节数转换为易读形式的文本
     */
    fun Long.bytesCountToReadableFileSize(): String {
        if (this <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB", "PB", "EB")
        val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt()
        return String.format(
            "%.2f %s",
            this / 1024.0.pow(digitGroups.toDouble()),
            units[digitGroups]
        )
    }

    /**
     * 从 Uri 获取文件名
     */
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
}
