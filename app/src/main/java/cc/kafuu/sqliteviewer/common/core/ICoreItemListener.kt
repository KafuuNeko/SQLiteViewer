package cc.kafuu.sqliteviewer.common.core

interface ICoreItemListener {
    fun onItemClick(position: Int, any: Any): Boolean
}