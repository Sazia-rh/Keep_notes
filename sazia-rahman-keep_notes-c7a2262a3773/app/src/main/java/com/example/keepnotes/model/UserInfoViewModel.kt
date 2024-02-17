package com.example.keepnotes.model

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.data.local.NoteDataBase
import com.example.keepnotes.data.local.entity.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {

    var userRepository: UserInfoRepository
    val authenticatedUser: MutableLiveData<UserInfo?> = MutableLiveData()
    lateinit var uid: UUID

    init {
        var userdao = NoteDataBase.getDatabase(application).getUserDao()
        userRepository = UserInfoRepository(userdao)


    }

    @SuppressLint("SuspiciousIndentation")

    fun authenticateUser(username: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
             val response = userRepository.authenticateUser(username, password)
                authenticatedUser.postValue(response)

        }

}