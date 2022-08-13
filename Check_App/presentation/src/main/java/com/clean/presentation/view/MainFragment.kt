package com.clean.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.clean.presentation.R
import com.clean.presentation.adapter.ScoreRecyclerViewAdapter
import com.clean.presentation.base.BaseFragment
import com.clean.presentation.databinding.FragmentMainBinding
import com.clean.presentation.viewmodel.MainViewModel
import com.clean.presentation.widget.extension.showVertical

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.fragment = this
        observeDatas()
        mainViewModel.getStatisticsDisplay()
        mainViewModel.getScore()
    }


    fun startBtnClick(view: View){
        this.findNavController().navigate(R.id.action_mainFragment_to_manNameFragment)
    }

    private fun observeDatas(){
        mainViewModel.getStatisticsEvent.observe(viewLifecycleOwner){
            binding.statisticsTextView.text = it.toString()
        }

        mainViewModel.getScoreEvent.observe(viewLifecycleOwner){
            Log.d("abc", "observeDatas: ${mainViewModel.scoreList}")
            initRecyclerView()
        }
    }

    private fun initRecyclerView(){
        binding.scoreRecyclerView.adapter = ScoreRecyclerViewAdapter(mainViewModel)
        binding.scoreRecyclerView.showVertical(requireContext())
    }

}