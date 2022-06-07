package kyj.practice.kakaologin.presentation.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kyj.practice.kakaologin.R
import kyj.practice.kakaologin.databinding.ActivityMainBinding
import kyj.practice.kakaologin.presentation.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        initButton()

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
                Toast.makeText(this, "login plese", Toast.LENGTH_SHORT).show()
            } else {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {

                        Log.i(
                            TAG, "사용자 정보 요청 성공" +
                                    "\n정보 : ${user.properties}" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )

                        val intent = Intent(this, SecondActivity::class.java)

                        intent.putExtra("id", user.id)
                        intent.putExtra("account", user.kakaoAccount?.email)
                        intent.putExtra("nickname", user.kakaoAccount?.profile?.nickname)
                        intent.putExtra(
                            "profileUrl",
                            user.kakaoAccount?.profile?.thumbnailImageUrl
                        )
                        startActivity(intent)
                    }

                }
            }
        }


    }

    private fun initButton() {
        binding.loginButton.setOnClickListener {
// 로그인 조합 예제

// 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(
                            this,
                            callback = callback
                        )
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        val properties =
                            mapOf("nickname" to "${System.currentTimeMillis()}")

                        UserApiClient.instance.scopes { scopeInfo, error ->
                            if (error != null) {
                                Log.e(TAG, "동의 정보 확인 실패", error)
                            } else if (scopeInfo != null) {
                                Log.i(TAG, "동의 정보 확인 성공\n 현재 가지고 있는 동의 항목 $scopeInfo")
                            }
                        }
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                var scopes = mutableListOf<String>()

                                if (user.kakaoAccount?.emailNeedsAgreement == true) {
                                    scopes.add("account_email")
                                }
                                if (user.kakaoAccount?.birthdayNeedsAgreement == true) {
                                    scopes.add("birthday")
                                }
                                if (user.kakaoAccount?.birthyearNeedsAgreement == true) {
                                    scopes.add("birthyear")
                                }
                                if (user.kakaoAccount?.genderNeedsAgreement == true) {
                                    scopes.add("gender")
                                }
                                if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) {
                                    scopes.add("phone_number")
                                }
                                if (user.kakaoAccount?.profileNeedsAgreement == true) {
                                    scopes.add("profile")
                                }
                                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) {
                                    scopes.add("age_range")
                                }
                                if (user.kakaoAccount?.ciNeedsAgreement == true) {
                                    scopes.add("account_ci")
                                }
                                Log.d("kkkkk", scopes.count().toString())
                                if (scopes.count() > 0) {
                                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                                    UserApiClient.instance.loginWithNewScopes(
                                        this,
                                        scopes
                                    ) { token, error ->
                                        if (error != null) {
                                            Log.e(TAG, "사용자 추가 동의 실패", error)
                                        } else {
                                            Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                                            // 사용자 정보 재요청
                                            UserApiClient.instance.me { user, error ->
                                                if (error != null) {
                                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                                } else if (user != null) {
                                                    Log.i(TAG, "사용자 정보 요청 성공")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }


                        UserApiClient.instance.updateProfile(properties) { error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 저장 실패", error)
                            } else {
                                Log.i(TAG, "사용자 정보 저장 성공")
                            }
                        }

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

        }
    }

}