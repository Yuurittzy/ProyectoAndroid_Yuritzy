package com.example.proyectoandroid_yuritzy.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.AddressDatabaseController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EditAddressFragment: Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var addressDatabaseController: AddressDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_address, container, false)

        addressDatabaseController = AddressDatabaseController(context?: requireContext())

        activity?.findViewById<ImageView>(R.id.iv_back)?.visibility = View.VISIBLE

        getAddress(view)
        closeDetail()

        view.findViewById<TextView>(R.id.textView_edit_address).setOnClickListener { addAddress(view) }

        return view
    }

    private fun getAddress(view: View) {
        compositeDisposable.add(addressDatabaseController.getAddressById(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.apply {
                    findViewById<TextView>(R.id.editText_street)?.text = it.street
                    findViewById<TextView>(R.id.editText_number)?.text = it.number
                    findViewById<TextView>(R.id.editText_district)?.text = it.district
                    findViewById<TextView>(R.id.editText_postal_code)?.text = it.postalCode
                    findViewById<TextView>(R.id.editText_municipality)?.text = it.municipality
                    findViewById<TextView>(R.id.editText_state)?.text = it.state
                    findViewById<TextView>(R.id.editText_country)?.text = it.country
                }
            }, { }))
    }

    private fun addAddress(view: View) {
        val street = view.findViewById<EditText>(R.id.editText_street).text.toString()
        val number = view.findViewById<EditText>(R.id.editText_number).text.toString()
        val district = view.findViewById<EditText>(R.id.editText_district).text.toString()
        val postalCode = view.findViewById<EditText>(R.id.editText_postal_code).text.toString()
        val municipality = view.findViewById<EditText>(R.id.editText_municipality).text.toString()
        val state = view.findViewById<EditText>(R.id.editText_state).text.toString()
        val country = view.findViewById<EditText>(R.id.editText_country).text.toString()

        val address = Address(0, street, number, district, postalCode, municipality, state, country)

        compositeDisposable.add(addressDatabaseController.addAddress(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(context?: requireContext(), "Dirección guardada exitósamente.", Toast.LENGTH_SHORT).show()
                activity?.findViewById<TextView>(R.id.textView_location)?.text = "¿Compras desde $street $number CP $postalCode?"
                       }, {
                Toast.makeText(context?: requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }))
    }


    private fun closeDetail() {
        activity?.findViewById<ImageView>(R.id.iv_back)?.apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@EditAddressFragment)?.commit()
            }
        }
    }

}