package com.kotlin.lib.base.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<T : Any>(
    DataDifferntiator: DiffUtil.ItemCallback<T>,
    val layoutId: Int
) :
    PagingDataAdapter<T, BasePagingAdapter.ViewHolder>(DataDifferntiator) {

    private var onItemClickListener: OnItemClickListener<T>? = null
    private var onItemChildClickListener: OnItemChildClickListener<T>? = null

    private val onChildClickIds by lazy {
        arrayListOf<Int>()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder
        (view)

    abstract fun convert(holder: ViewHolder, position: Int)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        convert(holder, position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(this, it, position, getItem(position)!!)
        }

        onItemChildClickListener?.let {
            for (id in onChildClickIds) {
                holder.itemView.findViewById<View>(id)?.let { childView ->
                    if (!childView.isClickable) {
                        childView.isClickable = true
                    }
                    childView.setOnClickListener { v ->
                        var position = holder.bindingAdapterPosition
                        if (position == RecyclerView.NO_POSITION) {
                            return@setOnClickListener
                        }
                        onItemChildClickListener?.onItemChildClick(
                            this,
                            childView,
                            position,
                            getItem(position)!!
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        )
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemChildClickListener(onItemChildClickListener: OnItemChildClickListener<T>?) {
        this.onItemChildClickListener = onItemChildClickListener
    }

    fun addChildClickViewIds(id: Int) {
        onChildClickIds.add(id)
    }

    interface OnItemClickListener<T : Any> {
        fun onItemClick(adapter: BasePagingAdapter<T>, view: View, position: Int, item: T)
    }

    interface OnItemChildClickListener<T : Any> {
        fun onItemChildClick(adapter: BasePagingAdapter<T>, view: View, position: Int, item: T)
    }
}