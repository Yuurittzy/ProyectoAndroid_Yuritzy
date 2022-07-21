package com.example.proyectoandroid_yuritzy.profile_picture

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectoandroid_yuritzy.R
import com.example.proyectoandroid_yuritzy.database.ProfileDatabaseController
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ProfilePictureActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var profileDatabaseController: ProfileDatabaseController

    private lateinit var outPutDirectory: File
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        profileDatabaseController = ProfileDatabaseController(this)

        outPutDirectory = getOutputDirectory()
        requestPermissions()

        findViewById<Button>(R.id.btnTomarFoto).setOnClickListener {
            takePhoto()
        }
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                        mPreview ->
                    mPreview.setSurfaceProvider (
                        findViewById<PreviewView>(R.id.viewFinder).surfaceProvider
                    )
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,cameraSelector,preview,imageCapture
                )
            }catch (e: Exception){
                Log.d(Constants.TAG,"Error al inicializar camara")
            }

        }, ContextCompat.getMainExecutor(this))
    }
    private fun takePhoto(){
        val imageCapture = imageCapture?: return

        val  photoFile = File(
            outPutDirectory,
            SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())+".jpg")

        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()

        imageCapture.takePicture(outputOption, ContextCompat.getMainExecutor(this),
            object: ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    val msg = "Foto guardada"
                    Log.i(Constants.TAG,"Foto: $msg,$savedUri")
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG,"Error: ${exception.message.toString()}")
                }

            }
        )

    }

    private fun getOutputDirectory():File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
                mFile -> File(mFile,"ejemplocamara").apply {
            mkdirs()
        }
        }
        return if(mediaDir!=null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun requestPermissions(){
        if (allPermissionGranted()){
            //Tenemos permiso a la camara
            startCamera()
        }else{
            //Si no están habilitados los permisos, se piden
            ActivityCompat.requestPermissions(this,Constants.REQUIRED_PERMISSIONS,Constants.REQUEST_CODE_PERMISSIONS)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==Constants.REQUEST_CODE_PERMISSIONS){
            if (allPermissionGranted()){
                startCamera()
            }else{
                finish()
            }
        }
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext,it) == PackageManager.PERMISSION_GRANTED
        }

}