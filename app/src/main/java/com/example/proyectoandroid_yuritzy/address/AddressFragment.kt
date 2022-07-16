package com.example.proyectoandroid_yuritzy.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.AddressDatabaseController
import com.example.proyectoandroid_yuritzy.favorite.FavoritesAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddressFragment: Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var addressDatabaseController: AddressDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_address, container, false)

        addressDatabaseController = AddressDatabaseController(context?: requireContext())

        activity?.findViewById<TextView>(R.id.textView_title)?.text = getText(R.string.my_addresses)

        getAddress()

        view.findViewById<TextView>(R.id.textView_change_address).setOnClickListener {

        }

        return view
    }

    private fun getAddress() {
        compositeDisposable.add(addressDatabaseController.getAddressById(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, { }))
    }

}