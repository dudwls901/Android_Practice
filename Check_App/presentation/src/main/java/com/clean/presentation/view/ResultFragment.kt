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
import com.clean.presentation.databinding.FragmentResultBinding
import com.clean.presentation.viewmodel.MainViewModel

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.fragment = this
        initResult()
    }

    private fun initResult() {
        binding.scoreTextView.text = mainViewModel.apiCallResult.percentage.toString()
        saveStatistics()
        when (mainViewModel.apiCallResult.percentage) {
            in 0..20 -> setLoveMsgText("조금 힘들어 보여요")
            in 21..40 -> setLoveMsgText("노력해 보세요")
            in 41..70 -> setLoveMsgText("기대해도 좋겠는데요?")
            in 71..90 -> setLoveMsgText("일단 축하드려요")
            in 91..99 -> setLoveMsgText("와우.. 눈을 의심하고 있어요")
            100 -> setLoveMsgText("완벽하네요! 축하드려요")
            else -> setLoveMsgText("와우????????")
        }
    }

    private fun saveStatistics() {
        mainViewModel.getStatistics()
            .addOnSuccessListener {
                if (it != null) mainViewModel.setStatistics(it.value.toString().toInt() + 1)
                    .addOnFailureListener {
                        error()
                    }
            }
            .addOnFailureListener {
                error()
            }
    }

    private fun error() = shortShowToast("통계를 저장하는 데 오류가 발생했습니다.")

    private fun setLoveMsgText(msg: String) = binding.scoreMessageTextView.setText(msg)

    fun backMainBtnClick(view: View) {
        this.findNavController().navigate(R.id.action_resultFragment_to_mainFragment)
    }
}