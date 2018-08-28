package com.ech0s7r.android.tvshow.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import com.ech0s7r.android.tvshow.TestAPI
import com.ech0s7r.android.tvshow.data.Listing
import com.ech0s7r.android.tvshow.model.Show
import com.ech0s7r.android.tvshow.repository.MainRepository
import com.ech0s7r.android.tvshow.ui.MainViewModel
import com.ech0s7r.android.tvshow.utils.Constants
import com.ech0s7r.android.tvshow.utils.KMockito.any
import com.ech0s7r.android.tvshow.utils.KMockito.mock
import com.ech0s7r.android.tvshow.utils.KMockito.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.times

@RunWith(JUnit4::class)
class ViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repo = mock<MainRepository>()

    private val trigger = MutableLiveData<Boolean>()
    private val observer = mock<Observer<PagedList<Show>>>()
    private val result = MutableLiveData<PagedList<Show>>()
    private val refresh = mock<() -> Unit>()
    private val emptyListing = Listing(result, MutableLiveData(), MutableLiveData(), refresh)

    @Before
    fun before() {
        whenever(repo.showPopularTrigger).thenReturn(trigger)
        whenever(repo.requestPopular(Constants.PAGE_SIZE)).thenReturn(emptyListing)
        whenever(repo.requestSimilar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mock())
    }

    @Test
    fun testInitialFetch() {
        val viewModel = MainViewModel(repo)
        viewModel.popularShow.observeForever(observer)
        result.value = mock()
        trigger.value = true
        Mockito.verify(observer, times(1)).onChanged(any())
        Mockito.verifyNoMoreInteractions(observer)
    }

    @Test
    fun testRefreshPopular() {
        val viewModel = MainViewModel(repo)
        viewModel.popularShow.observeForever(observer)
        result.value = mock()
        trigger.value = true
        viewModel.refreshPopularShow()
        Mockito.verify(observer, times(1)).onChanged(any())
        Mockito.verify(emptyListing.refresh, times(1)).invoke()
        Mockito.verifyNoMoreInteractions(observer)
    }

    @Test
    fun testSelectedShow() {
        whenever(repo.showPopularTrigger).thenReturn(mock())
        val viewModel = MainViewModel(repo)
        viewModel.similarShow.observeForever(observer)
        whenever(repo.requestSimilar(0, 30)).thenReturn(emptyListing)
        viewModel.setSelectedShow(TestAPI.dummyShow)
        Mockito.verify(repo).requestSimilar(Mockito.anyInt(), Mockito.anyInt())
    }

}