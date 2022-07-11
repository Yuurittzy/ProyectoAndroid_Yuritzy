package com.example.proyectoandroid_yuritzy.database

import android.content.Context
import androidx.room.Room
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class CheckoutDatabaseController(context: Context) {

    private val dataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, "checkout")
        .fallbackToDestructiveMigration()
        .build()

    private val checkoutDao = dataBase.checkoutDao()

    fun getProducts(): Single<List<Product>> {
        return checkoutDao.getProducts()
    }

    fun addProduct(product: Product) {
        checkoutDao.addProduct(product)
    }

    fun updateProduct(product: Product): Completable {
        return checkoutDao.updateProduct(product.id, product.quantity?: 0)
    }

    fun deleteProduct(product: Product): Completable {
        return checkoutDao.deleteProduct(product)
    }

}