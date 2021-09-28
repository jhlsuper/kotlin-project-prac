package com.example.alarm

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initOnOffButton()
        initChangeAlarmTimeButton()
    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.btn_OnOff)
        onOffButton.setOnClickListener {

        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.btn_changeAlarmTime)

        val calendar = Calendar.getInstance()

        changeAlarmButton.setOnClickListener {
            TimePickerDialog(this, { picker, hour, minute ->
                val model = saveAlarmModel(hour, minute, false)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show()
        }
    }

    private fun saveAlarmModel(hour: Int, minute: Int, onOff: Boolean): AlarmDisplayModel {
        val model = AlarmDisplayModel(

            hour = hour,
            minute = minute,
            onOff = false
        )//처음

        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()){
            putString("alarm",model.makeDataForDB())
            putBoolean("onOff",model.onOff)
            commit()
        }
        return model// 으로 알람을 설정하는거니깐 false
    }
}