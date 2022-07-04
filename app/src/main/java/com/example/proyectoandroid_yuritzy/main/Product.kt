package com.example.proyectoandroid_yuritzy.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Product(val name: String,
              val image: Int,
              val previewImage: Int,
              val rating: Int,
              val price: Int,
              val priceWithDiscount: Int,
              val discountPercent: Int): Parcelable