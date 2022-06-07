package kyj.practice.livedataviewmodeldatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kyj.practice.livedataviewmodeldatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val numViewModel: NumViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()

        //view는 viewModel의 데이터를 구독->변경 감지
//        numViewModel.currentNum.observe(this, Observer{
////            binding.numTextView.text = it.toString()
//        })
//
        binding.numViewModel = numViewModel

        //observable필요 없음
        binding.lifecycleOwner = this

    }

    private fun initButtons(){
        binding.upButton.setOnClickListener {
            numViewModel.setNum(ActionType.UP)
        }
        binding.downButton.setOnClickListener {
            numViewModel.setNum(ActionType.DOWN)
        }
    }
}