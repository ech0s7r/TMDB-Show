package com.ech0s7r.android.skeletonapp.utils.lifecycle

import android.arch.lifecycle.LiveData

/**
 * A LiveData class that has null value
 *
 * @author ech0s7r
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> new(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
