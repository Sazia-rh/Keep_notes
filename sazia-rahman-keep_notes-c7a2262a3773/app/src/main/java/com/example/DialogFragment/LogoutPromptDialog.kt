package com.example.DialogFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.keepnotes.R
import com.example.keepnotes.model.NoteViewModel
import com.example.keepnotes.model.UserInfoViewModel
import android.app.Dialog as Dialog

class LogoutPromptDialog :DialogFragment(){
    var delete =false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.logoutprompt))
                .setPositiveButton(getString(R.string.yes)
                ) { dialog, id ->
                    delete = true

                }
                .setNegativeButton(getString(R.string.no),
                DialogInterface.OnClickListener { dialog, id ->
                    dismiss()

                })
            builder.create()

        }?: throw IllegalStateException("Error")
    }
}