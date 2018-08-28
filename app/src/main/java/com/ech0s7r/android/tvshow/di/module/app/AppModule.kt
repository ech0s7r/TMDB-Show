package com.ech0s7r.android.tvshow.di.module.app

import android.content.Context
import android.content.SharedPreferences
import com.ech0s7r.android.base.utils.concurrent.AppExecutors
import com.ech0s7r.android.tvshow.BuildConfig.TMDB_APIKEY
import com.ech0s7r.android.tvshow.TvShowApp
import com.ech0s7r.android.tvshow.remote.api.RestAPI
import com.ech0s7r.android.tvshow.remote.api.RestAPI.Companion.ENDPOINT_URL
import com.ech0s7r.android.tvshow.remote.api.RestAPI.Companion.PARAM_API_KEY
import com.ech0s7r.android.tvshow.remote.api.RestAPI.Companion.PARAM_LANGUAGE
import com.ech0s7r.android.tvshow.remote.interceptor.DefaultParamInterceptor
import com.ech0s7r.android.tvshow.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Module
open class AppModule(private val app: TvShowApp) {

    @Singleton
    @Provides
    fun provideAppContext(): Context =
            app.applicationContext

    @Singleton
    @Provides
    fun provideApp(): TvShowApp = app

    @Singleton
    @Provides
    fun provideNetworkExecutor(): Executor = AppExecutors.networkIO

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    open fun provideApiClient(): RestAPI {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(DefaultParamInterceptor(mapOf(
                        PARAM_API_KEY to TMDB_APIKEY,
                        PARAM_LANGUAGE to "en-US"
                )))
                .addInterceptor(interceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(RestAPI::class.java)
    }

}