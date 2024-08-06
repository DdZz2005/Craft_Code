package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
class LoginActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация SharedPreferences
        pref = getSharedPreferences("startMain", MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()

        // Проверка, есть ли сохраненное состояние авторизации
        val isLoggedIn = pref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Setup click listener for registration link
        val registrationTextView: TextView = findViewById(R.id.Registration)
        registrationTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        // Setup click listener for forgot password link
        val forgotPasswordTextView: TextView = findViewById(R.id.tvForgotPassword)
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgottenPasswordStartActivity::class.java)
            startActivity(intent)
        }

        // Setup click listener for sign in button
        binding.btnSignIn.setOnClickListener {
            val email = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Сохранение состояния авторизации
                    val editor = pref.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the login activity
                } else {
                    Toast.makeText(this, "Ошибка входа: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Optionally handle logout to reset shared preferences
    fun logout() {
        auth.signOut()
        val editor = pref.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
