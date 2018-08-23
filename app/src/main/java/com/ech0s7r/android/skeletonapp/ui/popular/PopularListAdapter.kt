package com.ech0s7r.android.skeletonapp.ui.popular

import android.app.Activity
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.repository.ShowDataSource
import com.ech0s7r.android.skeletonapp.utils.ImgUtils
import kotlinx.android.synthetic.main.list_item_network_state.view.*
import kotlinx.android.synthetic.main.list_item_show.view.*

class PopularListAdapter(private val activity: Activity, private val onShowClickListener: ((Show?, View?) -> Unit)? = null)
    : PagedListAdapter<Show, RecyclerView.ViewHolder>(SHOW_COMPARATOR) {

    private var networkState: ShowDataSource.NetworkState? = null

    class ShowViewHolder(private val activity: Activity, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(show: Show?, listener: ((Show?, View?) -> Unit)? = null) {
            with(itemView) {
                show?.let {
                    ImgUtils.load(activity, RestAPI.IMG_SMALL_BASE_PATH + it.poster_path, itemView.movie_image)
                }
                setOnClickListener { listener?.invoke(show, itemView.movie_image) }
            }
        }
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
            R.layout.list_item_show -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_show, parent, false)
                ShowViewHolder(activity, v)
            }
            R.layout.list_item_network_state -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_network_state, parent, false)
                NetworkStateItemViewHolder(v)
            }
            else -> throw IllegalArgumentException()
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_show -> (holder as ShowViewHolder).bind(getItem(position), onShowClickListener)
            R.layout.list_item_network_state -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != ShowDataSource.NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_network_state
        } else {
            R.layout.list_item_show
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