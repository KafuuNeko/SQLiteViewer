package cc.kafuu.sqliteviewer.common.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

@SuppressLint("StaticFieldLeak")
object CommonLibs {
    private lateinit var mContext: Context
    fun init(context: Context) {
        mContext = context
    }

    fun requestContext(): Context {
        return mContext
    }

    fun getString(@StringRes id: Int) = requestContext().resources?.getString(id).toString()
    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(requestContext(), color)
    fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(requestContext(), id)
}