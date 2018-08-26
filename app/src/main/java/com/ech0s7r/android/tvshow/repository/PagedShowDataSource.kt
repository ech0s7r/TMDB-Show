package com.ech0s7r.android.tvshow.repository

import com.ech0s7r.android.tvshow.data.PagedRequest
import com.ech0s7r.android.tvshow.data.ShowDataFactory
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Singleton
class PagedShowDataSource @Inject constructor(private val restAPI: RestAPI) {

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