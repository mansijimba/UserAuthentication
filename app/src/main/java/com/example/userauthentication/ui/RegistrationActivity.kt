package com.example.userauthentication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.userauthentication.R
import com.example.userauthentication.Utils.imageUtils
import com.example.userauthentication.databinding.ActivityRegistrationBinding
import com.squareup.picasso.Picasso

class RegistrationActivity : AppCompatActivity() {
    lateinit var registrationBinding: ActivityRegistrationBinding

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    var imageUri : Uri? = null

    lateinit var imageUtils: imageUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(registrationBinding.root)

        imageUtils = imageUtils(this)
        imageUtils.registerActivity { url ->
            url.let {
                imageUri = url
                Picasso.get().load(url).into(registrationBinding.imagebrowse)
            }
        }

//        registrationBinding.imagebrowse.setOnClickListener{
//            imageUtils.launchGallery(this)
//        }
//        registrationBinding.save.setOnClickListener {
//            if (imageUri != null){
//                uploadImage()
//            }else{
//                Toast.makeText(applicationContext,"Please upload image first",Toast.LENGTH_LONG)
//                    .show()
//            }
//        }

        registrationBinding.buttonregister2.setOnClickListener {
             var name : String = registrationBinding.editname.text.toString()
             var email : String = registrationBinding.editemail.text.toString()
             var password : String = registrationBinding.editPassword.text.toString()

            Toast.makeText(applicationContext, "Registration Success", Toast.LENGTH_LONG).show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}