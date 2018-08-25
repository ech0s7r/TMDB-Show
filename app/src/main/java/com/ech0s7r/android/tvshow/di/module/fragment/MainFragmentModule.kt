package com.ech0s7r.android.tvshow.di.module.fragment

import android.arch.lifecycle.ViewModelProviders
import com.ech0s7r.android.tvshow.repository.MainRepository
import com.ech0s7r.android.tvshow.ui.popular.PopularFragment
import com.ech0s7r.android.tvshow.ui.MainViewModel
import dagger.Module
import dagger.Provides

/**
 *
 * @author ech0s7r
 */
@Module
class MainFragmentModule {

    @Provides
    fun provideViewModel(fragment: PopularFragment, repo: MainRepository): MainViewModel {
        val factory = MainViewModel.MainViewModelFactory(repo)
        return ViewModelProviders.of(fragment.requireActivity(), factory).get(MainViewModel::class.java)
    }


}