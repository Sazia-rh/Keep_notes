package com.example.keepnotes.data.local.entity

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "UserInfoTable",indices = [Index(value = ["UserName"],
    unique = true)]
)
class UserInfo(
    var Name:String,
    var Password:String,
    var UserName:String,
    var email:String,
    @PrimaryKey val ID:UUID


) {
    override fun toString(): String {
        return "UserInfo(Name='$Name', Password='$Password', UserName='$UserName', email='$email', ID=$ID)"
    }
}
