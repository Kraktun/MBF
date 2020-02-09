package com.kraktun.android.mbf

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BrReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val mode = intent?.getIntExtra(INTENT_BT_SETTING, -1) ?: -1
        if (mode > 0)
            changeBluetoothMode(mode)
    }
}
