package com.example.keepnotes
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.keepnotes.data.local.NoteDataBase
import com.example.keepnotes.databinding.ActivityMainBinding
import com.example.keepnotes.login.LoginActivity
import com.example.keepnotes.model.UserInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserInfoViewModel
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.MaterialButtonStart.setOnClickListener {
            val sp=getSharedPreferences("LoginPreference",MODE_PRIVATE)
            val uid= sp.getString(getString(R.string.userid),null)

            Log.d("login", "onCreate: $uid")
            if(uid!=null)
            {
                val intent=Intent(this,CreateNote::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


}