package com.kraktun.android.mbf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter.*
import android.widget.*


class MainActivity : AppCompatActivity() {

    lateinit var buttonNone: Switch
    lateinit var buttonConnected: Switch
    lateinit var buttonAll: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonNone = findViewById(R.id.discover_none)
        buttonConnected = findViewById(R.id.discover_connected)
        buttonAll = findViewById(R.id.discover_all)
        buttonNone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonConnected.isChecked = false
                buttonAll.isChecked = false
                changeBluetoothMode(SCAN_MODE_NONE)
            }
        }
        buttonConnected.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonNone.isChecked = false
                buttonAll.isChecked = false
                changeBluetoothMode(SCAN_MODE_CONNECTABLE)
            }
        }
        buttonAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonConnected.isChecked = false
                buttonNone.isChecked = false
                changeBluetoothMode(SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when(getDefaultAdapter().scanMode) {
            SCAN_MODE_NONE -> buttonNone.isChecked = true
            SCAN_MODE_CONNECTABLE -> buttonConnected.isChecked = true
            SCAN_MODE_CONNECTABLE_DISCOVERABLE -> buttonAll.isChecked = true
        }
    }
}
