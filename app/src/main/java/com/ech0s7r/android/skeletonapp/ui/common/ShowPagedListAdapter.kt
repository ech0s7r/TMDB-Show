package com.ech0s7r.android.skeletonapp.ui.common

import android.app.Activity
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.repository.ShowDataSource
import kotlinx.android.synthetic.main.list_item_network_state.view.*

abstract class ShowPagedListAdapter(private val activity: Activity, private val onShowClickListener: ((Show?) -> Unit)? = null)
    : PagedListAdapter<Show, RecyclerView.ViewHolder>(SHOW_COMPARATOR) {

    private var networkState: ShowDataSource.NetworkState? = null

    abstract val listItemResId: Int

    abstract class ShowPagedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(show: Show?, listener: ((Show?) -> Unit)? = null)
    }

    class NetworkStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(networkState: ShowDataSource.NetworkState?) {
            with(itemView) {
                itemView.progress_bar.visibility = if (networkState == ShowDataSource.NetworkState.LOADING) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            listItemResId -> {
                val v = LayoutInflater.from(parent.context).inflate(listItemResId, parent, false)
                createItemViewHolder(activity, v)
            }
            R.layout.list_item_network_state -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_network_state, parent, false)
                NetworkStateItemViewHolder(v)
            }
            else -> throw IllegalArgumentException()
        }

    }

    abstract fun createItemViewHolder(activity: Activity, v: View): ShowPagedViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            listItemResId -> (holder as ShowPagedViewHolder).bind(getItem(position), onShowClickListener)
            R.layout.list_item_network_state -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != ShowDataSource.NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_network_state
        } else {
            listItemResId
        }
    }

    fun onNetworkStateChange(state: ShowDataSource.NetworkState?) {
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        networkState = state
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != state) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<Show>() {
            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean =
                    oldItem.id == newItem.id
        }
    }
}