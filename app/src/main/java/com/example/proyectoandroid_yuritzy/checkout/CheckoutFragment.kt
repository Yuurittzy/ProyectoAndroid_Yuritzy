package com.example.proyectoandroid_yuritzy.checkout

import android.content.ActivityNotFoundException
import android.content.Intent
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
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class CheckoutFragment : Fragment(), CheckoutInterface {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var checkoutDatabaseController: CheckoutDatabaseController

    private val items = mutableListOf<Product>()
    private var finalPrice = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        checkoutDatabaseController = CheckoutDatabaseController(context?: requireContext())

        getProducts(view)
        checkout(view)

        return view
    }

    private fun getProducts(view: View) {
        compositeDisposable.add(checkoutDatabaseController.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.addAll(it)
                finalPrice = it.sumOf { (it.priceWithDiscount?: 0) * (it.quantity?: 0) } + 50
                view.findViewById<RecyclerView>(R.id.recyclerView_checkout)?.adapter = CheckoutAdapter(it, this)
                view.findViewById<TextView>(R.id.texview_final_price)?.text = getString(R.string.price_final, finalPrice)
                view.findViewById<TextView>(R.id.button_checkout).isEnabled = it.isNotEmpty()
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

    private fun checkout(view: View) {
        view.findViewById<TextView>(R.id.button_checkout).setOnClickListener {
            val products = "Hola, buen d??a, me gustaria comprar: \n"
                .plus(items.fold("\n") { acc, product -> acc.plus("${product.quantity} ${if (product.quantity == 1) "Caja" else "Cajas"} de ${product.name} \n") })
                .plus("\nCon un costo total de $$finalPrice, incluyendo env??o.")

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, products)
            try {
                activity?.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(activity, "Hubo un error enviando tu solicitud de compra.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}