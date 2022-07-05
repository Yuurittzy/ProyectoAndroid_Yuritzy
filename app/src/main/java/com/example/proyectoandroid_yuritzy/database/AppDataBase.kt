package com.example.proyectoandroid_yuritzy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectoandroid_yuritzy.main.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao


}