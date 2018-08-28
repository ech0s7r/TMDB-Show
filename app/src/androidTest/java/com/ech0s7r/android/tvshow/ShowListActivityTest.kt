package com.ech0s7r.android.tvshow

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.ech0s7r.android.tvshow.ui.common.ShowPagedListAdapter
import com.ech0s7r.android.tvshow.utils.waitForAdapterChange
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ShowListActivityTest {

    @Test
    fun testShowResult() {
        val intent = Intent(InstrumentationRegistry.getTargetContext(), MainActivity::class.java)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
        val recyclerView = activity.findViewById<RecyclerView>(R.id.list_view)
        MatcherAssert.assertThat(recyclerView.adapter, CoreMatchers.notNullValue())
        waitForAdapterChange(recyclerView)
        MatcherAssert.assertThat(recyclerView.adapter!!.itemCount, CoreMatchers.`is`(40))
    }

    @Test
    fun testSelectMovie() {
        val intent = Intent(InstrumentationRegistry.getTargetContext(), MainActivity::class.java)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
        val recyclerView = activity.findViewById<RecyclerView>(R.id.list_view)
        MatcherAssert.assertThat(recyclerView.adapter, CoreMatchers.notNullValue())
        waitForAdapterChange(recyclerView)

        onView(ViewMatchers.withId(R.id.list_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition<ShowPagedListAdapter.ShowPagedViewHolder>(0, click()))

        onView(withId(R.id.details_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.title)).check(matches(withText(TestAPI.dummyShow.name)))
    }

}