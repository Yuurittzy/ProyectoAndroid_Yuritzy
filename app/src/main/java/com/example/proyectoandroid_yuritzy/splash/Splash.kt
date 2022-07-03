package com.example.proyectoandroid_yuritzy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.main.MainActivity
import kotlin.concurrent.thread

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        thread{
            Thread.sleep(1000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}