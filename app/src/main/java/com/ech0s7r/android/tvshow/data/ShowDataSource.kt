package com.ech0s7r.android.tvshow.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.support.annotation.WorkerThread
import com.ech0s7r.android.tvshow.model.PagedResult
import com.ech0s7r.android.tvshow.model.Show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @author ech0s7r
 */
class ShowDataSource constructor(private val pagedRequest: PagedRequest) : PageKeyedDataSource<Long, Show>() {

    enum class NetworkState {
        LOADING,
        LOADED,
        FAILED
    }

    val networkState = MutableLiveData<NetworkState>()
    val initialLoading = MutableLiveData<NetworkState>()

    @WorkerThread
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Show>) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        pagedRequest.initApiCall().enqueue(object : Callback<PagedResult> {
            override fun onFailure(call: Call<PagedResult>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

            override fun onResponse(call: Call<PagedResult>, response: Response<PagedResult>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        callback.onResult(it, null, 2L)
                    }
                    initialLoading.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    initialLoading.postValue(NetworkState.FAILED)
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        })
    }

    @WorkerThread
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Show>) {
        networkState.postValue(NetworkState.LOADING)
        pagedRequest.afterApiCall(params.key.toInt()).enqueue(object : Callback<PagedResult> {
            override fun onFailure(call: Call<PagedResult>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

            override fun onResponse(call: Call<PagedResult>, response: Response<PagedResult>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val nextKey = if (params.key == it.total_pages.toLong()) null else params.key + 1
                        callback.onResult(it.results, nextKey)
                    }
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Show>) {}

}