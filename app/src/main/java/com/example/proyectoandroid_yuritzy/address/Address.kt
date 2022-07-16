package com.example.proyectoandroid_yuritzy.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(@PrimaryKey val id: Int,
                   @ColumnInfo(name = "street") val street: String? = "",
                   @ColumnInfo(name = "number") val number: String? = "",
                   @ColumnInfo(name = "district") val district: String? = "",
                   @ColumnInfo(name = "postal_code") val postalCode: String? = "",
                   @ColumnInfo(name = "municipality") val municipality: String? = "",
                   @ColumnInfo(name = "state") val state: String? = "",
                   @ColumnInfo(name = "country") val country: String? = "")