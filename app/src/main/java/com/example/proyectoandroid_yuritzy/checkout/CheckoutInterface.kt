package com.example.proyectoandroid_yuritzy.checkout

import com.example.proyectoandroid_yuritzy.main.Product

interface CheckoutInterface {

    fun updateProductQuantity(product: Product)

    fun deleteProductQuantity(product: Product)

}