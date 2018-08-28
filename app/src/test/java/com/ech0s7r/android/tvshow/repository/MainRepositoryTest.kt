package com.ech0s7r.android.tvshow.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.ech0s7r.android.tvshow.TestAPI
import com.ech0s7r.android.tvshow.data.PagedRequest
import com.ech0s7r.android.tvshow.data.ShowDataFactory
import com.ech0s7r.android.tvshow.utils.KMockito
import com.ech0s7r.android.tvshow.utils.KMockito.mock
import com.ech0s7r.android.tvshow.utils.getPagedList
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.Executor

@RunWith(JUnit4::class)
class MainRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val instantExecutor = Executor { command -> command.run() }
    private val dataSource = mock<PagedShowDataSource>()


    private fun initDummyData(size: Int) {
        val testAPI = TestAPI(size)
        KMockito.whenever(dataSource.restAPI).thenReturn(testAPI)
        KMockito.whenever(dataSource.popularDataFactory).thenReturn(ShowDataFactory(PagedRequest(
                initApiCall = { testAPI.getPopular(1) },
                afterApiCall = { testAPI.getPopular(it) }
        )))
        KMockito.whenever(dataSource.getSimilarDataFactory(1)).thenReturn(ShowDataFactory(PagedRequest(
                initApiCall = { testAPI.getSimilar(1, 1) },
                afterApiCall = { testAPI.getSimilar(1, 1) }
        )))
    }


    @Test
    fun testRequestPopularWithSize() {
        initDummyData(1)
        val repo = MainRepository(dataSource, instantExecutor)
        val result = repo.requestPopular(30)
        assertThat(getPagedList(result).size, CoreMatchers.`is`(1))
    }

    @Test
    fun testRequestSimilarWithSize() {
        initDummyData(1)
        val repo = MainRepository(dataSource, instantExecutor)
        val result = repo.requestSimilar(1, 30)
        assertThat(getPagedList(result).size, CoreMatchers.`is`(1))
    }

    @Test
    fun testRequestPopularEmpty() {
        initDummyData(0)
        val repo = MainRepository(dataSource, instantExecutor)
        val result = repo.requestPopular(30)
        assertThat(getPagedList(result).size, CoreMatchers.`is`(0))
    }

    @Test
    fun testRequestSimilarEmpty() {
        initDummyData(0)
        val repo = MainRepository(dataSource, instantExecutor)
        val result = repo.requestSimilar(1, 30)
        assertThat(getPagedList(result).size, CoreMatchers.`is`(0))
    }

}