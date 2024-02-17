package com.example.keepnotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.keepnotes.data.local.NoteDataBase
import com.example.keepnotes.data.local.entity.Note
import com.example.keepnotes.databinding.ActivityCreateNoteBinding
import com.example.keepnotes.login.LoginActivity
import com.example.keepnotes.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class CreateNote : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    lateinit var binding: ActivityCreateNoteBinding
    lateinit var viewmodel: NoteViewModel
    lateinit var userviewmodel: UserInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initToolbar()


        binding.RecyclerViewNotes.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        val noteAdapter = NoteAdapter(this, this, this)
        binding.RecyclerViewNotes.adapter = noteAdapter
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val sp=getSharedPreferences("LoginPreference",MODE_PRIVATE)
        val uid= UUID.fromString(sp.getString(getString(R.string.userid),null))
        viewmodel.getAllNotes(uid)
        viewmodel.allNote.observe(this, Observer { list ->
            list?.let {

                noteAdapter.updateList(it)
                val count = noteAdapter.itemCount
                if (count > 0) {
                    binding.RecyclerViewNotes.visibility = View.VISIBLE
                    binding.MaterialCardViewPopup.visibility = View.INVISIBLE
                } else {
                    binding.RecyclerViewNotes.visibility = View.INVISIBLE
                    binding.MaterialCardViewPopup.visibility = View.VISIBLE
                }
            }
        })




        initClicklistener()

    }

    fun initToolbar (){

        binding.toolbar.title = "Add Notes"
        setSupportActionBar(binding.toolbar)

    }


    private fun initClicklistener() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==R.id.logout)
        {
            dialog()
        }


        return super.onOptionsItemSelected(item)
    }

    fun logout(){
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        val sp=
            this.getSharedPreferences("LoginPreference",MODE_PRIVATE)
        sp.edit().clear().commit()

        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.notes)
        intent.putExtra("noteID", note.id)
        intent.putExtra("noteTimestamp", note.timestamp)
        startActivity(intent)

    }

    override fun onDeleteIconClick(note: Note) {
        viewmodel.deleteNote(note)
        Toast.makeText(this, "deleted${note.id}", Toast.LENGTH_LONG).show()
        Log.d("Delete", "onDeleteIconClick: ${note.id} ")
    }

    fun dialog(){
        val alertDialog: AlertDialog = AlertDialog.Builder(this@CreateNote).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("Alert message to be shown")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"
        ) { dialog, which ->
            logout()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"
        ) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
}