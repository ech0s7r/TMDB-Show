package com.ech0s7r.android.skeletonapp.utils

import android.app.AlertDialog
import android.content.Context

/**
 *
 * @author marco.rocco
 */

fun Context.showMessage(title: Int, message: Int, buttonId: Int, callback: () -> Unit) {
    AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonId) { _, _ -> callback() }
            .create()
            .show()
}
