package com.example.proyectoandroid_yuritzy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectoandroid_yuritzy.address.Address
import com.example.proyectoandroid_yuritzy.main.Product

@Database(entities = [Product::class, Address::class], version = 4)
abstract class AppDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun checkoutDao(): CheckoutDao

    abstract fun addressDao(): AddressDao

}