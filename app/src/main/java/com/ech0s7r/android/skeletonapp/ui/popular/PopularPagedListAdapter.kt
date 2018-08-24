package com.ech0s7r.android.skeletonapp.ui.popular

import android.app.Activity
import android.view.View
import com.ech0s7r.android.skeletonapp.R
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.ui.common.ShowPagedListAdapter
import com.ech0s7r.android.skeletonapp.utils.ImgUtils
import kotlinx.android.synthetic.main.list_item_show.view.*

class PopularPagedListAdapter(val activity: Activity, private val listener: ((Show?) -> Unit)?) : ShowPagedListAdapter(activity, listener) {

    override val listItemResId: Int = R.layout.list_item_show

    override fun createItemViewHolder(activity: Activity, v: View): ShowPagedViewHolder {
        return object : ShowPagedViewHolder(v) {
            override fun bind(show: Show?, listener: ((Show?) -> Unit)?) {
                with(v) {
                    show?.let {
                        ImgUtils.load(activity, RestAPI.IMG_SMALL_BASE_PATH + it.poster_path, v.movie_image)
                    }
                    setOnClickListener { listener?.invoke(show) }
                }
            }
        }
    }

}