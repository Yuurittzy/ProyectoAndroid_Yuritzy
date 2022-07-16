package com.example.proyectoandroid_yuritzy.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.checkout.CheckoutFragment
import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding
import com.example.proyectoandroid_yuritzy.favorite.FavoritesFragment
import com.example.proyectoandroid_yuritzy.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setCurrentFragment(MainFragment())
        setUpBottomSheet()

    }


    private fun setUpBottomSheet() {
        binding.btnNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> { setCurrentFragment(MainFragment()); findViewById<TextView>(R.id.textView_title).text = getText(R.string.yuu_ritzy) }

                R.id.fav -> { setCurrentFragment(FavoritesFragment()); findViewById<TextView>(R.id.textView_title).text = getText(R.string.favourites) }

                R.id.account -> { setCurrentFragment(ProfileFragment()); findViewById<TextView>(R.id.textView_title).text = getText(R.string.my_account) }

                R.id.shopping_cart -> { setCurrentFragment(CheckoutFragment()); findViewById<TextView>(R.id.textView_title).text = getText(R.string.shopping_cart) }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_main, fragment)
            commit()
        }
    }


}