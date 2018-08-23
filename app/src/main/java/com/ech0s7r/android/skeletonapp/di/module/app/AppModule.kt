package com.ech0s7r.android.skeletonapp.di.module.app

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.ech0s7r.android.skeletonapp.BuildConfig.TMDB_APIKEY
import com.ech0s7r.android.skeletonapp.SkeletonApp
import com.ech0s7r.android.skeletonapp.cache.ModelDatabase
import com.ech0s7r.android.skeletonapp.remote.api.RestAPI
import com.ech0s7r.android.skeletonapp.remote.api.RestClient
import com.ech0s7r.android.skeletonapp.remote.api.RestClient.Companion.PARAM_API_KEY
import com.ech0s7r.android.skeletonapp.remote.api.RestClient.Companion.PARAM_LANGUAGE
import com.ech0s7r.android.skeletonapp.remote.interceptor.DefaultParamInterceptor
import com.ech0s7r.android.skeletonapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *
 * @author ech0s7r
 */
@Module
class AppModule(private val app: SkeletonApp) {

    @Singleton
    @Provides
    fun provideAppContext(): Context =
            app.applicationContext

    @Singleton
    @Provides
    fun provideApp(): SkeletonApp = app

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(app: SkeletonApp): ModelDatabase =
            Room.databaseBuilder(app, ModelDatabase::class.java, "model.db").build()

    @Singleton
    @Provides
    fun provideUserDao(db: ModelDatabase) = db.getModelDao()

    @Singleton
    @Provides
    fun provideApiClient(): RestAPI {
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
                .baseUrl(RestClient.ENDPOINT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(RestAPI::class.java)
    }

}