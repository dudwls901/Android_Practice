package fastcampus.part3.fastcampus_tinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fastcampus.part3.fastcampus_tinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


    }

    override fun onStart() {
        super.onStart()
        //로그인 되지 않았으면 loginActivity로 이동
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else{
            startActivity(Intent(this, LikeActivity::class.java))
            finish()
        }
    }

}