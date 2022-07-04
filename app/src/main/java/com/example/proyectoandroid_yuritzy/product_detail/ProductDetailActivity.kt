package com.example.proyectoandroid_yuritzy.product_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding
import com.example.proyectoandroid_yuritzy.databinding.ActivityProductDetailBinding
import com.example.proyectoandroid_yuritzy.main.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            setProductDetail(it.get("product") as Product)
        }
    }

    private fun setProductDetail(product: Product) {
        binding.themathic.text = getString(R.string.thematic, product.name)
    }

}