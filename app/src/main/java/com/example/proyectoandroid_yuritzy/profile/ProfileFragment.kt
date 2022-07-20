package com.example.proyectoandroid_yuritzy.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.address.AddressFragment
import com.example.proyectoandroid_yuritzy.database.ProfileDatabaseController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var profileDatabaseController: ProfileDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileDatabaseController = ProfileDatabaseController(context?: requireContext())

        getProfile(view)

        view.findViewById<TextView>(R.id.textView_favorite).setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.btn_nav_view)?.selectedItemId = R.id.fav
        }

        view.findViewById<TextView>(R.id.textView_car).setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.btn_nav_view)?.selectedItemId = R.id.shopping_cart
        }

        view.findViewById<TextView>(R.id.textView_address).setOnClickListener { setCurrentFragment(AddressFragment()) }

        view.findViewById<TextView>(R.id.textView_edit_profile).setOnClickListener { setCurrentFragment(EditProfileFragment()) }

        view.findViewById<TextView>(R.id.textView_help).setOnClickListener { help() }

        return view
    }

    private fun getProfile(view: View) {
        compositeDisposable.add(profileDatabaseController.getProfileById(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.findViewById<TextView>(R.id.textView_name).text = "Hola, ${it.name}!" }, {
                Toast.makeText(context?: requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }))
    }

    private fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.fragment_main, fragment)
            commit()
        }
    }

    private fun help() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hola, buen día. \nNecesito ayuda relacionada con la aplicacion Yuu Ritzy.")
        try {
            activity?.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(activity, "Hubo un error enviando el mensaje.", Toast.LENGTH_SHORT).show()
        }
    }

}