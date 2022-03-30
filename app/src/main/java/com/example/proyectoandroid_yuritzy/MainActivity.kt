package com.example.proyectoandroid_yuritzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding= ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        setContentView(R.layout.activity_main)

    }

    fun Click(view: android.view.View) {
        startActivity(Intent(this, MainActivity2::class.java))
        //finish()
    }


}