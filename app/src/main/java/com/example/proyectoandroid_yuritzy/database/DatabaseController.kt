package com.example.proyectoandroid_yuritzy.database

import android.content.Context
import androidx.room.Room
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.core.Single

class DatabaseController(context: Context) {

    private val dataBase = Room.databaseBuilder(context, AppDataBase::class.java, "database-name").build()
    private val productDao = dataBase.productDao()

    fun getProducts(): Single<List<Product>> {
        return productDao.getProducts()
    }

    fun addProduct(product: Product) {
        productDao.addProduct(product)
    }

    fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

}