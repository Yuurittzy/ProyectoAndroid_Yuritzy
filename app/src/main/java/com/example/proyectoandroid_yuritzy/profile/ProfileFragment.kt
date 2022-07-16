package com.example.proyectoandroid_yuritzy.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.address.AddressFragment
import com.example.proyectoandroid_yuritzy.favorite.FavoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<TextView>(R.id.textView_favorite).setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.btn_nav_view)?.selectedItemId = R.id.fav
        }

        view.findViewById<TextView>(R.id.textView_car).setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.btn_nav_view)?.selectedItemId = R.id.shopping_cart
        }

        view.findViewById<TextView>(R.id.textView_address).setOnClickListener {
            setCurrentFragment(AddressFragment())
        }

        view.findViewById<TextView>(R.id.textView_edit_profile).setOnClickListener {

        }

        view.findViewById<TextView>(R.id.textView_help).setOnClickListener {

        }

        return view
    }

    private fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_main, fragment)
            commit()
        }
    }

}