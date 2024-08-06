package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityForgottenPasswordStartBinding
import com.google.firebase.auth.FirebaseAuth

class ForgottenPasswordStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgottenPasswordStartBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgottenPasswordStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.btnNext.setOnClickListener {
            val login = binding.etLogin.text.toString()

            if (login.isEmpty()){
                binding.etLogin.error = "Введите email"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(login).addOnCompleteListener{task ->
                if (task.isSuccessful){
                    val intent = Intent(this, CheckingMailActivity::class.java)
                    startActivity(intent)


                        // здесь должна быть функция, которая ожидает смены пароля
                }
                else {
                    Toast.makeText(this, "Ошибка отправки письма для восстановления пароля: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}