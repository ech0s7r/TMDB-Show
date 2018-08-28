package com.ech0s7r.android.tvshow

import com.ech0s7r.android.tvshow.model.PagedResult
import com.ech0s7r.android.tvshow.model.Show
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import retrofit2.Call

class TestAPI(size: Int) : RestAPI {

    companion object {
        val dummyShow = Show(
                original_name = "original_name",
                genre_ids = listOf(1),
                name = "name",
                popularity = 0.0,
                origin_country = listOf("EN"),
                vote_count = 0,
                first_air_date = "first_air_date",
                backdrop_path = "path",
                original_language = "EN",
                id = 0,
                vote_average = 0.0,
                overview = "overview",
                poster_path = "poster_path")
    }

    private val listOf: List<Show> = MutableList(size) { dummyShow }
    private val mockData = PagedResult(0, 1, 1, listOf)

    override fun getPopular(page: Int): Call<PagedResult> = retrofit2.mock.Calls.response(mockData)

    override fun getSimilar(tvId: Int, page: Int): Call<PagedResult> = retrofit2.mock.Calls.response(mockData)

}