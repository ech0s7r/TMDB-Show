package com.ech0s7r.android.skeletonapp.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowDataFactory @Inject constructor(private val api: RestAPI) : DataSource.Factory<Long, Show>() {

    val sourceLiveData = MutableLiveData<ShowDataSource>()

    override fun create(): DataSource<Long, Show> {
        val source = ShowDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }

}