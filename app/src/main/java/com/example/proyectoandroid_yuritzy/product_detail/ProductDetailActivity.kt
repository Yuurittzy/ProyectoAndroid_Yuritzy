package com.example.proyectoandroid_yuritzy.product_detail

import android.graphics.PorterDuff.Mode.MULTIPLY
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.CheckoutDatabaseController
import com.example.proyectoandroid_yuritzy.database.ProductsDatabaseController
import com.example.proyectoandroid_yuritzy.databinding.ActivityProductDetailBinding
import com.example.proyectoandroid_yuritzy.main.Product
import kotlin.concurrent.thread

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var productsDatabaseController: ProductsDatabaseController
    private lateinit var checkoutDatabaseController: CheckoutDatabaseController

    private var product = Product(0)

    private var isProductLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.extras?.let { setProductDetail(it.get("product") as Product) }

        productsDatabaseController = ProductsDatabaseController(this)
        checkoutDatabaseController = CheckoutDatabaseController(this)

        onClickLikeButton()
        onClickAddToCar()
    }

    private fun setProductDetail(product: Product) {
        this.product = product

        binding.themathic.text = getString(R.string.thematic, product.name)
        binding.ratingBar.rating = product.rating?.toFloat()?: 0F
        binding.ivPreview.setImageDrawable(product.previewImage?.let { ContextCompat.getDrawable(this, it) })
        binding.tvInitialPrice.text = getString(R.string.price_initial, product.price)
        binding.tvFinalPrice.text = getString(R.string.price_final, product.priceWithDiscount)
        binding.tvDiscount.text = "% ${getString(R.string.discount, product.discountPercent)}"
    }

    private fun onClickLikeButton() {
        binding.ivFavourite.setOnClickListener {
            binding.ivFavourite.setColorFilter(
                ContextCompat.getColor(this, if (!isProductLiked) R.color.orange else R.color.gray), MULTIPLY)
                isProductLiked = !isProductLiked

            if (isProductLiked) {
                thread { productsDatabaseController.addProduct(product) }
            }
        }
    }

    private fun onClickAddToCar() {
        binding.button3.setOnClickListener {
            thread { checkoutDatabaseController.addProduct(product) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}