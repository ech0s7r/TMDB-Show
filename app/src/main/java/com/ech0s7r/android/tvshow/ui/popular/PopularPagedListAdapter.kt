package com.ech0s7r.android.tvshow.ui.popular

import android.app.Activity
import android.view.View
import com.ech0s7r.android.base.utils.ImgUtils
import com.ech0s7r.android.tvshow.R
import com.ech0s7r.android.tvshow.model.Show
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import com.ech0s7r.android.tvshow.ui.common.ShowPagedListAdapter
import kotlinx.android.synthetic.main.list_item_show.view.*

/**
 * @author ech0s7r
 */
class PopularPagedListAdapter(val activity: Activity, private val listener: ((Show?) -> Unit)?) : ShowPagedListAdapter(activity, listener) {

    override val listItemResId: Int = R.layout.list_item_show

    override fun createItemViewHolder(activity: Activity, v: View): ShowPagedViewHolder {
        return object : ShowPagedViewHolder(v) {
            override fun bind(show: Show?, listener: ((Show?) -> Unit)?) {
                with(v) {
                    show?.let {
                        ImgUtils.load(activity, RestAPI.IMG_SMALL_BASE_PATH + it.poster_path, v.movie_image)
                        text_title.text = show.name
                        text_rating.text = show.vote_average.toString()
                    }
                    setOnClickListener { listener?.invoke(show) }
                }
            }
        }
    }

}