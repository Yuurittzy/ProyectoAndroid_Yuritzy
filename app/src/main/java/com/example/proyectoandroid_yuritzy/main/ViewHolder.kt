package com.example.proyectoandroid_yuritzy.main

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.product_detail.ProductDetailActivity

class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_namebox).text = product.name
        view.findViewById<ImageView>(R.id.iv_box).setImageDrawable(ContextCompat.getDrawable(itemView.context, product.image))
        onClick(product)
    }

    private fun onClick(product: Product) {
        view.setOnClickListener {
            val intent = Intent(Intent(view.context, ProductDetailActivity::class.java))
                .putExtra(PRODUCT, product)

            view.context.startActivity(intent)
        }
    }

    companion object {
        private const val PRODUCT = "product"
    }

}