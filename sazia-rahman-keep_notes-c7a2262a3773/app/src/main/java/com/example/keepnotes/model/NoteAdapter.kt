package com.example.keepnotes.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.data.local.entity.Note
import com.example.keepnotes.databinding.NoteItemBinding
import com.google.android.material.card.MaterialCardView

class NoteAdapter(
    val context: Context,
     val noteClickInterface : NoteClickInterface,
     val noteClickDeleteInterface :NoteClickDeleteInterface
    ):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    lateinit var binding: NoteItemBinding
    private val allNotes=ArrayList<Note>()

   inner class ViewHolder(itemView: MaterialCardView):RecyclerView.ViewHolder(itemView){
        fun bind(binding: NoteItemBinding,item : ArrayList<Note>)
        {
            binding.MaterialTextViewTitle.text = item[absoluteAdapterPosition].title
            binding.MaterialTextViewNote.text = item[absoluteAdapterPosition].notes
            //binding.MaterialTextViewNote.setText("Last Updated: " + item.get(position).timestamp )
            //binding..setText("Last Updated: " + item.get(position).timestamp )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        //allNotes = arrayListOf()
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(binding,allNotes)
        binding.ImageViewDelete.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
            notifyItemRemoved(position)
        }
        binding.MaterialTextViewNote.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }
    fun updateList(newList: List<Note>)
    {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}
interface NoteClickInterface{
    fun onNoteClick(note: Note)
}
interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)


}