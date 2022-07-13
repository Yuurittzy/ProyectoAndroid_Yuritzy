package com.example.proyectoandroid_yuritzy.favorite

import com.example.proyectoandroid_yuritzy.main.Product

interface FavoritesInterface {

    fun deleteFavoriteProduct(product: Product)

    fun addProductToCar(product: Product)

}