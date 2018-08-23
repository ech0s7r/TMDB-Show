package com.ech0s7r.android.skeletonapp.di

import com.ech0s7r.android.skeletonapp.SkeletonApp
import com.ech0s7r.android.skeletonapp.di.module.activity.ActivitiesModule
import com.ech0s7r.android.skeletonapp.di.module.app.AppModule
import com.ech0s7r.android.skeletonapp.di.module.fragment.FragmentsModule
import com.ech0s7r.android.skeletonapp.di.module.service.ServicesModule
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
    (FragmentsModule::class),
    (ServicesModule::class)
])
interface AppComponent {
    fun inject(app: SkeletonApp)
}