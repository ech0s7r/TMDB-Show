package com.ech0s7r.android.tvshow.model.tv

/**
 * TMDB PagedResult entity
 *
 * @author ech0s7r
 */
data class PagedResult(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<Show>
)