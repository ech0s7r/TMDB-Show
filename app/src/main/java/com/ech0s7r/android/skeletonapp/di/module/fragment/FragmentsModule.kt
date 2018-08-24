package com.ech0s7r.android.skeletonapp.di.module.fragment

import com.ech0s7r.android.skeletonapp.ui.popular.PopularFragment
import com.ech0s7r.android.skeletonapp.ui.detail.ShowDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * @author ech0s7r
 */

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [(MainFragmentModule::class)])
    abstract fun providePopularFragment(): PopularFragment

    @ContributesAndroidInjector(modules = [(ShowDetailFragmentModule::class)])
    abstract fun provideShowDetailFragment(): ShowDetailFragment

}