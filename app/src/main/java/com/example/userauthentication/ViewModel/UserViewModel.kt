package com.example.userauthentication.ViewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userauthentication.Model.UserModel
import com.example.userauthentication.Repository.UserRepository

class UserViewModel(val repository: UserRepository):ViewModel(){
    fun deleteData(id:String,callback:(Boolean,String?) -> Unit){
        repository.deleteData(id,callback)
    }
    fun deleteImage(imageName:String,callback:(Boolean,String?) -> Unit){
        repository.deleteImage(imageName,callback)
    }


    fun updateProduct(id:String,data: MutableMap<String,Any>?,callback:(Boolean,String?) -> Unit){
        repository.UpdateUser(id,data,callback)
    }

    fun addProduct(userModel: UserModel, callback: (Boolean, String?) -> Unit) {
        repository.addUser(userModel, callback)
    }

    fun uploadImage(imageName: String, imageUrl: Uri, callback: (Boolean, String?) -> Unit) {
        repository.uploadImage(imageName, imageUrl){success,imageUrl ->
            callback(success, imageUrl)
        }
    }

    private var _userList = MutableLiveData<List<UserModel>?>()
    var userList = MutableLiveData<List<UserModel>?>()
        get() = _userList

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun fetchProduct() {
        _loadingState.value = true
        repository.getAllUser() { productList, success, message ->
            if (productList != null) {
                _loadingState.value= false
                _userList.value = productList
            }
        }
    }

}
