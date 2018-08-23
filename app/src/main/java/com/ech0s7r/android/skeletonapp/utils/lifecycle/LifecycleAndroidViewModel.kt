package com.ech0s7r.android.skeletonapp.utils.lifecycle

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry

/**
 * @author ech0s7r
 */
abstract class LifecycleAndroidViewModel(app: Application) : AndroidViewModel(app), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this).apply {
        markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle() = lifecycleRegistry


    override fun onCleared() {
        super.onCleared()
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

}