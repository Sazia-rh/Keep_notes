package com.example.keepnotes.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.data.local.NoteDataBase
import com.example.keepnotes.data.local.entity.UserInfo
import com.example.keepnotes.model.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class RegistrationViewModel(application: Application):AndroidViewModel(application) {
    var registrationrepository:UserInfoRepository
    init{
        var registrationdao=NoteDataBase.getDatabase(application).getUserDao()
        registrationrepository= UserInfoRepository(registrationdao)
    }
    fun addUser(userInfo: UserInfo)=viewModelScope.launch (Dispatchers.IO){
        registrationrepository.insert(userInfo)
    }
}