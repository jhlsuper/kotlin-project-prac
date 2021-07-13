package com.example.timer

import android.annotation.SuppressLint
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.txt_remainMinutes)
    }
    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }
    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.txt_remainSeconds)
    }
    private var currentCountDownTimer: CountDownTimer? = null

    private val soundPool = SoundPool.Builder().build()

    private var tickingSoundId: Int? = null
    private var bellSoundId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()

    }

    override fun onPause() {
        super.onPause()

        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun bindViews() {
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        updateRemainTime(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar ?: return //seekBar가 null일경우에 끝낸다 //앨비스 오퍼레이터
                    startCountDown()
                }
            }
        )
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()

            }
        }
    private fun completeCountDown(){
        updateRemainTime(0)
        updateSeekBar(0)
        soundPool.autoPause()
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
        }
    }
    private fun startCountDown(){
        currentCountDownTimer =
            createCountDownTimer(seekBar.progress * 60 * 1000L) //밀리세컨드로 변환
        currentCountDownTimer?.start()

        tickingSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0 - 1, 1F)
        }
    }

    private fun initSounds() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)

    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        seekBar.progress = (remainMillis / 1000 / 60).toInt() //분으로 변환
    }


}