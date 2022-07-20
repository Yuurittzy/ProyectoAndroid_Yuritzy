package com.example.proyectoandroid_yuritzy.database

import androidx.room.*
import com.example.proyectoandroid_yuritzy.address.Address
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface AddressDao {

    @Query("SELECT * FROM address WHERE id = :id")
    fun getAddressById(id: Long): Single<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address): Completable

    @Update
    fun updateAddress(address: Address): Completable

}