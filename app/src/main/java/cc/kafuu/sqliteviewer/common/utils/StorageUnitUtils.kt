package cc.kafuu.sqliteviewer.common.utils

import kotlin.math.log10
import kotlin.math.pow

/**
 * 将字节数转换为易读形式的文本 */
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
 * 将千字节数转换为易读形式的文本 */
fun Long.kBytesCountToReadableFileSize(): String {
    if (this <= 0) return "0 KB"
    val units = arrayOf("KB", "MB", "GB", "TB", "PB", "EB")
    var size = this.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }

    return String.format("%.2f %s", size, units[unitIndex])
}