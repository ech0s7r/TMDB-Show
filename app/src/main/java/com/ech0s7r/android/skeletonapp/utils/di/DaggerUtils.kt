package com.ech0s7r.android.skeletonapp.utils.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.ech0s7r.android.skeletonapp.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 *
 * @author ech0s7r
 */
object DaggerUtils {

    /**
     * Register activity lifeCycle callback listener for
     * automatically injecting every activity that launches
     */
    fun registerAutoInject(app: Application) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {}
            override fun onActivityResumed(activity: Activity?) {}
            override fun onActivityStarted(activity: Activity?) {}
            override fun onActivityDestroyed(activity: Activity?) {}
            override fun onActivitySaveInstanceState(activity: Activity?, savedState: Bundle?) {}
            override fun onActivityStopped(activity: Activity?) {}
            override fun onActivityCreated(activity: Activity?, savedState: Bundle?) = handleActivity(activity)
        })
    }

    private fun handleActivity(activity: Activity?) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            when (f) {
                                is Injectable -> AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
        }
    }

}