package kyj.practice.livedataviewmodeldatabinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NumViewModel : ViewModel() {
    private var _currentNum = MutableLiveData<Int>()

    val currentNum : LiveData<Int>
        get() = _currentNum

    init{
        _currentNum.value = 0
    }

    fun setNum(type: ActionType){
        when(type){
            ActionType.UP->{
                _currentNum.value = _currentNum.value?.plus(1)
            }
            ActionType.DOWN->{
                _currentNum.value = _currentNum.value?.minus(1)
            }
        }
    }

}