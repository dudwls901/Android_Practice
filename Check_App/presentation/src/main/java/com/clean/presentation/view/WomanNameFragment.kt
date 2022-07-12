package com.clean.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.clean.domain.utils.ErrorType
import com.clean.domain.utils.ScreenState
import com.clean.presentation.R
import com.clean.presentation.base.BaseFragment
import com.clean.presentation.databinding.FragmentWomanNameBinding
import com.clean.presentation.viewmodel.MainViewModel

class WomanNameFragment : BaseFragment<FragmentWomanNameBinding>(R.layout.fragment_woman_name) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.fragment = this
        observeViewModel()
    }
    fun nextBtnClick(view: View) {
        binding.loadingBar.visibility = View.VISIBLE
        mainViewModel.checkLoveCalculator(
            "love-calculator.p.rapidapi.com",
            "e6ce9e73famshdecbcaf7987deaap176b51jsn4e5d45fdba88",
            mainViewModel.manNameResult,
            binding.womanNameEditText.text.toString()
        )
    }

    private fun observeViewModel(){
        mainViewModel.apiCallEvent.observe(viewLifecycleOwner){
            binding.loadingBar.visibility = View.INVISIBLE
            when(it){
                ScreenState.LOADING -> this.findNavController().navigate(R.id.action_womanNameFragment_to_resultFragment)
                ScreenState.ERROR ->  toastErrorMsg()
                else -> shortShowToast("알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    private fun toastErrorMsg(){
        when(mainViewModel.apiErrorType){
            ErrorType.NETWORK -> longShowToast("네트워크 오류가 발생했습니다.")
            ErrorType.SESSION_EXPIRED -> longShowToast("세션이 만료되었습니다.")
            ErrorType.TIMEOUT -> longShowToast("호출 시간이 초과되었습니다.")
            ErrorType.UNKNOWN -> longShowToast("예기치 못한 오류가 발생했습니다.")
        }
    }

}