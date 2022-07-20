package com.example.proyectoandroid_yuritzy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectoandroid_yuritzy.profile.Profile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfileById(id: Long): Single<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfile(profile: Profile): Completable

}