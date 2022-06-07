package com.example.retrofit2practice1.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.retrofit2practice1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSubmitButton()


    }
    private fun initSubmitButton(){
        binding.submitButton.setOnClickListener {
            val intent = Intent(this, GithubRepositoryActivity::class.java)
            intent.putExtra("searchId",binding.idEditText.text.toString())
            startActivity(intent)
            Log.d("mainactivity","??????????")
        }
    }

}