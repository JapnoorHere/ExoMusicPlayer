package com.droidbytes.exoaudioplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droidbytes.exoaudioplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playMusic.setOnClickListener {
            var intent  = Intent(this@MainActivity,AudioActivity::class.java)
            startActivity(intent)
        }
    }
}