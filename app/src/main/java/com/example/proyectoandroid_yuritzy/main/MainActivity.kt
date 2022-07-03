package com.example.proyectoandroid_yuritzy.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val items = listOf(Product("Naruto", R.drawable.naruto), Product("Sasuke", R.drawable.sasuke), Product("Sakura", R.drawable.sakura))

        binding.recyclerView.adapter = Adapter(items)
    }

}