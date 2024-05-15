package com.example.taskbuddy.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbuddy.databinding.NoteLayoutBinding
import com.example.taskbuddy.fragment.Home_FragmentDirections
import com.example.taskbuddy.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NoteLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val dirfferCallBack = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteDesc == newItem.noteDesc &&
                    oldItem.noteTitele == newItem.noteTitele &&
                    oldItem.noteDate == newItem.noteDate
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, dirfferCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder(
           NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding. noteTitle.text = currentNote.noteTitele
        holder.itemBinding. noteDesc.text = currentNote.noteDesc
        holder.itemBinding.noteDate.text = currentNote.noteDate

        holder.itemView.setOnClickListener{
            val direction = Home_FragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }


    }
}