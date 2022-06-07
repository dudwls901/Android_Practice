package kyj.practice.kakaologin.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kyj.practice.kakaologin.domain.model.LoginModel

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var _loginInfo = MutableLiveData<LoginModel>()

    val loginInfo: LiveData<LoginModel>
        get() = _loginInfo

    init {
        _loginInfo.value = LoginModel(
            1,
            "ab",
            "abc",
            "ccc"
        )
    }

    fun setLoginInfo(loginModel: LoginModel) {
        Log.d("kkkkkk", loginModel.toString())
        _loginInfo.value = loginModel ?: LoginModel(
            1,
            "ab",
            "abc",
            "ccc"
        )
    }


}