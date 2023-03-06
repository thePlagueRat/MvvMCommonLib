package com.kotlin.lib.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.lib.R
import com.kotlin.lib.paging.drawable.ProgressDrawable

/**
 * paging's footer
 */
class FooterAdapter : LoadStateAdapter<FooterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ImageView = itemView.findViewById(R.id.iv_progress)
        val tvLoading: TextView = itemView.findViewById(R.id.tv_loading)
        val tvNoMoreData: TextView = itemView.findViewById(R.id.tv_no_more_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_paging_footer, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.tvLoading.isVisible = loadState is LoadState.Loading
        holder.tvNoMoreData.isVisible =
            loadState is LoadState.NotLoading && loadState.endOfPaginationReached

        if (holder.progressBar.isVisible) {
            val mProgressDrawable =
                ProgressDrawable()
            mProgressDrawable.setColor(-0x99999a)
            holder.progressBar.setImageDrawable(mProgressDrawable)
        }
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState)
                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }
}