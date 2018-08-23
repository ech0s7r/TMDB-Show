package com.ech0s7r.android.skeletonapp.utils

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 *
 * @author ech0s7r
 */
object KMockito {
    inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

    inline fun <reified T : Any> isA() = Mockito.isA(T::class.java)

    inline fun <reified T> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}