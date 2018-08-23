package com.ech0s7r.android.skeletonapp.repository

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.content.Context
import android.content.SharedPreferences
import com.ech0s7r.android.log.Logger
import com.ech0s7r.android.skeletonapp.cache.ModelDatabase
import com.ech0s7r.android.skeletonapp.model.tv.Show
import com.ech0s7r.android.skeletonapp.remote.ModelDataSource
import com.ech0s7r.android.skeletonapp.utils.concurrent.AppExecutors
import kotlinx.coroutines.experimental.async
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

    init {
        val response = dataSource.getModeLiveData()
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        response.observeForever {
            async {
                // Deletes old historical data
                db.getModelDao().deleteAll()

                // Insert our new data into DB
                db.getModelDao().bulkInsert(response.value)
            }
        }

        initializeData()
    }


    private fun initializeData() {
        if (initialized) return
        initialized = true
        async {
            // Fetch channels if never fetched
            if (db.getModelDao().getModelSync().isEmpty()) {
                Logger.w("db empty, fetching new data")
                dataSource.fetchModelNetwork()
            } else {
                Logger.w("db not empty, don't need to download new data")
            }
        }
    }

    fun getModelList() = db.getModelDao().getModel()

    /**
     * Request popular show with custom page size
     * @param pageSize page size
     */
    fun requestPopular(pageSize: Int): Listing<Show> {
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
