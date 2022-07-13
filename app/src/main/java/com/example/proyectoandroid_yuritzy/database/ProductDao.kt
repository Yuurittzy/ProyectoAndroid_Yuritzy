package com.example.proyectoandroid_yuritzy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): Single<List<Product>>

    @Insert
    fun addProduct(product: Product): Completable

    @Delete
    fun deleteProduct(product: Product): Completable

}