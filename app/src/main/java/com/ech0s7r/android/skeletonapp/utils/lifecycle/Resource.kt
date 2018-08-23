package com.ech0s7r.android.skeletonapp.utils.lifecycle

/**
 * A generic class that describes a data with a status, provided by ViewModel to UI
 *
 * @param T data type
 * @author ech0s7r
 */
sealed class Resource<T> {
    data class Loading<T>(val data: T?) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    open class Error<T>(val exception: Throwable?) : Resource<T>()
}