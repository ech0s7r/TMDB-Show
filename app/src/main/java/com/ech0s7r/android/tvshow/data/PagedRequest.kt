package com.ech0s7r.android.tvshow.data

import com.ech0s7r.android.tvshow.model.tv.PagedResult
import retrofit2.Call

/**
 * @author ech0s7r
 */
class PagedRequest(
        val initApiCall: () -> Call<PagedResult>,
        val afterApiCall: (Int) -> Call<PagedResult>
)