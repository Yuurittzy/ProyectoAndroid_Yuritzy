package com.example.proyectoandroid_yuritzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.block2.root.setOnClickListener {
            startActivity(Intent(this, ProductDetailActivity::class.java))

        }
    }

}