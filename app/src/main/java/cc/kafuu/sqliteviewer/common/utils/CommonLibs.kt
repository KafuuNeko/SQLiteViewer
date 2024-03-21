package cc.kafuu.sqliteviewer.common.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.io.File

@SuppressLint("StaticFieldLeak")
object CommonLibs {
    private lateinit var mContext: Context
    val context: Context get() = mContext

    val rootDir get() = context.getExternalFilesDir(null) ?: throw NullPointerException()
    val sqliteDir get() = getDir("share/sqlite")

    fun init(context: Context) {
        mContext = context
    }

    fun getString(@StringRes id: Int) = context.resources?.getString(id).toString()
    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(context, color)
    fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)

    private fun getDir(child: String) = File(rootDir, child).also { if (!it.exists()) it.mkdirs() }
}