package fastcampus.part3.fastcampus_tinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcampus.part3.fastcampus_tinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}