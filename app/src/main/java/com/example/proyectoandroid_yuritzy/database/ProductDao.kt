package com.example.proyectoandroid_yuritzy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectoandroid_yuritzy.main.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): List<Product>

    @Insert
    fun addProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

}