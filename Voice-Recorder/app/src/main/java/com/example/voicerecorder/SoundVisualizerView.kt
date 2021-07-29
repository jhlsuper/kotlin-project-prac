package com.example.voicerecorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SoundVisualizerView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    val amplitubePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH
        strokeCap = Paint.Cap.ROUND //라인의 양 끄트머리를 어떻게 처리할지

    }
    var drawingWidth: Int = 0
    var drawingHeight: Int = 0
    var drawingAmplitudes: List<Int> = emptyList()
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


        }
    }

    companion object {
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15f
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
    //32767 float로 해놔서 Int로 나눠서 0이되는 현상을 방지

    }
}