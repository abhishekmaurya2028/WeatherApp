package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class splacecreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splacecreen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(this,MainActivity::class.java);
            startActivity(intent);
            finish()
        },3000)
    }
}