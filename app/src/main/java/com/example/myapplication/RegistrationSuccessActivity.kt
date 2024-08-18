package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.myapplication.databinding.ActivityRegistrationSuccessBinding
class RegistrationSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val button = binding.btnContinue
        button.setOnClickListener {
            // Перейти в основное Activity или другое нужное Activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

}
