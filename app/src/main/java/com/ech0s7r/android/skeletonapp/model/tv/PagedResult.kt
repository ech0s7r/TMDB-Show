package com.ech0s7r.android.skeletonapp.model.tv

data class PagedResult(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<Show>
)