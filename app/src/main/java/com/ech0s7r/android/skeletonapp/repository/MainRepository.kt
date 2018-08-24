package com.ech0s7r.android.skeletonapp.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.content.Context
import android.content.SharedPreferences
import com.ech0s7r.android.skeletonapp.cache.ModelDatabase
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.ModelDataSource
import com.ech0s7r.android.skeletonapp.utils.concurrent.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author ech0s7r
 */

@Singleton
class MainRepository @Inject constructor(private val ctx: Context,
                                         private val db: ModelDatabase,
                                         private val dataSource: ModelDataSource,
                                         private val showDataFactory: ShowDataFactory) {

    @Inject
    lateinit var prefs: SharedPreferences

    private var initialized = false

    internal val showPopular = MutableLiveData<Boolean>()

    init {
        initializeData()
    }


    private fun initializeData() {
        if (initialized) return
        initialized = true
        showPopular.value = true
    }

    /**
     * Request popular show with custom page size
     * @param pageSize page size
     */
    internal fun requestPopular(pageSize: Int): Listing<Show> {
        val livePagedList = LivePagedListBuilder(showDataFactory, pageSize)
                .setFetchExecutor(AppExecutors.networkIO)
                .build()
        val refreshState = Transformations.switchMap(showDataFactory.sourceLiveData) { it.initialLoading }
        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(showDataFactory.sourceLiveData) {
                    it.networkState
                },
                refresh = {
                    showDataFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

}
