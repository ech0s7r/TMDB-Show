package com.ech0s7r.android.skeletonapp.remote.api

import com.ech0s7r.android.skeletonapp.model.tv.PagedResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * @author ech0s7r
 */
interface RestAPI {

    companion object {
        private const val IMG_BASE_PATH = "http://image.tmdb.org/t/p"
        const val IMG_SMALL_BASE_PATH = "$IMG_BASE_PATH/w185/"
        const val IMG_LARGE_BASE_PATH = "$IMG_BASE_PATH/w780/"
    }

    @GET("/3/tv/popular")
    fun getPopular(@Query("page") page: Int): Call<PagedResult>

    @GET("/3/tv/{tv_id}/similar")
    fun getSimilar(@Path("tv_id") tvId: Int, @Query("page") page: Int): Call<PagedResult>

}