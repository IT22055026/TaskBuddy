package com.example.taskbuddy.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskbuddy.MainActivity
import com.example.taskbuddy.R
import com.example.taskbuddy.databinding.FragmentEditNoteBinding
import com.example.taskbuddy.viewmodel.NoteViewModel
import java.util.Currency
import com.example.taskbuddy.model.Note
import java.util.Calendar


class Edit_Note_Fragment : Fragment(R.layout.fragment_edit__note_), MenuProvider{

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

   private val args:Edit_Note_FragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitele)
        binding.editNoteDesc.setText(currentNote.noteDesc)
        binding.editnoteDate.setText(currentNote.noteDate)

        // Set up the date picker dialog
        binding.editnoteDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date and set it to the TextView
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.editnoteDate.text = Editable.Factory.getInstance().newEditable(selectedDate)
            }, year, month, day)
            datePickerDialog.show()
        }

       binding.editNoteFab.setOnClickListener {
           val noteTitle = binding.editNoteTitle.text.toString().trim()
           val noteDesc = binding.editNoteDesc.text.toString().trim()
           val noteDate = binding.editnoteDate.text.toString().trim()

           if(noteTitle.isNotEmpty()){
               val note = Note(currentNote.id, noteTitle, noteDesc, noteDate)
               notesViewModel.updateNote(note)
               view.findNavController().popBackStack(R.id.home_Fragment, false)
           }else {
               Toast.makeText(context, "Please Enter Note Title", Toast.LENGTH_SHORT).show()

           }
       }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want To delete this Note?")
            setPositiveButton("Delete"){_,_->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT)
                view?.findNavController()?.popBackStack(R.id.home_Fragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
      menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}