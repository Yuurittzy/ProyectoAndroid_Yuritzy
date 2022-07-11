package com.example.proyectoandroid_yuritzy.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.CheckoutDatabaseController
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CheckoutFragment : Fragment(), CheckoutInterface {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var checkoutDatabaseController: CheckoutDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        checkoutDatabaseController = CheckoutDatabaseController(context?: requireContext())

        getProducts(view)

        return view
    }

    private fun getProducts(view: View) {
        compositeDisposable.add(checkoutDatabaseController.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.findViewById<RecyclerView>(R.id.recyclerView_checkout)?.adapter = CheckoutAdapter(it, this)
                this.view?.findViewById<TextView>(R.id.texview_final_price)?.text = getString(R.string.price_final, it.sumOf { (it.priceWithDiscount?: 0) * (it.quantity?: 0) })
            }, { }))
    }

    override fun updateProductQuantity(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.updateProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.view?.let { getProducts(it) } }, { }))
    }

    override fun deleteProductQuantity(product: Product) {
        compositeDisposable.add(checkoutDatabaseController.deleteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.view?.let { getProducts(it) } }, { }))
    }

}