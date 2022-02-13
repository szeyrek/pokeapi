package com.scz.globallogic.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.scz.globallogic.BR
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<BINDING : ViewDataBinding, T>(var data: List<T>) :
    RecyclerView.Adapter<BaseViewHolder<BINDING, T>>() {
    @get:LayoutRes
    protected abstract val layoutId: Int

    open var onItemClickListener: ((Int, T) -> Unit)? = null
    open fun onItemBinding(holder: BaseViewHolder<BINDING, T>, position: Int) {}

    fun updateData(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BINDING, T> {
        val binding = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BINDING, T>, position: Int) {
        data.getOrNull(position)?.let { item ->
            holder.apply {
                bindItem(item)
                onItemBinding(holder, position)
                itemView.setOnClickListener {
                    onItemClickListener?.invoke(position, item)
                }
            }
        }
    }
}

class BaseViewHolder<BINDING : ViewDataBinding, T>(val binding: BINDING) :
    RecyclerView.ViewHolder(binding.root) {

    var onItemBinding: ((BINDING) -> Unit)? = null

    fun bindItem(item: T) {
        binding.setVariable(BR.item, item)
        onItemBinding?.invoke(binding)
        binding.executePendingBindings()
    }
}