package cc.kafuu.sqliteviewer.common.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

data class CoreViewHolder(var binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)