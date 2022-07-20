package com.example.proyectoandroid_yuritzy.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.address.EditAddressFragment
import com.example.proyectoandroid_yuritzy.checkout.CheckoutFragment
import com.example.proyectoandroid_yuritzy.database.AddressDatabaseController
import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding
import com.example.proyectoandroid_yuritzy.favorite.FavoritesFragment
import com.example.proyectoandroid_yuritzy.profile.ProfileFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: ActivityMainBinding

    private lateinit var addressDatabaseController: AddressDatabaseController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        addressDatabaseController = AddressDatabaseController(this)

        getAddress()
        setCurrentFragment(MainFragment())
        setUpBottomSheet()
        goToEditAddress()
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

    private fun getAddress() {
        compositeDisposable.add(addressDatabaseController.getAddressById(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                findViewById<TextView>(R.id.textView_location)?.text = "¿Compras desde ${it.street} ${it.number} CP ${it.postalCode}?"
            }, {
                findViewById<TextView>(R.id.textView_location)?.text = getString(R.string.enter_your_addess)
            }))
    }

    private fun goToEditAddress() {
        findViewById<ConstraintLayout>(R.id.layout_landing_page).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_main, EditAddressFragment())
                commit()
            }
        }
    }

}