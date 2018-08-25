package com.ech0s7r.android.tvshow.ui.detail

import android.app.Activity
import android.view.View
import com.ech0s7r.android.tvshow.R
import com.ech0s7r.android.tvshow.model.tv.Show
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import com.ech0s7r.android.tvshow.ui.common.ShowPagedListAdapter
import com.ech0s7r.android.base.utils.ImgUtils
import kotlinx.android.synthetic.main.list_item_similar_show.view.*

/**
 * @author ech0s7r
 */
class SimilarPagedListAdapter(val activity: Activity, listener: ((Show?) -> Unit)? = null) : ShowPagedListAdapter(activity, listener) {

    override val listItemResId: Int = R.layout.list_item_similar_show

    override fun createItemViewHolder(activity: Activity, v: View): ShowPagedViewHolder {
        return object : ShowPagedViewHolder(v) {
            override fun bind(show: Show?, listener: ((Show?) -> Unit)?) {
                with(v) {
                    show?.let {
                        ImgUtils.load(activity, RestAPI.IMG_SMALL_BASE_PATH + it.poster_path, v.movie_image)
                        v.text_title.text = show.name
                        v.text_year.text = show.first_air_date.substringBefore("-")
                        v.text_rating.text = show.vote_average.toString()
                    }
                    setOnClickListener { listener?.invoke(show) }
                }
            }
        }
    }

}