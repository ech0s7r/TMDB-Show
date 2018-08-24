package com.ech0s7r.android.skeletonapp.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.ShowDataSource
import com.ech0s7r.android.skeletonapp.utils.concurrent.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author ech0s7r
 */

@Singleton
class MainRepository @Inject constructor(private val dataSource: ShowDataSource) {

    private var initialized = false

    internal val showPopularTrigger = MutableLiveData<Boolean>()

    init {
        initializeData()
    }

    private fun initializeData() {
        if (initialized) return
        initialized = true
        showPopularTrigger.value = true
    }

    /**
     * Request popular show with custom page size
     * @param pageSize page size
     */
    internal fun requestPopular(pageSize: Int): Listing<Show> {
        val dataFactory = dataSource.popularDataFactory
        val livePagedList = LivePagedListBuilder(dataFactory, pageSize)
                .setFetchExecutor(AppExecutors.networkIO)
                .build()
        val refreshState = Transformations.switchMap(dataFactory.sourceLiveData) { it.initialLoading }
        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(dataFactory.sourceLiveData) {
                    it.networkState
                },
                refresh = {
                    dataFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

    /**
     * Request similar show with custom page size
     * @param showId id show
     * @param pageSize page size
     */
    internal fun requestSimilar(showId: Int, pageSize: Int): Listing<Show> {
        val dataFactory = dataSource.getSimilarDataFactory(showId)
        val livePagedList = LivePagedListBuilder(dataFactory, pageSize)
                .setFetchExecutor(AppExecutors.networkIO)
                .build()
        val refreshState = Transformations.switchMap(dataFactory.sourceLiveData) { it.initialLoading }
        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(dataFactory.sourceLiveData) {
                    it.networkState
                },
                refresh = {
                    dataFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

}
