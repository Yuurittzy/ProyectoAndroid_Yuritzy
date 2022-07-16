package com.example.proyectoandroid_yuritzy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyectoandroid_yuritzy.address.Address
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface AddressDao {

    @Query("SELECT * FROM address WHERE id = :id")
    fun getAddressById(id: Long): Single<Address>

    @Insert
    fun addAddress(address: Address): Completable

    @Update
    fun updateAddress(address: Address): Completable

}