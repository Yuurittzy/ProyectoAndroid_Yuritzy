package com.example.proyectoandroid_yuritzy.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private lateinit var localView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        productsDatabaseController = ProductsDatabaseController(context?: requireContext())
        checkoutDatabaseController = CheckoutDatabaseController(context?: requireContext())

        getFavoritesProducts(view)

        localView = view

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
            .subscribe({
                Toast.makeText(context?: requireContext(), "Producto eliminado de favoritos", Toast.LENGTH_SHORT).show()
                getFavoritesProducts(localView)
            }, { }))
    }

    private fun getProductCheckoutById(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.getProductById(product.id.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ updateProduct(it.copy(quantity = (it.quantity?: 0) + 1)) }, { addProduct(product) }))
    }

    private fun updateProduct(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.updateProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Toast.makeText(context?: requireContext(), "Cantidad de producto actualizada", Toast.LENGTH_SHORT).show() }, { }))
    }

    private fun addProduct(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.addProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Toast.makeText(context?: requireContext(), "Producto a??adido al carrito", Toast.LENGTH_SHORT).show() }, { }))
    }

    override fun addProductToCar(product: Product) {
        getProductCheckoutById(product)
    }

}