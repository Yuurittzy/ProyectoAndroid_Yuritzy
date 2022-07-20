package com.example.proyectoandroid_yuritzy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectoandroid_yuritzy.address.Address
import com.example.proyectoandroid_yuritzy.main.Product
import com.example.proyectoandroid_yuritzy.profile.Profile

@Database(entities = [Product::class, Address::class, Profile::class], version = 7)
abstract class AppDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun checkoutDao(): CheckoutDao

    abstract fun addressDao(): AddressDao

    abstract fun profileDao(): ProfileDao

}