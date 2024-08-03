package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        val registrationTextView: TextView = findViewById(R.id.Registration)
        registrationTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val forgotPasswordTextView: TextView = findViewById(R.id.tvForgotPassword)
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgottenPasswordStartActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            signInUser()
        }
    }




    private fun signInUser() {
        val login = binding.etLogin.text.toString()
        val pass = binding.etPassword.text.toString()


        // Проверка данных
        if (login.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show()
            return
        }


        auth.signInWithEmailAndPassword(login, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Успешная авторизация!", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val exception = task.exception
                Toast.makeText(
                    this,
                    "Ошибка авторизации: ${exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
                exception?.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


}