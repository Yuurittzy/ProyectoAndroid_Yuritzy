package com.example.proyectoandroid_yuritzy.checkout

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.main.Product

class CheckoutViewHolder(private val view: View, private val checkoutInterface: CheckoutInterface): RecyclerView.ViewHolder(view) {

    fun setItem(product: Product) {
        view.findViewById<TextView>(R.id.tv_thematic_checkout).text = view.context.getString(R.string.thematic, product.name)
        view.findViewById<ImageView>(R.id.imageView_product_checkout).setImageDrawable(ContextCompat.getDrawable(view.context, product.image?: 0))
        view.findViewById<TextView>(R.id.tv_final_price_checkout).text = view.context.getString(R.string.price_final, product.priceWithDiscount)
        view.findViewById<TextView>(R.id.tv_quantity).text = product.quantity.toString()

        view.findViewById<ImageView>(R.id.tv_minus).setOnClickListener {
            if (product.quantity == 1) {
                checkoutInterface.deleteProductQuantity(product)
            } else {
                checkoutInterface.updateProductQuantity(product.copy(quantity = (product.quantity?: 0) - 1))
            }
        }

        view.findViewById<ImageView>(R.id.tv_more).setOnClickListener {
            checkoutInterface.updateProductQuantity(product.copy(quantity = (product.quantity?: 0) + 1))
        }
    }

}