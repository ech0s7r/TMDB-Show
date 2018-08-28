package com.ech0s7r.android.tvshow

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.ech0s7r.android.tvshow.di.module.app.AppModule

class JUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestDaggerApplication::class.java.name, context)
    }

    class TestDaggerApplication : TvShowApp() {
        override fun getDaggerAppModule() = object : AppModule(this) {
            override fun provideApiClient() = TestAPI(5)
        }
    }

}