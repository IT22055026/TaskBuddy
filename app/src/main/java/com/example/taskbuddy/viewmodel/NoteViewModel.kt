package com.example.taskbuddy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskbuddy.model.Note
import com.example.taskbuddy.repository.NoteRepositorary
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepositorary: NoteRepositorary): AndroidViewModel(app) {

    fun addNote(note: Note) =
        viewModelScope.launch {
             noteRepositorary.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepositorary.deletetNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepositorary.updatetNote(note)
        }

    fun getAllNotes() = noteRepositorary.getAllNotes()

    fun searchNote(query: String?) =
        noteRepositorary.searchNote(query ?: "")

}

