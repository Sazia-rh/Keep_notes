package com.example.keepnotes.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.data.local.NoteDataBase
import com.example.keepnotes.data.local.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class NoteViewModel(application: Application):AndroidViewModel(application) {
    val allNote:MutableLiveData<List<Note>> = MutableLiveData()
    val repository:NoteRepository

    init{
        val dao= NoteDataBase.getDatabase(application).getNotesDao()
        repository= NoteRepository(dao)

    }

    fun deleteNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun getAllNotes(uid:UUID)=viewModelScope.launch(Dispatchers.IO){
        val response = repository.getNotesByUserId(uid)
        allNote.postValue(response)

    }
}