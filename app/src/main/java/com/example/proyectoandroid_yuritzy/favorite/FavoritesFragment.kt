package com.example.proyectoandroid_yuritzy.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.DatabaseController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var databaseController: DatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        databaseController = DatabaseController(context?: requireContext())

        setUpRecyclerView(view)

        return view
    }

    private fun setUpRecyclerView(view: View) {
        compositeDisposable.add(databaseController.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.findViewById<RecyclerView>(R.id.recyclerView_favorites)?.adapter = FavoritesAdapter(it) }, { }))
    }

}