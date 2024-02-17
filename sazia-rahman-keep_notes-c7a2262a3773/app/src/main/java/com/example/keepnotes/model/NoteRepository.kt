package com.example.keepnotes.model

import androidx.lifecycle.LiveData
import com.example.keepnotes.data.local.dao.NoteDao
import com.example.keepnotes.data.local.entity.Note
import java.util.UUID

class NoteRepository(private val noteDao: NoteDao) {

    fun insert(note: Note){
        noteDao.insert(note)
    }
    fun delete(note: Note)
    {
        noteDao.delete(note)
    }
    fun update(note: Note)
    {
        noteDao.update(note)
    }

    fun getNotesByUserId(uid: UUID): List<Note> {
        return noteDao.getAllNotes(uid)
    }

}