package com.example.taskbuddy.repository

import androidx.room.Query
import com.example.taskbuddy.database.NoteDatabase
import com.example.taskbuddy.model.Note

class NoteRepositorary(private val db: NoteDatabase) {
    suspend fun insertNote(note: Note) = db.getNoteDeo().insertNote(note)
    suspend fun deletetNote(note: Note) = db.getNoteDeo().deleteNote(note)
    suspend fun updatetNote(note: Note) = db.getNoteDeo().updateNote(note)

    fun getAllNotes() = db.getNoteDeo().getAllNotes()
    fun searchNote(query: String) = db.getNoteDeo().serachNote(query)
}