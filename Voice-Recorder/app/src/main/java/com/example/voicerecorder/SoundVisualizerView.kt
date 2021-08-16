package com.example.voicerecorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SoundVisualizerView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {


    var onRequestCurrentAmplitude:(()->Int)?=null

    private val amplitubePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH
        strokeCap = Paint.Cap.ROUND //라인의 양 끄트머리를 어떻게 처리할지

    }
    private var drawingWidth: Int = 0
    private var drawingHeight: Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()     //(0..10).map{ Random.nextInt(Short.MAX_VALUE.toInt())}


    //반복해서 자기자신을 실행시키
    private val visualizeRepeatAction :Runnable=object:Runnable{
        override fun run() {
            // Amplitube, Draw
            val currentAmplitude = onRequestCurrentAmplitude?.invoke()
            handler?.postDelayed(this, ACTION_INTERVAL)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        val centerY = drawingHeight / 2f //y축의 절반값 즉 중앙을 구하기
        var offsetX = drawingWidth.toFloat()

        for (amplitude in drawingAmplitudes) {
            val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8F

            offsetX -= LINE_SPACE
            if (offsetX <0) return

            canvas.drawLine(
                offsetX,
                centerY -lineLength/2F,
                offsetX,
                centerY +lineLength/2F,
                amplitubePaint
            )
        }
        //20 밀리 sec로 drawing하는게 눈에 자연스럽다.
    }

    companion object {
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15f
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    //32767 float로 해놔서 Int로 나눠서 0이되는 현상을 방지

    }
}