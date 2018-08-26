package com.ech0s7r.android.tvshow.di.module.fragment

import com.ech0s7r.android.tvshow.ui.detail.ShowDetailFragment
import com.ech0s7r.android.tvshow.ui.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * @author ech0s7r
 */
@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [(PopularFragmentModule::class)])
    abstract fun providePopularFragment(): PopularFragment

    @ContributesAndroidInjector(modules = [(ShowDetailFragmentModule::class)])
    abstract fun provideShowDetailFragment(): ShowDetailFragment

}