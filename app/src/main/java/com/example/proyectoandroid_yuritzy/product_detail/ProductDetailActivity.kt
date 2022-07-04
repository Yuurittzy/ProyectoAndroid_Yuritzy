package com.example.proyectoandroid_yuritzy.product_detail

import android.graphics.PorterDuff.Mode.MULTIPLY
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.databinding.ActivityProductDetailBinding
import com.example.proyectoandroid_yuritzy.main.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    private var isProductLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let { setProductDetail(it.get("product") as Product) }

        onClickLikeButton()
    }

    private fun setProductDetail(product: Product) {
        binding.themathic.text = getString(R.string.thematic, product.name)
        binding.ratingBar.rating = product.rating.toFloat()
        binding.ivPreview.setImageDrawable(ContextCompat.getDrawable(this, product.previewImage))
        binding.tvInitialPrice.text = getString(R.string.price_initial, product.price)
        binding.tvFinalPrice.text = getString(R.string.price_final, product.priceWithDiscount)
        binding.tvDiscount.text = "% ${getString(R.string.discount, product.discountPercent)}"
    }

    private fun onClickLikeButton() {
        binding.ivFavourite.setOnClickListener {
            binding.ivFavourite.setColorFilter(
                ContextCompat.getColor(this, if (!isProductLiked) R.color.orange else R.color.gray), MULTIPLY)
                isProductLiked = !isProductLiked
        }
    }

}