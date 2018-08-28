package com.ech0s7r.android.tvshow.utils

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import com.ech0s7r.android.tvshow.data.Listing
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

/**
 * simple observer that logs the latest value it receives
 */
class LoggingObserver<T> : Observer<T> {
    var value: T? = null
    override fun onChanged(t: T?) {
        this.value = t
    }
}

/**
 * Extract the latest paged list from the listing
 */
fun <T> getPagedList(listing: Listing<T>): PagedList<T> {
    val observer = LoggingObserver<PagedList<T>>()
    listing.pagedList.observeForever(observer)
    MatcherAssert.assertThat(observer.value, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    return observer.value!!
}