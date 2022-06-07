package kyj.practice.livedataviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ActionType{
    PLUS, MINUS
}

//데이터의 변경
//뷰모델은 데이터의 변경사항을 알려주는 라이브 데이터를 가지고 있다.
class MyNumViewModel : ViewModel(){
    //뮤터블 라이브 데이터 - 값 변경 가능
    //라이브 데이터 - 값 변경 불가능
    //코틀린 컨벤션
    //내부 사용하는 변수 _abc
    //외부에서 접근하는 변수 abc
    //getter를 커스텀하여 외부에서 abc를 호출할 때 값이 변경 가능한 _abc를 abc에 저장하여 가져오기
    //내부에서 설정하는 자료형은 뮤터블로 변경가능하도록 설정
    private val _currentValue = MutableLiveData<Int>()

    val currentValue : LiveData<Int>
        get() = _currentValue

    //초기값 설정
    init{
        Log.d(TAG, "MyNuberViewModel: init ")
        _currentValue.value = 0
    }

    //뷰모델이 가지고 있는 값을 변경
    fun updateValue(actionType: ActionType, input: Int){
        when(actionType){
            ActionType.PLUS ->{
                _currentValue.value = _currentValue.value?.plus(input)
            }
            ActionType.MINUS ->{
                _currentValue.value = _currentValue.value?.minus(input)
            }
        }
    }


    companion object {
        private const val TAG = "MyNumViewModel"
    }
}