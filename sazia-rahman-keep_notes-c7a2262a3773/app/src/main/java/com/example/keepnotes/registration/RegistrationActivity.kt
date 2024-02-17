package com.example.keepnotes.registration

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
import com.example.keepnotes.data.local.entity.UserInfo
import com.example.keepnotes.databinding.ActivityRegistrationBinding
import com.example.keepnotes.login.LoginActivity
import kotlinx.coroutines.Runnable
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    private val registrationViewModel by lazy {
        ViewModelProvider(this@RegistrationActivity)[RegistrationViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.CircularProgressIndicator.visibility = View.GONE
        binding.TextViewWait.visibility = View.GONE

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initOnclickListener()


    }

    private fun initOnclickListener() {

        binding.MaterialButtonSignUp.setOnClickListener {
            val userName = binding.TextInputEditTextUserName.text.toString()
            val password = binding.TextInputEditPass.text.toString()
            val name = binding.TextInputEditName.text.toString()
            val mail = binding.TextInputEditEmail.text.toString()
            var list = listOf(userName, password, name, mail)
            Log.d("list", "initOnclickListener: ${list}")
            var function:GenericFunction =  GenericFunction()
            val checkAllFields = function.CheckAllFields(list)

            if (checkAllFields) {
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    binding.TextInputLayoutEmail.error = getString(R.string.error_massage)
                } else {


                    binding.CircularProgressIndicator.visibility = View.VISIBLE
                    binding.MaterialButtonSignUp.isEnabled = false
                    binding.TextViewWait.visibility = View.VISIBLE
                    var uuid = UUID.randomUUID()

                    try {

                        registrationViewModel.addUser(
                            UserInfo(
                                name,
                                password,
                                userName,
                                mail,
                                uuid
                            )
                        )
                        val sharedPref = this.getSharedPreferences("LoginPreference", MODE_PRIVATE)
                            ?: return@setOnClickListener
                        with(sharedPref.edit()) {
                            putString(getString(R.string.userid), uuid.toString())

                            apply()
                        }
                    } catch (e:Exception) {

                        Toast.makeText(this, "Need Unique UserName", Toast.LENGTH_LONG).show()
                        Log.d("uniqueerror", "initOnclickListener: ${e.message}")

                    }

                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            binding.CircularProgressIndicator.visibility = View.GONE
                            binding.TextViewWait.visibility = View.GONE
                            val intent = Intent(this@RegistrationActivity, CreateNote::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }, 5000)

                }

            } else {
                Toast.makeText(this, "All Fields Are Required", Toast.LENGTH_LONG).show()
            }

        }
        binding.textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



}
