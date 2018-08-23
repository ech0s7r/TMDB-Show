package com.ech0s7r.android.skeletonapp.remote.api

import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Singleton
class RestClient @Inject constructor(private val api: RestAPI) {

    companion object {
        const val ENDPOINT_URL = "https://api.themoviedb.org"

        const val PARAM_API_KEY = "api_key"
        const val PARAM_LANGUAGE = "language"
    }

    fun getPopular(page: Int = 1) = api.getPopular(page).execute().body()

    fun getSimilar(tvId: Int, page: Int = 1) = api.getSimilar(tvId, page).execute().body()

}