package com.ech0s7r.android.skeletonapp.utils.lifecycle

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.ech0s7r.android.log.Logger
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @author ech0s7r
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
            Logger.w("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner, Observer<T> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })

    }

    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }

}