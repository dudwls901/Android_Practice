package com.clean.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.clean.presentation.R
import com.clean.presentation.base.BaseFragment
import com.clean.presentation.databinding.FragmentMainBinding
import com.clean.presentation.viewmodel.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.fragment = this
        mainViewModel.getStatisticsDisplay()
        observeDatas()
    }


    fun startBtnClick(view: View){
        this.findNavController().navigate(R.id.action_mainFragment_to_manNameFragment)
    }

    private fun observeDatas(){
        mainViewModel.getStatisticsEvent.observe(viewLifecycleOwner){
            binding.statisticsTextView.text = it.toString()
        }
    }

}