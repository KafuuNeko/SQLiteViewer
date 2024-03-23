package cc.kafuu.sqliteviewer.common.adapter

import cc.kafuu.sqliteviewer.BR
import cc.kafuu.sqliteviewer.R
import cc.kafuu.sqliteviewer.common.core.CoreRvAdapter
import cc.kafuu.sqliteviewer.common.core.CoreViewHolder
import cc.kafuu.sqliteviewer.common.core.CoreViewModel
import cc.kafuu.sqliteviewer.common.model.SQLiteFile
import cc.kafuu.sqliteviewer.databinding.ItemDatabaseBinding

class DatabaseListAdapter :
    CoreRvAdapter<ItemDatabaseBinding, CoreViewModel>(BR.viewModel, BR.data) {
    companion object {
        interface IClickedListener {
            fun onDelete(data: SQLiteFile)
            fun onRename(data: SQLiteFile)
            fun onSaveAs(data: SQLiteFile)
            fun onContent(data: SQLiteFile)
        }
    }

    var clickedListener: IClickedListener? = null
    var sqliteFiles: List<SQLiteFile>? = null

    override fun getItemData(position: Int): Any =
        sqliteFiles?.get(position) ?: throw NullPointerException("sqliteFiles is null")

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_database

    override fun getItemCount(): Int = sqliteFiles?.size ?: 0

    override fun onBindViewHolder(holder: CoreViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        sqliteFiles?.get(position)?.let {
            (holder.binding as ItemDatabaseBinding).initListener(it)
        }
    }

    private fun ItemDatabaseBinding.initListener(data: SQLiteFile) {
        llDelete.setOnClickListener {
            sdItem.closeDrawer()
            clickedListener?.onDelete(data)
        }

        llRename.setOnClickListener {
            sdItem.closeDrawer()
            clickedListener?.onRename(data)
        }

        llSaveAs.setOnClickListener {
            sdItem.closeDrawer()
            clickedListener?.onSaveAs(data)
        }

        llContent.setOnClickListener {
            sdItem.closeDrawer()
            clickedListener?.onContent(data)
        }
    }
}