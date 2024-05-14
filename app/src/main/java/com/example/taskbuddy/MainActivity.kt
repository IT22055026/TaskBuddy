package com.example.taskbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.taskbuddy.database.NoteDatabase
import com.example.taskbuddy.repository.NoteRepositorary
import com.example.taskbuddy.viewmodel.NoteViewModel
import com.example.taskbuddy.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val noteRepositorary = NoteRepositorary(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepositorary)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}