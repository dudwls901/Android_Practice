package fastcampus.part3.fastcampus_tinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fastcampus.part3.fastcampus_tinder.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //same
        auth = Firebase.auth
        //auth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordEditText()
        initFacebookLoginButton()
    }

    private fun initLoginButton() {
        binding.loginButton.setOnClickListener {
            //signInWithEmailAndPassword 구현부를 보면 email과 password를 non-null로 받는다
            //따라서 email과 password가 null일 경우 에러
            val email = getInputEmail()
            val password = getPasswordEmail()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        //로그인이 성공적으로 완료되면 현재 액티비티를 종료
                        finish()
                    }else{
                        Toast.makeText(this,"로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initSignUpButton() {
        binding.signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getPasswordEmail()

            //파베에서 이메일 형식까지 검사해줌 대신 email null처리는 내가
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task->
                    Log.d(TAG, "${task.isSuccessful} email : ${email} password : ${password}")
                    if(task.isSuccessful){
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인 버튼을 눌러 로그인해주세요.",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    //email, password를 null로 넘기지 않기 위한 처리
    private fun initEmailAndPasswordEditText(){
        //에딧 텍스트의 텍스트 변경 리스너를 받아서 두 값이 다 있는 경우만 로그인, 회원가입 버튼 활성화
        binding.emailEditText.addTextChangedListener {
            val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
            binding.loginButton.isEnabled = enable
            binding.signUpButton.isEnabled = enable
        }
        binding.passwordEditText.addTextChangedListener {
            val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
            binding.loginButton.isEnabled = enable
            binding.signUpButton.isEnabled = enable
        }

    }

    private fun initFacebookLoginButton(){
        //페이스북에서 이메일과 프로필 가져오기
        binding.facebookLoginButton.setPermissions("email", "public_profile")
        binding.facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                // 로그인이 성공적
                //로그인 액세스 토큰을 가져와서 파이어베이스에 넘기기
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if(task.isSuccessful){
                            finish()
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다.",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getInputEmail() = binding.emailEditText.text.toString()


    private fun getPasswordEmail() = binding.passwordEditText.text.toString()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        const val TAG = "LoginActivity"
    }

}