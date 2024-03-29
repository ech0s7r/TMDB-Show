package com.ech0s7r.android.tvshow

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.v4.app.Fragment
import com.ech0s7r.android.base.utils.di.DaggerUtils
import com.ech0s7r.android.log.Logger
import com.ech0s7r.android.log.LoggerConfigurator
import com.ech0s7r.android.log.appender.LogcatAppender
import com.ech0s7r.android.log.layout.LogcatLayout
import com.ech0s7r.android.tvshow.R.string.app_name
import com.ech0s7r.android.tvshow.di.DaggerAppComponent
import com.ech0s7r.android.tvshow.di.module.app.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 *
 * @author ech0s7r
 */
open class TvShowApp : Application(), HasActivityInjector, HasSupportFragmentInjector, HasServiceInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    open val appModule by lazy { getDaggerAppModule() }


    override fun onCreate() {
        super.onCreate()
        initLogger()
        initDI()
    }

    private fun initLogger() {
        val appName = getString(app_name)
        LoggerConfigurator.init(Logger.Level.DEBUG, 0, appName, "", "", appName)
        LoggerConfigurator.addAppender(LogcatAppender(LogcatLayout()))
    }

    private fun initDI() {
        DaggerAppComponent.builder()
                .appModule(appModule)
                .build()
                .inject(this)
        DaggerUtils.registerAutoInject(this)
    }

    open fun getDaggerAppModule(): AppModule = AppModule(this)

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}