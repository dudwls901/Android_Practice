package fastcampus.chapter1.fastcampus_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.room.Room
import fastcampus.chapter1.fastcampus_calculator.model.History
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private val expressionTv: TextView by lazy {
        findViewById<TextView>(R.id.expressionTv)
    }
    private val resultTv: TextView by lazy {
        findViewById<TextView>(R.id.resultTv)
    }
    private val historyLayout : View by lazy{
        findViewById<View>(R.id.historyLayout)
    }
    private val historyLinearLayout : LinearLayout by lazy{
        findViewById<LinearLayout>(R.id.historyLinearLayout)
    }
    private var isOperator = false
    private var hasOperator = false

    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstanc(this)!!
    }

    fun buttonClicked(v: View) {
        Log.d("tttt", v.id.toString())
        when (v.id) {
            R.id.btn0 -> numberButtonClicked("0")
            R.id.btn1 -> numberButtonClicked("1")
            R.id.btn2 -> numberButtonClicked("2")
            R.id.btn3 -> numberButtonClicked("3")
            R.id.btn4 -> numberButtonClicked("4")
            R.id.btn5 -> numberButtonClicked("5")
            R.id.btn6 -> numberButtonClicked("6")
            R.id.btn7 -> numberButtonClicked("7")
            R.id.btn8 -> numberButtonClicked("8")
            R.id.btn9 -> numberButtonClicked("9")
            R.id.btnPlus -> operatorButtonClicked("+")
            R.id.btnMinus -> operatorButtonClicked("-")
            R.id.btnDivider -> operatorButtonClicked("/")
            R.id.btnModular -> operatorButtonClicked("%")
            R.id.btnMulti -> operatorButtonClicked("*")
        }
    }

    private fun operatorButtonClicked(operator: String) {
        if (expressionTv.text.isEmpty()) {
            return
        }
        when {
            //Operater??? ???????????? ?????? ??? Operator??? ????????????
            isOperator -> {
                val text = expressionTv.text.toString()
                expressionTv.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "???????????? ??? ?????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTv.append(" $operator")
            }
        }

        //????????? ?????? ????????????
        //span ?????? ??????
        val ssb = SpannableStringBuilder(expressionTv.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTv.text.length - 1,
            expressionTv.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        expressionTv.text = ssb

        hasOperator = true
        isOperator = true
    }

    private fun numberButtonClicked(number: String) {

        Log.d("tttt", number)
        if (isOperator) {
            expressionTv.append(" ")
        }
        isOperator = false

        val expressionText = expressionTv.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0??? ?????? ?????? ??? ??? ????????????.", Toast.LENGTH_SHORT).show()
            return
        }
        expressionTv.append(number)
        resultTv.text =  calculateExpression()
        Log.d("tttt", number + " " + expressionTv.text)
    }

    fun resultButtonClicked(v: View) {
        // ??????????????? ?????? ????????? ????????? ?????? ??????
        val expressionTexts = expressionTv.text.split(' ')

        if(expressionTv.text.isEmpty() || expressionTexts.size==1){
            return
        }
        if(expressionTexts.size != 3 && hasOperator){
            Toast.makeText(this, "?????? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = expressionTv.text.toString()
        val resultText = calculateExpression()


        //?????? ?????????
        Thread(Runnable {
            //uid??? pk??? null??? ????????? ???????????? ????????? ?????????
            db.historyDao().insertHistory(History( null, expressionText, resultText ))
        }).start()

        resultTv.text = ""
        expressionTv.text = resultText

        isOperator = false
        hasOperator = false
    }

    private fun calculateExpression(): String {
        val expressionTexts = expressionTv.text.split(' ')

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

    fun clearButtonClicked(v: View) {
        expressionTv.text =""
        resultTv.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyButtonClicked(v: View) {
        historyLayout.isVisible = true
        historyLinearLayout.removeAllViews()

        //Ui??????????????? ????????? ?????? ???????????? ??????
        Thread(Runnable {
            db.historyDao().getAll().reversed().forEach {

                //????????? ?????? ?????? attach
                //????????? ????????? ?????? ??????
                runOnUiThread {
                    val historyView = LayoutInflater.from(this).inflate(R.layout.history_row, null, false)
                    historyView.findViewById<TextView>(R.id.expressionTv).text = it.expression
                    historyView.findViewById<TextView>(R.id.resultTv).text = "= ${it.result}"

                    historyLinearLayout.addView(historyView)
                }
            }
        }).start()
    }

    fun historyClearButtonClicked(v : View){

        historyLinearLayout.removeAllViews()

        Thread(Runnable {
            db.historyDao().deletAll()
        }).start()
    }

    fun closeHistoryButtonClicked(v : View){
        historyLayout.isVisible = false
    }
}

fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}