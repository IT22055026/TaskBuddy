package com.example.taskbuddy.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskbuddy.repository.NoteRepositorary

class NoteViewModelFactory(val app: Application, private val noteRepositorary: NoteRepositorary): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(app, noteRepositorary) as T
    }
}