package com.example.proyectoandroid_yuritzy.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R

class ProductsViewHolder(private val view: View, private val mainInterface: MainInterface): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_namebox).text = product.name
        view.findViewById<ImageView>(R.id.iv_box).setImageDrawable(product.image?.let { ContextCompat.getDrawable(itemView.context, it) })
        onClick(product)
    }

    private fun onClick(product: Product) {
        view.findViewById<View>(R.id.layout_product).setOnClickListener {
            mainInterface.openProductDetail(product)
        }
    }

}