package com.example.proyectoandroid_yuritzy.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Product(@PrimaryKey val id: Int,
              @ColumnInfo(name = "name") val name: String? = "",
              @ColumnInfo(name = "image" )val image: Int? = 0,
              @ColumnInfo(name = "preview_image") val previewImage: Int? = 0,
              @ColumnInfo(name = "rating") val rating: Int? = 0,
              @ColumnInfo(name = "price") val price: Int? = 0,
              @ColumnInfo(name = "price_with_discount") val priceWithDiscount: Int? = 0,
              @ColumnInfo(name = "discount_percent") val discountPercent: Int? = 0,
              @ColumnInfo(name = "quantity") val quantity: Int? = 1): Parcelable