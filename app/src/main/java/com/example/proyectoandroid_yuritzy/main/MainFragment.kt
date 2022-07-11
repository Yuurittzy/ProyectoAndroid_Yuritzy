package com.example.proyectoandroid_yuritzy.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R

class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view: View) {
        val items = listOf(
            Product(2, getString(R.string.itachi), R.drawable.itachi, R.drawable.itachi_preview, 3, 1000, 600, 40),
            Product(0, getString(R.string.naruto), R.drawable.naruto, R.drawable.naruto_preview, 3, 1000, 900, 10),
            Product(4, getString(R.string.sakura), R.drawable.sakura, R.drawable.sasuke_preview, 5, 1000, 700, 30),
            Product(3, getString(R.string.kakashi), R.drawable.kakashi, R.drawable.kakashi_preview, 5, 1000, 900, 10),
            Product(5, getString(R.string.minato), R.drawable.minato, R.drawable.minato_preview, 4, 1000, 700, 30),
            Product(1, getString(R.string.sasuke), R.drawable.sasuke, R.drawable.sasuke_preview, 4, 1000, 800, 20),)

        view.findViewById<RecyclerView>(R.id.recyclerView)?.adapter = ProductsAdapter(items)
    }

}