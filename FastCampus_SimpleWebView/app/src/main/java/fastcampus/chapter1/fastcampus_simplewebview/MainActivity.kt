package fastcampus.chapter1.fastcampus_simplewebview

import android.annotation.SuppressLint
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import fastcampus.chapter1.fastcampus_simplewebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(){
        //http(clearText, 보안 신경x)로 접근시 외부 브라우저(기기의 디폴트 브라우저)로 웹이 켜지는 일을
        // 웹뷰클라이언트를 내가 만든 앱의 웹뷰클라이언트로 오버라이드하여 방지
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
                handler?.proceed()
            }
        }
        //웹뷰 켜져도 웹뷰 내의 컴포넌트들이 동작 안 함(대부분의 웹은 자바스크립트로 동작이 구현되어있기 때문)
        //안드로이드에선 디폴트로 자바스크립트 관련된 것들을 허용하지 않음(보안상의 이슈)
        //웹뷰 사용할 때 자바스크립트도 사용하겠다고 명시해야 함
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("http://www.google.com")
    }
}