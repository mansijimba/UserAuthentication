package com.example.userauthentication.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userauthentication.Adapter.UserAdapter
import com.example.userauthentication.Model.UserModel
import com.example.userauthentication.R
import com.example.userauthentication.Repository.UserRepositoryImpl
import com.example.userauthentication.ViewModel.UserViewModel
import com.example.userauthentication.databinding.ActivityLoginBinding
import java.util.ArrayList

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var userAdapter: UserAdapter
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)
        userViewModel.fetchProduct()

        userAdapter = UserAdapter(this@LoginActivity, ArrayList())
        loginBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LoginActivity)
            adapter = userAdapter
        }
        userViewModel.loadingState.observe(this) { loading ->
            if (loading) {
                loginBinding.progressBar2.visibility = View.VISIBLE
            } else {
                loginBinding.progressBar2.visibility = View.GONE
            }
        }
        userViewModel.userList.observe(this) { user ->
            user?.let {
                userAdapter.updateData(it)
            }
        }


    }
}