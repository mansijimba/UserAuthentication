package com.example.userauthentication.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.userauthentication.Model.UserModel
import com.example.userauthentication.R
import com.example.userauthentication.Repository.UserRepositoryImpl
import com.example.userauthentication.Utils.ImageUtils
import com.example.userauthentication.Utils.LoadingUtils
import com.example.userauthentication.ViewModel.UserViewModel
import com.example.userauthentication.databinding.ActivityRegistrationBinding
import com.example.userauthentication.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.UUID

class RegistrationActivity : AppCompatActivity() {
    lateinit var loadingUtils: LoadingUtils

    lateinit var registrationBinding: ActivityRegistrationBinding

    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref = firebaseDatabase.reference.child("user")


    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null

    lateinit var imageUtils: ImageUtils
    lateinit var userViewmodel: UserViewModel
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(registrationBinding.root)

        loadingUtils = LoadingUtils(this)

        val repo = UserRepositoryImpl()
        userViewmodel = UserViewModel(repo)
        imageUtils = ImageUtils(this)
        imageUtils.registerActivity { url ->
            url.let {
                imageUri = url
                Picasso.get().load(url).into(registrationBinding.imagebrowse)
            }
        }

        registrationBinding.imagebrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        registrationBinding.buttonregister2.setOnClickListener {
            if (imageUri != null) {
                uploadImage()
            } else {
                Toast.makeText(applicationContext, "Please upload image first", Toast.LENGTH_LONG)
                    .show()
            }
        }

        registrationBinding.buttonregister2.setOnClickListener {
            uploadImage()

//            Toast.makeText(applicationContext, "Registration Success", Toast.LENGTH_LONG).show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun uploadImage() {
        loadingUtils.showLoading()

        val imageName = UUID.randomUUID().toString()
        imageUri?.let {
            userViewmodel.uploadImage(imageName, it) { success, imageUrl ->
                if (success) {
                    addUser(imageUrl.toString(), imageName.toString())
                }
            }

        }

    }

    fun addUser(url: String, imageName: String) {
        Log.d("errroeroeoreroe","i am called herer")
        var name: String = registrationBinding.editname.text.toString()
        var email: String = registrationBinding.editemail.text.toString()
        var number: Int = registrationBinding.editNumber.text.toString().toInt()
        var password: String = registrationBinding.editPassword.text.toString()
        var data = UserModel("", name, email, number, password, url, imageName)

        userViewmodel.addUser(data) { success, message ->
            if (success) {
               loadingUtils.dismiss()
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

            }
        }

    }
}