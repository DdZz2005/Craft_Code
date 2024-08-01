package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityForgottenPasswordStartBinding

class ForgottenPasswordStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgottenPasswordStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgottenPasswordStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

}