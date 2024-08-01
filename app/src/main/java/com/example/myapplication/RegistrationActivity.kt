package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация auth объекта
        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val confirmPassword = binding.etPasswordAccess.text.toString()

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val middleName = binding.etMiddleName.text.toString()
        val role = binding.spinnerRole.toString()

        // Проверка данных
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank() || firstName.isBlank() || lastName.isBlank() || middleName.isBlank() || role.isBlank()) {
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Пароль и подтверждение пароля не совпадают", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                saveUserToDatabase(firstName, lastName, middleName, role)
                Toast.makeText(this, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val exception = task.exception
                Toast.makeText(this, "Ошибка регистрации: ${exception?.message}", Toast.LENGTH_SHORT).show()
                exception?.printStackTrace()
            }
        }
    }

    private fun saveUserToDatabase(firstName: String, lastName: String, middleName: String, role: String) {
        val user = auth.currentUser
        val database = Firebase.database
        val reference = database.getReference("users")

        val userInfo = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "middleName" to middleName,
            "role" to role,
            "email" to user?.email
        )

        user?.let {
            reference.child(it.uid).setValue(userInfo)
                .addOnSuccessListener {
                    Toast.makeText(this, "Данные пользователя сохранены", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Ошибка сохранения данных: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    public override fun onStart() {
        super.onStart()
    }
}
