package com.example.proyectoandroid_yuritzy.database

import android.content.Context
import androidx.room.Room
import com.example.proyectoandroid_yuritzy.main.Product
import com.example.proyectoandroid_yuritzy.profile.Profile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProfileDatabaseController(context: Context) {

    private val dataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, "profile")
        .fallbackToDestructiveMigration()
        .build()

    private val profileDao = dataBase.profileDao()

    fun getProfileById(id: Long): Single<Profile> {
        return profileDao.getProfileById(id)
    }

    fun addProfile(profile: Profile): Completable {
        return profileDao.addProfile(profile)
    }

}