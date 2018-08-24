package com.ech0s7r.android.skeletonapp.remote

import android.content.Context
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.repository.PagedRequest
import com.ech0s7r.android.skeletonapp.repository.ShowDataFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Singleton
class ShowDataSource @Inject constructor(val context: Context, private val restAPI: RestAPI) {

    val popularDataFactory by lazy { createPopularDataFactory() }

    private fun createPopularDataFactory() = ShowDataFactory(PagedRequest(
            initApiCall = { restAPI.getPopular(1) },
            afterApiCall = { restAPI.getPopular(it) }
    ))

    fun getSimilarDataFactory(id: Int) = ShowDataFactory(PagedRequest(
            initApiCall = { restAPI.getSimilar(id, 1) },
            afterApiCall = { restAPI.getSimilar(id, it) }
    ))


}