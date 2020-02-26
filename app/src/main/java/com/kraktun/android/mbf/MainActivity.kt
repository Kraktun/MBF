package com.kraktun.android.mbf

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter.*
import android.content.Intent
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var currentMode: TextView
    private var isTaskerAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isTaskerAction = intent != null && intent.action != null && intent.action == TASKER_ACTION_EDIT_INTENT
        setContentView(R.layout.activity_main)
        currentMode = findViewById(R.id.currentModeView)
        if (isTaskerAction) {
            currentMode.visibility = TextView.INVISIBLE
            // set previous configuration to screen
            when(intent.getIntExtra(INTENT_BT_SETTING, -1)) {
                SCAN_MODE_NONE -> findViewById<RadioButton>(R.id.discover_none).isChecked = true
                SCAN_MODE_CONNECTABLE -> findViewById<RadioButton>(R.id.discover_connected).isChecked = true
                SCAN_MODE_CONNECTABLE_DISCOVERABLE -> findViewById<RadioButton>(R.id.discover_all).isChecked = true
            }
        }
        findViewById<Button>(R.id.applyButton).setOnClickListener {
            // get checked button and apply mode
            val scanMode = when(findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId) {
                R.id.discover_none -> SCAN_MODE_NONE
                R.id.discover_connected -> SCAN_MODE_CONNECTABLE
                R.id.discover_all -> SCAN_MODE_CONNECTABLE_DISCOVERABLE
                else -> -1
            }
            if (!isTaskerAction && scanMode != -1) {
                changeBluetoothMode(scanMode)
                setText(getDefaultAdapter().scanMode)
            } else if (scanMode != -1) {
                // prepare intent
                val intent = Intent()
                val m = when(scanMode) {
                    SCAN_MODE_NONE -> getString(R.string.discover_none)
                    SCAN_MODE_CONNECTABLE -> getString(R.string.discover_connected)
                    SCAN_MODE_CONNECTABLE_DISCOVERABLE -> getString(R.string.discover_all)
                    else -> "-1" // note that this is never the case
                }
                intent.putExtra(INTENT_BT_SETTING, scanMode)
                intent.putExtra(EXTRA_STRING_BLURB, m)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finishAndRemoveTask() // exit
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isTaskerAction) setText(getDefaultAdapter().scanMode) // display current mode
    }

    private fun setText(mode: Int) {
        // update text for current mode
        when(mode) {
            SCAN_MODE_NONE -> {
                currentMode.text = getString(R.string.current_mode, getString(R.string.discover_none)).toHtmlSpan()
            }
            SCAN_MODE_CONNECTABLE -> {
                currentMode.text = getString(R.string.current_mode, getString(R.string.discover_connected)).toHtmlSpan()
            }
            SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
                currentMode.text = getString(R.string.current_mode, getString(R.string.discover_all)).toHtmlSpan()
            }
        }
    }
}
