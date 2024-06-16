package com.example.userauthentication.Repository

import android.net.Uri
import com.example.userauthentication.Model.UserModel

interface UserRepository {
    fun uploadImage(imageName: String, imageUrl: Uri, callback: (Boolean, String?) -> Unit)

    fun addUser(userModel: UserModel, callback : (Boolean,String?) -> Unit)

    fun getAllUser(callback: (List<UserModel>?,Boolean, String?) -> Unit)

    fun UpdateUser(id:String,data:MutableMap<String,Any>?,callback:(Boolean,String?) -> Unit)

    fun deleteData(id:String,callback:(Boolean,String?) -> Unit)


    fun deleteImage(imageName:String,callback:(Boolean,String?) -> Unit)
}