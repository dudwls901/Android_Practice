package com.example.retrofit2practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.retrofit2practice.Utils.Constants.TAG
import com.example.retrofit2practice.Utils.RESPONSE_STATE
import com.example.retrofit2practice.databinding.ActivityMainBinding
import com.example.retrofit2practice.retrofit.RetrofitManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnInit()

        setTextModel()

    }

    private fun setTextModel(){
        val textModel = TextModel("abcabda")
        binding.textModel = textModel
    }


    private fun btnInit(){
        binding.btnSearch.setOnClickListener{
            Log.d(TAG, "검색 버튼 클릭")

            //검색 api 호출
            RetrofitManager.instance.searchPhotos(searchTerm = binding.etSearch.text.toString(), completion = {
                //it == response.raw().toString()
                    responsState, responseBody ->
                when(responsState){
                    RESPONSE_STATE.OKAY -> {
                        Log.d(TAG, "api 호출 성공 : $responseBody")
                    }
                    RESPONSE_STATE.FAIL ->{
                        Log.d(TAG,binding.etSearch.text.toString())
                        Toast.makeText(this,"api 호출 에러입니다.",Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호출 실패 : $responseBody")
                    }
                }
            })
        }
    }

}