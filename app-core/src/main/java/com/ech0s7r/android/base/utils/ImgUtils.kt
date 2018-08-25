package com.ech0s7r.android.base.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 *
 * @author ech0s7r
 */
object ImgUtils {
    //private val opt = RequestOptions().placeholder(R.drawable.transparent_placeholder)

    fun load(activity: Activity, url: String, into: ImageView) {
        Glide.with(activity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(opt)
                .into(into)
    }

}