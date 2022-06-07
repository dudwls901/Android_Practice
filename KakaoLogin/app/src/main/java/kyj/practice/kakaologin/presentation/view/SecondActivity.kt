package kyj.practice.kakaologin.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import kyj.practice.kakaologin.databinding.ActivitySecondBinding
import kyj.practice.kakaologin.domain.model.LoginModel
import kyj.practice.kakaologin.presentation.viewmodel.LoginViewModel

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding

    private lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this



        val intent = intent
        val id = intent.getLongExtra("id",-1)
        val nickname : String = intent.getStringExtra("account")?:""
        val account = intent.getStringExtra("nickname")?:""
        val profileUrl = intent.getStringExtra("profileUrl")?:""

        binding.loginViewModel?.setLoginInfo(LoginModel(id,nickname,account,profileUrl))
//        binding?.loginViewModel?.setName(nickname)
    }
}