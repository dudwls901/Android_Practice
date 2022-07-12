package com.clean.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.domain.model.DomainLoveResponse
import com.clean.domain.usecase.CheckLoveCalculatorUseCase
import com.clean.domain.utils.ErrorType
import com.clean.domain.utils.RemoteErrorEmitter
import com.clean.domain.utils.ScreenState
import com.clean.presentation.widget.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoveCalculatorUseCase: CheckLoveCalculatorUseCase
) : ViewModel(), RemoteErrorEmitter {

    private var _apiCallEvent = SingleLiveEvent<ScreenState>()
    val apiCallEvent: LiveData<ScreenState>
        get() = _apiCallEvent

    var apiCallResult = DomainLoveResponse("", "", 0, "")
    var apiErrorType = ErrorType.UNKNOWN
    var apiErrorMessage = "none"
    var manNameResult = "man"
    var womanNameResult = "woman"

    fun checkLoveCalculator(
        host: String,
        key: String,
        mName: String,
        wName: String
    ) = viewModelScope.launch {
        checkLoveCalculatorUseCase.execute(this@MainViewModel, host, key, mName, wName).let { response ->
            //반환값이 null이 아니고 잘 받아져 왔을때
            if (response != null){
                apiCallResult = response
                _apiCallEvent.postValue(ScreenState.LOADING)
            }
            //반환값이 null 일때
            else _apiCallEvent.postValue(ScreenState.ERROR)
        }
    }

    override fun onError(msg: String) {
        apiErrorMessage = msg
    }

    override fun onError(errorType: ErrorType) {
        apiErrorType = errorType
    }

}