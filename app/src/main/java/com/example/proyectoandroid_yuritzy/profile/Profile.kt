package com.example.proyectoandroid_yuritzy.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(@PrimaryKey val id: Int,
                   @ColumnInfo(name = "picture") val picture: String = "",
                   @ColumnInfo(name = "name") val name: String = "")