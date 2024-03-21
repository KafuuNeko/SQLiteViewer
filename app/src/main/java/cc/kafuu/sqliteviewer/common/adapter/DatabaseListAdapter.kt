package cc.kafuu.sqliteviewer.common.adapter

import cc.kafuu.sqliteviewer.BR
import cc.kafuu.sqliteviewer.R
import cc.kafuu.sqliteviewer.common.core.CoreRvAdapter
import cc.kafuu.sqliteviewer.common.core.CoreViewModel
import cc.kafuu.sqliteviewer.common.model.SQLiteFile
import cc.kafuu.sqliteviewer.databinding.ItemDatabaseBinding

class DatabaseListAdapter :
    CoreRvAdapter<ItemDatabaseBinding, CoreViewModel>(BR.viewModel, BR.data) {
    var sqliteFiles: List<SQLiteFile>? = null

    override fun getItemData(position: Int): Any =
        sqliteFiles?.get(position) ?: throw NullPointerException("sqliteFiles is null")

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_database

    override fun getItemCount(): Int = sqliteFiles?.size ?: 0
}