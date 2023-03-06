package com.kotlin.lib.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 */
class CommonViewHolder<VB : ViewBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private var itemViewBinding: VB? = null

    constructor(itemViewBinding: VB) : this(itemViewBinding.root) {
        this.itemViewBinding = itemViewBinding
    }

    fun viewBanding(block: VB.() -> Unit) {
        itemViewBinding?.run {
            block(this)
        }
    }
}