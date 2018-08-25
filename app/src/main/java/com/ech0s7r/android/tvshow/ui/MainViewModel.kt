package com.ech0s7r.android.tvshow.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ech0s7r.android.tvshow.model.tv.Show
import com.ech0s7r.android.tvshow.repository.MainRepository
import com.ech0s7r.android.tvshow.utils.Constants.PAGE_SIZE
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Suppress("HasPlatformType")
@Singleton
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    @Singleton
    class MainViewModelFactory @Inject constructor(private val repo: MainRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repo) as T
        }
    }

    val selectedShow = MutableLiveData<Show>()

    private val popularRepoResult = Transformations.map(repository.showPopularTrigger) {
        repository.requestPopular(PAGE_SIZE)
    }
    private val similarShowResult = Transformations.map(selectedShow) {
        repository.requestSimilar(it.id, PAGE_SIZE)
    }

    val popularShow = Transformations.switchMap(popularRepoResult) { it.pagedList }
    val networkState = Transformations.switchMap(popularRepoResult) { it.networkState }
    val refreshState = Transformations.switchMap(popularRepoResult) { it.refreshState }
    val similarShow = Transformations.switchMap(similarShowResult) { it.pagedList }


    fun refreshPopularShow() {
        popularRepoResult.value?.refresh?.invoke()
    }

    fun setSelectedShow(show: Show) {
        selectedShow.value = show
    }

}