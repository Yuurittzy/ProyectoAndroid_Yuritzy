package com.example.proyectoandroid_yuritzy.database

import android.content.Context
import androidx.room.Room
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductsDatabaseController(context: Context) {

    private val dataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, "products")
        .fallbackToDestructiveMigration()
        .build()

    private val productDao = dataBase.productDao()

    fun getProducts(): Single<List<Product>> {
        return productDao.getProducts()
    }

    fun addProduct(product: Product) {
        productDao.addProduct(product)
    }

    fun deleteProduct(product: Product): Completable {
        return productDao.deleteProduct(product)
    }

}