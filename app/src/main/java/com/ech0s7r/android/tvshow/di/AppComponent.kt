package com.ech0s7r.android.tvshow.di

import com.ech0s7r.android.tvshow.TvShowApp
import com.ech0s7r.android.tvshow.di.module.activity.ActivitiesModule
import com.ech0s7r.android.tvshow.di.module.app.AppModule
import com.ech0s7r.android.tvshow.di.module.fragment.FragmentsModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Singleton
@Component(modules = [
    (AndroidInjectionModule::class),
    (AppModule::class),
    (ActivitiesModule::class),
    (FragmentsModule::class)
])
interface AppComponent {
    fun inject(app: TvShowApp)
}