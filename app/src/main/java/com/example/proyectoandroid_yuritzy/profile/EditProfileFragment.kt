package com.example.proyectoandroid_yuritzy.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.ProfileDatabaseController
import com.example.proyectoandroid_yuritzy.profile_picture.ProfilePictureActivity
import com.example.proyectoandroid_yuritzy.profile_picture.ProfilePictureInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EditProfileFragment: Fragment(), ProfilePictureInterface {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var profileDatabaseController: ProfileDatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        profileDatabaseController = ProfileDatabaseController(context?: requireContext())

        activity?.findViewById<ImageView>(R.id.iv_back)?.visibility = View.VISIBLE

        view.findViewById<TextView>(R.id.textView_take_picture).setOnClickListener { startActivity(Intent(activity, ProfilePictureActivity::class.java)) }

        view.findViewById<TextView>(R.id.textView_edit_profile).setOnClickListener { editProfile(view) }

        closeDetail()

        return view
    }

    private fun editProfile(view: View) {
        val name = view.findViewById<EditText>(R.id.editText_edit_name).text.toString()

        val profile = Profile(0, "", name)

        compositeDisposable.add(profileDatabaseController.addProfile(profile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Toast.makeText(context?: requireContext(), "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show() }, {}))
    }

    private fun closeDetail() {
        activity?.findViewById<ImageView>(R.id.iv_back)?.apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@EditProfileFragment)?.commit()
            }
        }
    }

    override fun addProfilePicture(picture: Uri) {
        Log.d("foto", picture.toString())
    }

}