package com.example.userauthentication.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.userauthentication.R
import com.example.userauthentication.Repository.UserRepositoryImpl
import com.example.userauthentication.Utils.ImageUtils
import com.example.userauthentication.ViewModel.UserViewModel
import com.example.userauthentication.databinding.ActivityUpdateUserBinding
import com.example.userauthentication.databinding.EditUserActivityBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class UpdateUserActivity : AppCompatActivity() {
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref = firebaseDatabase.reference.child("user")

    lateinit var updateUserBinding: ActivityUpdateUserBinding
    var id = ""
    var imageName= ""

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    var imageUri : Uri? = null

    lateinit var  userViewModel: UserViewModel


    lateinit var editUserActivityBinding: EditUserActivityBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }
    lateinit var imageUtils: ImageUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateUserBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(updateUserBinding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        imageUtils = ImageUtils(this)
        imageUtils.registerActivity {
            imageUri = it
            Picasso.get().load(it).into(updateUserBinding.imageupdate)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}