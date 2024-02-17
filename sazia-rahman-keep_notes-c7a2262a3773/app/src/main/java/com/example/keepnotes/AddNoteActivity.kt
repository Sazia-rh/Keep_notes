package com.example.keepnotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.keepnotes.Function.GenericFunction
import com.example.keepnotes.data.local.entity.Note
import com.example.keepnotes.databinding.ActivityAddNoteBinding
import com.example.keepnotes.model.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var viewModel: NoteViewModel
    var noteID: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            val noteTimestamp = intent.getLongExtra("noteTimestamp", 0)
            val sdf = SimpleDateFormat("dd MM YYYY - HH:mm")
            Log.d("timestamp", "onCreate: ${noteTimestamp},${noteTitle}")
            val date = Date(noteTimestamp)
            var timestamp = sdf.format(date)
            noteID = intent.getIntExtra("noteID", -1)

            binding.MaterialButtonSaveNotes.text = getString(R.string.update)
            binding.TextInputEditTextTitle.setText(noteTitle)
            binding.TextInputEditTextNotes.setText(noteDescription)
            binding.MotionLabelTimestamp.setText("Last Updated: ${timestamp} ")
        } else {
            binding.MotionLabelTimestamp.visibility = View.INVISIBLE
            binding.MaterialButtonSaveNotes.text = getString(R.string.savenote)
        }

        binding.MaterialButtonSaveNotes.setOnClickListener {
            val noteTitle = binding.TextInputEditTextTitle.text.toString()
            val noteDescription = binding.TextInputEditTextNotes.text.toString()
            var list = listOf(noteTitle, noteDescription)
            var function: GenericFunction = GenericFunction()
            val checkAllFields = function.CheckAllFields(list)

            if (noteType.equals("Edit")) {
                if (checkAllFields) {
                    var currentDate = System.currentTimeMillis();
                    val sp = getSharedPreferences("LoginPreference", MODE_PRIVATE)
                    val uid = sp.getString(getString(R.string.userid), null)
                    val uuid = UUID.fromString(uid)

                    val updateNote = Note(noteTitle, noteDescription, currentDate, uuid)
                    updateNote.id = noteID ?: 0
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, CreateNote::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "All Fields Are required", Toast.LENGTH_LONG).show()
                }


            } else {
                binding.MotionLabelTimestamp.visibility = View.INVISIBLE


                if (checkAllFields) {
                    val currentDate = System.currentTimeMillis();


                    val sp = getSharedPreferences("LoginPreference", MODE_PRIVATE)
                    val uid = sp.getString(getString(R.string.userid), null)
                    val uuid = UUID.fromString(uid)
                    Log.d("uid", "onCreate: $uuid")
                    viewModel.addNote(Note(noteTitle, noteDescription, currentDate, uuid))
                    Toast.makeText(this, "Note Added..", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, CreateNote::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "All Fields Are required", Toast.LENGTH_LONG).show()
                }

            }


        }

    }
}