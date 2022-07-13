package com.example.proyectoandroid_yuritzy.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.CheckoutDatabaseController
import com.example.proyectoandroid_yuritzy.database.ProductsDatabaseController
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesFragment : Fragment(), FavoritesInterface {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var productsDatabaseController: ProductsDatabaseController
    private lateinit var checkoutDatabaseController: CheckoutDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        productsDatabaseController = ProductsDatabaseController(context?: requireContext())
        checkoutDatabaseController = CheckoutDatabaseController(context?: requireContext())

        getFavoritesProducts(view)

        return view
    }

    private fun getFavoritesProducts(view: View) {
        compositeDisposable.add(productsDatabaseController.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.findViewById<RecyclerView>(R.id.recyclerView_favorites)?.adapter = FavoritesAdapter(it, this) }, { }))
    }

    override fun deleteFavoriteProduct(product: Product) {
        compositeDisposable.add(productsDatabaseController.deleteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.view?.let { getFavoritesProducts(it) } }, { }))
    }

    override fun addProductToCar(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.addProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Toast.makeText(context?: requireContext(), "Producto añadido al carrito", Toast.LENGTH_SHORT).show() }, { }))
    }

}