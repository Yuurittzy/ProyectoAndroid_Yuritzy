package com.example.proyectoandroid_yuritzy.favorite

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.main.Product

class FavoritesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_thematic).text = product.name
    }

}