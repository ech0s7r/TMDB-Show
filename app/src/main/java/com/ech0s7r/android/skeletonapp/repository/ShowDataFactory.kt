package com.ech0s7r.android.skeletonapp.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ech0s7r.android.skeletonapp.model.tv.Show

class ShowDataFactory(private val pagedRequest: PagedRequest) : DataSource.Factory<Long, Show>() {

    val sourceLiveData = MutableLiveData<ShowDataSource>()

    override fun create(): DataSource<Long, Show> {
        val source = ShowDataSource(pagedRequest)
        sourceLiveData.postValue(source)
        return source
    }

}