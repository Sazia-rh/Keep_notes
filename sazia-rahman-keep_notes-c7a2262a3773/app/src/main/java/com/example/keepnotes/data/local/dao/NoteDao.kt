package com.example.keepnotes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.keepnotes.data.local.entity.Note
import java.util.UUID


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)


    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query ("SELECT * FROM notesTable WHERE uid = :uid ORDER BY id ASC")
    fun getAllNotes(uid:UUID): List<Note>
}

