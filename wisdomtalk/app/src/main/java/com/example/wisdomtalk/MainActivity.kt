package com.example.wisdomtalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }


    private fun initViews() {
        viewPager.adapter = QuotesPageAdapter(
            listOf(Quote("나는 생각한다,고로 나는 존재한다", "데카르트"))
        )
    }
}