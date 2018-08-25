package com.ech0s7r.android.tvshow.model.tv

/**
 * @author ech0s7r
 */
data class Show(
        val original_name: String,
        val genre_ids: List<Int>,
        val name: String,
        val popularity: Double,
        val origin_country: List<String>,
        val vote_count: Int,
        val first_air_date: String,
        val backdrop_path: String,
        val original_language: String,
        val id: Int,
        val vote_average: Double,
        val overview: String,
        val poster_path: String
)