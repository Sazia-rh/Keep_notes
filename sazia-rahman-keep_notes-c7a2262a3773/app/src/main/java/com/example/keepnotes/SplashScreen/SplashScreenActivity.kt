package com.example.keepnotes.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.keepnotes.CreateNote
import com.example.keepnotes.R
import com.example.keepnotes.databinding.ActivityLoginBinding
import com.example.keepnotes.databinding.ActivitySplashScreenBinding
import com.example.keepnotes.registration.RegistrationActivity

class SplashScreenActivity : AppCompatActivity() {
   lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        var anim= AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                    val sp=getSharedPreferences("LoginPreference",MODE_PRIVATE)
                    val uid= sp.getString(getString(R.string.userid),null)
                Log.d("splash", "onAnimationEnd: $uid")
                    binding.CircularProgressIndicatorSplash.visibility=View.VISIBLE
                    if(uid!=null)
                    {
                        val intent= Intent(this@SplashScreenActivity, CreateNote::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        val intent = Intent(this@SplashScreenActivity, RegistrationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding.SplashScreenImage.startAnimation(anim)

    }
}