package fastcampus.chapter1.fastcampus_recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SoundVisualizerView(
    context : Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var onRequestCurrentAmplitude: (() -> Int)? =null

    //amplitude : 증폭
    //ANTI_ALIAS_FLAG : 계단화 방지, 도트 이미지 찍은 것처럼 각지게 그려지는 것을 방지하는 플래그 -> 곡선이 좀 부드럽게 됨
    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        //고정 값들, length(세로)는 볼륨에 따라 가변
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH
        //라인의 양 끝을 둥그렇게
        strokeCap = Paint.Cap.ROUND
    }
    private var drawingWidth : Int = 0
    private var drawingHeight : Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()
    private var isReplaying: Boolean = false
    private var replayingPosition : Int = 0

    //20ms마다 반복
    private val visualizeRepeatAction: Runnable = object : Runnable{
        override fun run() {
            //Amplitude, Draw

            if(!isReplaying) {
                //MainActivity에 Viewbind를 통해 정의한 onRequestCurrentAmplitude를 호출
                //onRequestCurrentAmplitude는 recorder의 maxAmplitude를 반환
                val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
                drawingAmplitudes = listOf(currentAmplitude) + drawingAmplitudes
            }
            else{
                replayingPosition++
            }
            //데이터가 추가됐을 때 invalidate를 추가해야 onDraw를 다시 호출하게 됨 -> 뷰 갱신을 위해 사용
            invalidate()
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

        //뷰의 위아래 진폭을 알아야 하고, 어떻게 그릴지
        //뷰의 우측부터 그리기
        val centerY = drawingHeight/2f
        var offsetX = drawingWidth.toFloat()

        //진폭값들을 리스트로

        //let + forEach
        drawingAmplitudes.let { amplitudes ->
            if(isReplaying) {
                amplitudes.takeLast(replayingPosition)
            }else{
                amplitudes
            }
        }.forEach{amplitude ->
            //진폭이 맥스일 때 꽉차는 것보다 0.8배 정도가 이쁨
            val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8f

            offsetX -= LINE_SPACE
            //뷰의 왼쪽 영역보다 더 밖에 있다 -> 화면을 벗어났다 ->그리지 않고 루프문 탈출
            if(offsetX <0) return@forEach

            canvas.drawLine(
                offsetX,
                centerY - lineLength /2f,
                offsetX,
                centerY + lineLength /2f,
                amplitudePaint
            )

        }

    }

    fun startVisualizing(isReplaying : Boolean){
        this.isReplaying = isReplaying
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing(){
        replayingPosition = 0
        handler?.removeCallbacks(visualizeRepeatAction)
    }

    fun clearVisualization(){
        drawingAmplitudes = emptyList()
        invalidate()
    }

    companion object{
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        //볼륨의 최댓값 short의 max 32767, toFloat()으로 0으로 나누는 일을 방지
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    }
}