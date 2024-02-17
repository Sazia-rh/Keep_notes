package com.example.keepnotes.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.keepnotes.CreateNote
import com.example.keepnotes.Function.GenericFunction
import com.example.keepnotes.R
import com.example.keepnotes.R.string.userid
import com.example.keepnotes.data.local.entity.UserInfo
import com.example.keepnotes.databinding.ActivityLoginBinding
import com.example.keepnotes.model.UserInfoViewModel
import com.example.keepnotes.registration.RegistrationActivity
import java.util.*


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val userViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    companion object {
        private val TAG = LoginActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.CircularProgressIndicatorsignin.visibility=View.GONE
        binding.TextViewWaitlogin.visibility=View.GONE
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding.MaterialTextViewRegister.setOnClickListener {
            val intent=Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.MaterialButtonSignIn.setOnClickListener {
            val userName = binding.TextInputEditTextUserName.text.toString()
            val password = binding.TextInputEditPass.text.toString()
            var list = listOf(userName, password)
            var function: GenericFunction =  GenericFunction()
            val checkAllFields = function.CheckAllFields(list)

            if (checkAllFields) {

                userViewModel.authenticateUser(userName, password)
                observeData()

            } else {
                Toast.makeText(this, "All Fields Are Required", Toast.LENGTH_LONG).show()

            }
        }


    }

    fun observeData (){
        userViewModel.authenticatedUser.observe(this) {
            if(it!=null)
            {

             val sharedPref = this.getSharedPreferences( "LoginPreference",MODE_PRIVATE) ?: return@observe
             with(sharedPref.edit()) {
                 putString(getString(userid), it.ID.toString())
                 apply()
             }
                binding.CircularProgressIndicatorsignin.visibility=View.VISIBLE
                binding.TextViewWaitlogin.visibility=View.VISIBLE

                binding.MaterialButtonSignIn.isEnabled=false
                 Handler(Looper.getMainLooper()).postDelayed(object :Runnable{
                     override fun run() {
                         binding.CircularProgressIndicatorsignin.visibility=View.GONE
                         binding.TextViewWaitlogin.visibility=View.GONE
                         val intent=Intent(this@LoginActivity,CreateNote::class.java)
                         startActivity(intent)
                         finish()
                     }
                 },3000)

            }
            else{
                Toast.makeText(this,"AuthnticationFailed",Toast.LENGTH_LONG).show()
            }
            Log.d(TAG, "observeData: ${it.toString()}")
        }
    }


}