package com.example.proyectoandroid_yuritzy.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R

class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_namebox).text = product.name
        view.findViewById<ImageView>(R.id.iv_box).setImageDrawable(ContextCompat.getDrawable(itemView.context, product.image))
    }

}