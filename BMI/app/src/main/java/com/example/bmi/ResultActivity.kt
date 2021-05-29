package com.example.bmi

import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class ResultActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height= intent.getIntExtra("height",0)
        val weight = intent.getIntExtra("weight",0)

        val bmi = weight/ (height / 100.0).pow(2.0)
        val resultText = when{
            bmi>= 35.0-> "고도 비만"
            bmi>= 30.0-> "중정도 비만"
            bmi>= 25.0-> "경도 비만"
            bmi>= 20.0-> "과제충"
            bmi>= 18.5-> "정상체중"
            else-> "저체중"
        }
        val resultValueTextView = findViewById<TextView>(R.id.txt_bmi)
        val resultStringTextView = findViewById<TextView>(R.id.txt_result)

        resultValueTextView.text = bmi.toString()
        resultStringTextView.text = resultText

    }
}