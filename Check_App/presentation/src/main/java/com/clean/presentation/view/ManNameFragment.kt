package com.clean.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.clean.domain.utils.ErrorType
import com.clean.domain.utils.ScreenState
import com.clean.presentation.R
import com.clean.presentation.base.BaseFragment
import com.clean.presentation.databinding.FragmentManNameBinding
import com.clean.presentation.viewmodel.MainViewModel

class ManNameFragment : BaseFragment<FragmentManNameBinding>(R.layout.fragment_man_name) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.fragment = this
    }

    fun nextBtnClick(view: View){
        mainViewModel.manNameResult = binding.manNameEditText.text.toString()
        this.findNavController().navigate(R.id.action_manNameFragment_to_womanNameFragment)
    }
}