package com.ech0s7r.android.base.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 *
 * @author ech0s7r
 */
inline fun <reified T : Fragment> gotoFragment(supportFragmentManager: FragmentManager, containerId: Int, toBackStack: Boolean = false, clearBackStack: Boolean = false, noinline args: (() -> Bundle)? = null) {
    val tag = T::class.qualifiedName
    val t = T::class.java.newInstance()
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: t
    args?.let { fragment.arguments = it.invoke() }
    if (clearBackStack) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    supportFragmentManager.beginTransaction()
            .replace(containerId, fragment, tag).apply { if (toBackStack) addToBackStack(tag) }
            .commitAllowingStateLoss()
}

inline fun <reified T : Fragment> AppCompatActivity.gotoFragment(containerId: Int, toBackStack: Boolean = false, clearBackStack: Boolean = false, noinline args: (() -> Bundle)? = null) =
        gotoFragment<T>(supportFragmentManager, containerId, toBackStack, clearBackStack, args)

inline fun <reified T : Fragment> Fragment.gotoFragment(containerId: Int, toBackStack: Boolean = false, clearBackStack: Boolean = false, noinline args: (() -> Bundle)? = null) =
        gotoFragment<T>(requireActivity().supportFragmentManager, containerId, toBackStack, clearBackStack, args)