package com.clean.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.clean.presentation.R
import com.clean.presentation.base.BaseActivity
import com.clean.presentation.databinding.ActivityMainBinding
import com.clean.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel by viewModels<MainViewModel>()



    override fun init() {

    }
}