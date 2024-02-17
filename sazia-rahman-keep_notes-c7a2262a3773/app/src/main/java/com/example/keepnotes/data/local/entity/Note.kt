package com.example.keepnotes.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notesTable",
        foreignKeys = [ForeignKey(entity = UserInfo::class,
    parentColumns = arrayOf("ID"),
    childColumns = arrayOf("uid"),
    onDelete = ForeignKey.CASCADE)],
    indices = [Index("uid")]
)

class Note(
    var title: String,
    var notes:String,
    val timestamp: Long,
    val uid:UUID
){
    @PrimaryKey(autoGenerate = true)
    var id =0
}
