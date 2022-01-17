package fastcampus.chapter1.fatcampus_lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val runBtn by lazy{
        findViewById<Button>(R.id.runBtn)
    }
    private val addBtn by lazy{
        findViewById<Button>(R.id.addBtn)
    }
    private val resetBtn by lazy{
        findViewById<Button>(R.id.resetBtn)
    }
    private val numberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()
    private val numberTvList by lazy{
        listOf<TextView>(
            findViewById(R.id.tvFir),
            findViewById(R.id.tvSec),
            findViewById(R.id.tvThi),
            findViewById(R.id.tvFou),
            findViewById(R.id.tvFiv),
            findViewById(R.id.tvSix)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberPicker.minValue = 1
        numberPicker.maxValue = 45
        initRunBtn()
        initAddBtn()
        initResetBtn()

    }
    private fun initRunBtn(){

         runBtn.setOnClickListener {
             val list = getRandomNumber()
             didRun = true

             list.forEachIndexed { index, num ->
                 val textView = numberTvList[index]
                 textView.text = num.toString()
                 textView.isVisible = true
                 Log.d("ttttt", "$index $num")
                 setNumBackground(num,textView)
             }
         }
    }

    private fun initResetBtn(){
        resetBtn.setOnClickListener {
            pickNumberSet.clear()
            numberTvList.forEach{
                it.isVisible=false
            }
            didRun = false
        }
    }

    private fun getRandomNumber() : List<Int>{
        val numberList = arrayListOf<Int>().apply{
            for(i in 1 ..45){
                if(pickNumberSet.contains(i)) continue
                this.add(i)
            }
        }
        numberList.shuffle()
        return (pickNumberSet.toList()+numberList.subList(0,6-pickNumberSet.size)).sorted()
    }
    private fun initAddBtn(){

        addBtn.setOnClickListener{
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요.",Toast.LENGTH_SHORT).show()
                //라벨 없으면 initAddBtn이 나가짐
                return@setOnClickListener
            }
            if(pickNumberSet.size >= 5){
                Toast.makeText(this, "번호는 다섯 개 까지만 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTvList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            pickNumberSet.add(numberPicker.value)

            setNumBackground(numberPicker.value, textView)
        }
    }
    private fun setNumBackground(number : Int, textView: TextView){
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
        }
    }
}