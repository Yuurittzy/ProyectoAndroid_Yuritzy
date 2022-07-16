package com.example.proyectoandroid_yuritzy.database

import android.content.Context
import androidx.room.Room
import com.example.proyectoandroid_yuritzy.address.Address
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AddressDatabaseController(context: Context) {

    private val dataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, "addresses")
        .fallbackToDestructiveMigration()
        .build()

    private val addressDao = dataBase.addressDao()

    fun getAddressById(id: Long): Single<Address> {
        return addressDao.getAddressById(id)
    }

    fun addAddress(address: Address): Completable {
        return addressDao.addAddress(address)
    }

    fun updateAddress(address: Address): Completable {
        return addressDao.updateAddress(address)
    }

}