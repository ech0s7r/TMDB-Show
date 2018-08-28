package com.ech0s7r.android.tvshow.utils

import android.support.test.InstrumentationRegistry
import android.support.v7.widget.RecyclerView
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun waitForAdapterChange(recyclerView: RecyclerView) {
    val latch = CountDownLatch(1)
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        recyclerView.adapter!!.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                })
    }
    if (recyclerView.adapter!!.itemCount > 0) {
        return
    }
    MatcherAssert.assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
}