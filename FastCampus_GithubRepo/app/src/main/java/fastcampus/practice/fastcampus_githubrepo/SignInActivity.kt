package fastcampus.practice.fastcampus_githubrepo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import fastcampus.practice.fastcampus_githubrepo.databinding.ActivitySignInBinding
import fastcampus.practice.fastcampus_githubrepo.util.AuthTokenProvider
import fastcampus.practice.fastcampus_githubrepo.util.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignInActivity : AppCompatActivity(), CoroutineScope{

    private lateinit var binding: ActivitySignInBinding

    private val authTokenProvider by lazy { AuthTokenProvider(this) }

    //앱이 종료될 때 코루틴 작업을 중지시킬것
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //preference에 기존 저장한 토큰이 있으면 메인으로 이동 구현
        if(checkAuthCodeExist()){
            launchMainActivity()
        }
        else{
            initViews()
        }

    }

    private fun launchMainActivity(){
        startActivity(Intent(this, MainActivity::class.java).apply {
            //메인 액티비티 실행하면 현재 화면 필요 없으니 cleartask
            //메인 액티비티 실행될 때 Signin 종료
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }


    private fun checkAuthCodeExist() : Boolean = authTokenProvider.token.isNullOrEmpty().not()

    private fun initViews() = with(binding){
        loginButton.setOnClickListener {
            loginGitHub()
        }
    }

    // https://github.com/login/oauth/authorize?client_id=asdfadfa
    private fun loginGitHub(){
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id",BuildConfig.GITHUB_CLIENT_ID)
            .build()
        CustomTabsIntent.Builder().build().also{
            it.launchUrl(this, loginUri)
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.getQueryParameter("code")?.let { code ->
            GlobalScope.launch {
                showProgress()
                //getAccessToken 함수는 suspend이기 때문에 join 안 써도 받아올 때까지 기다림
                getAccessToken(code)
                dismissProgress()
                if (checkAuthCodeExist()) {
                    launchMainActivity()
                }
            }
        }
    }

    private suspend fun showProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = true
            progressBar.isGone = false
            progressTextView.isGone = false
        }
    }

    private suspend fun dismissProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = false
            progressBar.isGone = true
            progressTextView.isGone = true
        }
    }

    private suspend fun getAccessToken(code: String){
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitUtil.authApiService.getAccessToken(
                    clientId = BuildConfig.GITHUB_CLIENT_ID,
                    clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
                    code = code
                )
                val accessToken = response.accessToken
                Log.e("accessToken", accessToken)
                if (accessToken.isNotEmpty()) {
                    withContext(coroutineContext) {
                        authTokenProvider.updateToken(accessToken)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@SignInActivity, "로그인 과정에서 에러가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}