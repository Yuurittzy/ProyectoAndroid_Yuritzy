package com.example.proyectoandroid_yuritzy.favorite

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.main.Product

class FavoritesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_thematic).text = product.name
        view.findViewById<ImageView>(R.id.imageView2).setImageDrawable(ContextCompat.getDrawable(view.context, product.image?: 0))
        view.findViewById<RatingBar>(R.id.ratingBar2).rating = product.rating?.toFloat()?: 0F
        view.findViewById<TextView>(R.id.tv_final_price).text = view.context.getString(R.string.price_final, product.priceWithDiscount)

    }

}