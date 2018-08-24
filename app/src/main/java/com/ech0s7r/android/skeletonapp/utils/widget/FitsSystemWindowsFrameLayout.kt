package com.ech0s7r.android.skeletonapp.utils.widget

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.FrameLayout

/**
 * Implements an effect similar to {@code android:fitsSystemWindows="true"} on Lollipop or higher,
 * except ignoring the top system window inset. {@code android:fitsSystemWindows="true"} does not
 * and should not be set on this layout.
 *
 * @author ech0s7r
 */
class FitsSystemWindowsFrameLayout : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @Suppress("unused")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setPadding(insets.systemWindowInsetLeft, 0, insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom)
            insets.replaceSystemWindowInsets(0, insets.systemWindowInsetTop, 0, 0)
        } else {
            super.onApplyWindowInsets(insets)
        }
    }

}