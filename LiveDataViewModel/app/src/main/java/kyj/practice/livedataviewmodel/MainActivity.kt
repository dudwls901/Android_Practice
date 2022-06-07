package kyj.practice.livedataviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kyj.practice.livedataviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val myNumViewModel: MyNumViewModel by viewModels()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myNumViewModel.currentValue.observe(this, Observer {
            Log.d(TAG,"mainactivity - mnynumverviewmodel - currentvalue 라이브 데이터 값 변경 : $it")
            binding.numberTextView.text = it.toString()
        })

        binding.plusButton.setOnClickListener(this)
        binding.minusButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Log.d(TAG,"onclick $view")
        val userInput = binding.userInputEditText.text.toString().toInt()
        //뷰모델에 라이브데이터 값을 변경하는 메소드 실행
        when(view){
            binding.plusButton->{
                myNumViewModel.updateValue(actionType = ActionType.PLUS, userInput)
            }
            binding.minusButton->{
                myNumViewModel.updateValue(actionType = ActionType.MINUS, userInput)
            }
        }
    }
    companion object{
        const val TAG: String = "로그"
    }
}


