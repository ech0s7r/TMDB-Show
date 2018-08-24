package com.ech0s7r.android.skeletonapp.repository

import com.ech0s7r.android.skeletonapp.model.tv.PagedResult
import retrofit2.Call

class PagedRequest(
        val initApiCall: () -> Call<PagedResult>,
        val afterApiCall: (Int) -> Call<PagedResult>
)