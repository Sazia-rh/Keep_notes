package com.example.keepnotes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keepnotes.data.local.dao.UserInfoDao
import com.example.keepnotes.data.local.entity.UserInfo
import java.util.*

class UserInfoRepository(private val userInfoDao: UserInfoDao) {



    fun insert(userInfo: UserInfo){
        userInfoDao.insert(userInfo)
    }
    fun deleteall() {
        userInfoDao.deleteall()
    }
    fun update(userInfo: UserInfo) {
        userInfoDao.update(userInfo)
    }
    fun authenticateUser(username: String,password:String): UserInfo?{
        return userInfoDao.authenticate(username,password)
    }

}