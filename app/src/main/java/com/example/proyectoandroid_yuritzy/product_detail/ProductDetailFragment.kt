package com.example.proyectoandroid_yuritzy.product_detail

import android.graphics.PorterDuff.Mode.MULTIPLY
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.CheckoutDatabaseController
import com.example.proyectoandroid_yuritzy.database.ProductsDatabaseController
import com.example.proyectoandroid_yuritzy.main.Product
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var productsDatabaseController: ProductsDatabaseController
    private lateinit var checkoutDatabaseController: CheckoutDatabaseController

    private var product = Product(0)

    private var isProductLiked = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)

        activity?.findViewById<ImageView>(R.id.iv_back)?.visibility = View.VISIBLE

        productsDatabaseController = ProductsDatabaseController(context?: requireContext())
        checkoutDatabaseController = CheckoutDatabaseController(context?: requireContext())

        arguments?.getParcelable<Product>("PRODUCT")?.let { setProductDetail(it, view) }

        onClickLikeButton(view)
        onClickAddToCar(view)
        closeDetail()

        return view
    }

    private fun setProductDetail(product: Product, view: View) {
        this.product = product

        getFavoriteProductById(product, view)

        view.apply {
            findViewById<TextView>(R.id.themathic).text = getString(R.string.thematic, product.name)
            findViewById<RatingBar>(R.id.ratingBar).rating = product.rating?.toFloat()?: 0F
            findViewById<ImageView>(R.id.iv_preview).setImageDrawable(product.previewImage?.let { ContextCompat.getDrawable(view.context, it) })
            findViewById<TextView>(R.id.tv_initial_price).text = getString(R.string.price_initial, product.price)
            findViewById<TextView>(R.id.tv_final_price).text = getString(R.string.price_final, product.priceWithDiscount)
            findViewById<TextView>(R.id.tv_discount).text = "% ${getString(R.string.discount, product.discountPercent)}"
        }
    }

    private fun onClickLikeButton(view: View) {
        view.findViewById<ImageView>(R.id.iv_favourite)?.apply {
            setOnClickListener {
            setColorFilter(ContextCompat.getColor(context?: requireContext(), if (!isProductLiked) R.color.orange else R.color.gray), MULTIPLY)
            isProductLiked = !isProductLiked

                if (isProductLiked) {
                    compositeDisposable.add(productsDatabaseController.addProduct(product)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ Toast.makeText(context?: requireContext(), "Producto añadido a favoritos", Toast.LENGTH_SHORT).show() }, { }))
                } else {
                    compositeDisposable.add(productsDatabaseController.deleteProduct(product)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ Toast.makeText(context?: requireContext(), "Producto eliminado de favoritos", Toast.LENGTH_SHORT).show() }, { }))
                }
            }
        }
    }

    private fun getFavoriteProductById(product: Product, view: View) {
        compositeDisposable.add(productsDatabaseController.getProductById(product.id.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isProductLiked = true
                view.findViewById<ImageView>(R.id.iv_favourite).setColorFilter(ContextCompat.getColor(context?: requireContext(), R.color.orange), MULTIPLY)
            }, {  }))
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
            .subscribe({ Toast.makeText(context?: requireContext(), "Producto añadido al carrito", Toast.LENGTH_SHORT).show() }, { }))
    }

    private fun onClickAddToCar(view: View) {
        view.findViewById<Button>(R.id.button3)?.setOnClickListener {
            getProductCheckoutById(product)
        }
    }

    private fun closeDetail() {
        activity?.findViewById<ImageView>(R.id.iv_back)?.apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@ProductDetailFragment)?.commit()
                this.visibility = View.GONE
            }
        }
    }

}