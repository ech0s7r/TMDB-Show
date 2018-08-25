package com.ech0s7r.android.tvshow.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author ech0s7r
 */
class DefaultParamInterceptor(private val paramMap: Map<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val builder = originalHttpUrl.newBuilder()
        for ((k, v) in paramMap) {
            builder.addQueryParameter(k, v)
        }
        val url = builder.build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}