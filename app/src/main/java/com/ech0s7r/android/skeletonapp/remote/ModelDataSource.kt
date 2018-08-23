package com.ech0s7r.android.skeletonapp.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.ech0s7r.android.skeletonapp.model.Model
import com.ech0s7r.android.skeletonapp.remote.api.RestClient
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Singleton
class ModelDataSource @Inject constructor(val context: Context, private val restClient: RestClient) {

    private val modelLiveData = MutableLiveData<List<Model>>()

    fun fetchModelNetwork() {
        launch {
            restClient.getPopular()
        }
    }

    fun getModeLiveData(): LiveData<List<Model>> = modelLiveData

}