package com.example.keepnotes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.keepnotes.data.local.entity.UserInfo
import java.util.*

@Dao
interface UserInfoDao {

    @Insert
    fun insert(userInfo: UserInfo)
    @Delete
    fun delete(userInfo: UserInfo)
    @Query("DELETE FROM UserInfoTable")
    fun deleteall():Void

    @Update
    fun update(userInfo: UserInfo)



    @Query("SELECT * FROM UserInfoTable WHERE UserName = :username AND password = :password")
    fun authenticate(username: String,password:String): UserInfo?


}