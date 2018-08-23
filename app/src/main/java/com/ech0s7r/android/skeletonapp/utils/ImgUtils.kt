package com.ech0s7r.android.skeletonapp.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ech0s7r.android.skeletonapp.R

/**
 *
 * @author ech0s7r
 */
object ImgUtils {
    private val opt = RequestOptions().placeholder(R.drawable.transparent_placeholder)

    fun load(activity: Activity, url: String, into: ImageView) {
        Glide.with(activity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(opt)
                .into(into)
    }

}