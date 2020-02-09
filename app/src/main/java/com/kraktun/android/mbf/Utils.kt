package com.kraktun.android.mbf

import android.bluetooth.BluetoothAdapter
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