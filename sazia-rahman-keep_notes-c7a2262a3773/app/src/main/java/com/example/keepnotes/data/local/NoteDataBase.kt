package com.example.keepnotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keepnotes.data.local.entity.Note
import com.example.keepnotes.data.local.dao.NoteDao
import com.example.keepnotes.data.local.entity.UserInfo
import com.example.keepnotes.data.local.dao.UserInfoDao


@Database(entities = [Note::class, UserInfo::class], version = 9, exportSchema = false)
abstract class NoteDataBase :RoomDatabase() {
    abstract fun getNotesDao(): NoteDao
    abstract fun getUserDao(): UserInfoDao
    companion object{
        @Volatile
        private var INSTANCE: NoteDataBase? =null

        fun getDatabase(context:Context) : NoteDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration().build()
                 INSTANCE =instance
                instance
            }
        }

    }

}