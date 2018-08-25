package com.ech0s7r.android.tvshow.di.module.activity

import com.ech0s7r.android.tvshow.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * @author ech0s7r
 */
@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun provideMainActivity(): MainActivity

}