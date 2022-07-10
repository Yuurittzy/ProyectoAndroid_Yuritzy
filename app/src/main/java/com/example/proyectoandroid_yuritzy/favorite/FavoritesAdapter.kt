package com.example.proyectoandroid_yuritzy.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.main.Product

class FavoritesAdapter(private val items: List<Product>, private val favoritesInterface: FavoritesInterface): RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_product, parent, false)
        return FavoritesViewHolder(view, favoritesInterface)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size

}