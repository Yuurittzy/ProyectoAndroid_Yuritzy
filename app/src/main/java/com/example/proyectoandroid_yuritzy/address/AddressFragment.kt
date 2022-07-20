package com.example.proyectoandroid_yuritzy.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.AddressDatabaseController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddressFragment: Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var addressDatabaseController: AddressDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_address, container, false)

        addressDatabaseController = AddressDatabaseController(context?: requireContext())

        activity?.findViewById<ImageView>(R.id.iv_back)?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.textView_title)?.text = getText(R.string.my_addresses)

        getAddress(view)
        closeDetail()

        view.findViewById<TextView>(R.id.textView_change_address).setOnClickListener {
            setCurrentFragment(EditAddressFragment())
        }

        return view
    }

    private fun getAddress(view: View) {
        compositeDisposable.add(addressDatabaseController.getAddressById(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.apply {
                    findViewById<TextView>(R.id.textView_street)?.text = it.street
                    findViewById<TextView>(R.id.textView_number)?.text = it.number
                    findViewById<TextView>(R.id.textView_district)?.text = it.district
                    findViewById<TextView>(R.id.textView_postal_code)?.text = it.postalCode
                    findViewById<TextView>(R.id.textView_municipality)?.text = it.municipality
                    findViewById<TextView>(R.id.textView_state)?.text = it.state
                    findViewById<TextView>(R.id.textView_country)?.text = it.country
                }
            }, { }))
    }

    private fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.fragment_main, fragment)
            commit()
        }
    }

    private fun closeDetail() {
        activity?.findViewById<ImageView>(R.id.iv_back)?.apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@AddressFragment)?.commit()
                this.visibility = View.GONE
            }
        }
    }

}