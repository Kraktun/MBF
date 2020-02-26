package com.kraktun.android.mbf

import android.bluetooth.BluetoothAdapter
import android.os.Build
import android.text.Html
import android.text.Spanned
import java.lang.Exception

fun changeBluetoothMode(mode: Int) {
    val defaultAdapter = BluetoothAdapter.getDefaultAdapter()
    if (defaultAdapter.isEnabled && defaultAdapter.scanMode != mode) {
        val cls = BluetoothAdapter::class.java
        try {
            cls.getMethod("setScanMode", Integer.TYPE).invoke(defaultAdapter, mode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun String.toHtmlSpan(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
} else {
    Html.fromHtml(this)
}