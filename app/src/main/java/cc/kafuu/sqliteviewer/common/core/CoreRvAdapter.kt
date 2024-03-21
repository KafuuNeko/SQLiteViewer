package cc.kafuu.sqliteviewer.common.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import cc.kafuu.sqliteviewer.common.utils.CommonLibs

abstract class CoreRvAdapter<V : ViewDataBinding, VM : CoreViewModel>(
    private val viewModelId: Int,
    private val dataVariableId: Int,
    protected val mViewModel: VM? = null
) : RecyclerView.Adapter<CoreViewHolder>() {

    private val mItemListeners: MutableList<ICoreItemListener> = mutableListOf()

    protected abstract fun getItemData(position: Int): Any
    protected abstract fun getItemLayoutId(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(CommonLibs.context),
            getItemLayoutId(viewType), parent, false
        )
        return CoreViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: CoreViewHolder, position: Int) {
        if (viewModelId != 0 && mViewModel != null) {
            holder.binding.setVariable(viewModelId, mViewModel)
        }

        if (dataVariableId != 0) {
            holder.binding.setVariable(dataVariableId, getItemData(position))
        }

        holder.binding.root.setOnClickListener {
            for (listener in mItemListeners) {
                if (listener.onItemClick(position, getItemData(position))) {
                    break
                }
            }
        }
    }

    override fun getItemId(position: Int) = position.toLong()

    fun registerItemListener(listener: ICoreItemListener) {
        if (!mItemListeners.contains(listener)) {
            mItemListeners.add(listener)
        }
    }

    fun unregisterItemListener(listener: ICoreItemListener) {
        mItemListeners.remove(listener)
    }
}