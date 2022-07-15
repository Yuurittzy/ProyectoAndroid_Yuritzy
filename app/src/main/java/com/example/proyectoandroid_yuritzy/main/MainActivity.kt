package com.example.proyectoandroid_yuritzy.main

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.checkout.CheckoutFragment
import com.example.proyectoandroid_yuritzy.databinding.ActivityMainBinding
import com.example.proyectoandroid_yuritzy.favorite.FavoritesFragment
import com.example.proyectoandroid_yuritzy.profile.ProfileFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setCurrentFragment(MainFragment())
        setUpBottomSheet()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getActualLocation()
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

    private fun getActualLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        task.addOnSuccessListener {
            if (it != null){
                getAddress(it.latitude, it.longitude)
            }
        }
    }

    private fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng,1)
        val address = list[0].getAddressLine(0)
        val postalCode = list[0].postalCode

        findViewById<TextView>(R.id.textView_location).text = getString(R.string.address, address, postalCode)
    }

}