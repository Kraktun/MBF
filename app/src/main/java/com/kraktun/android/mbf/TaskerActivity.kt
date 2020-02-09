package com.kraktun.android.mbf

import android.app.Activity
import android.bluetooth.BluetoothAdapter.*
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity


class TaskerActivity : AppCompatActivity() {

    lateinit var buttonNone: Switch
    lateinit var buttonConnected: Switch
    lateinit var buttonAll: Switch
    var btMode: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasker_activity_main)
        buttonNone = findViewById(R.id.tasker_discover_none)
        buttonConnected = findViewById(R.id.tasker_discover_connected)
        buttonAll = findViewById(R.id.tasker_discover_all)
        buttonNone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonConnected.isChecked = false
                buttonAll.isChecked = false
                btMode = SCAN_MODE_NONE
            }
        }
        buttonConnected.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonNone.isChecked = false
                buttonAll.isChecked = false
                btMode = SCAN_MODE_CONNECTABLE
            }
        }
        buttonAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonConnected.isChecked = false
                buttonNone.isChecked = false
                btMode = SCAN_MODE_CONNECTABLE_DISCOVERABLE
            }
        }
        val buttonOk = findViewById<Button>(R.id.tasker_button)
        buttonOk.setOnClickListener {
            val intent = Intent()
            val m = when {
                buttonAll.isChecked -> getString(R.string.discover_all)
                buttonConnected.isChecked -> getString(R.string.discover_connected)
                buttonNone.isChecked -> getString(R.string.discover_none)
                else -> "-1"
            }
            if (m == "-1") {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            intent.putExtra(INTENT_BT_SETTING, btMode)
            intent.putExtra(EXTRA_STRING_BLURB, m)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        buttonNone.isChecked = false
        buttonConnected.isChecked = false
        buttonAll.isChecked = false
    }
}
