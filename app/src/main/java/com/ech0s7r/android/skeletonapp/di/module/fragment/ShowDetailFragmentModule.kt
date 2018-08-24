package com.ech0s7r.android.skeletonapp.di.module.fragment

import android.arch.lifecycle.ViewModelProviders
import com.ech0s7r.android.skeletonapp.repository.MainRepository
import com.ech0s7r.android.skeletonapp.ui.detail.ShowDetailFragment
import com.ech0s7r.android.skeletonapp.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

/**
 *
 * @author ech0s7r
 */
@Module
class ShowDetailFragmentModule {

    @Provides
    fun provideViewModel(fragment: ShowDetailFragment, repo: MainRepository): MainViewModel {
        val factory = MainViewModel.MainViewModelFactory(repo)
        return ViewModelProviders.of(fragment.requireActivity(), factory).get(MainViewModel::class.java)
    }


}