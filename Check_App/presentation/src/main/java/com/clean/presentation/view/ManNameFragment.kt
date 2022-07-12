package com.clean.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clean.presentation.R
import com.clean.presentation.base.BaseFragment
import com.clean.presentation.databinding.FragmentManNameBinding

class ManNameFragment : BaseFragment<FragmentManNameBinding>(R.layout.fragment_man_name) {
    override fun init() {
        binding.fragment = this
    }


    fun startBtnClick(view: View){
        this.findNavController().navigate(R.id.action_manNameFragment_to_womanNameFragment)
    }
}