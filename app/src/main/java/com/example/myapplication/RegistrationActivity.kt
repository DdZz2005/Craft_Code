package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("users")

        binding.btnRegister.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val fields: Array<Pair<TextView, String>> = arrayOf(
            binding.etFirstName to "Поле не может быть пустым",
            binding.etLastName to "Поле не может быть пустым",
            binding.etMiddleName to "Поле не может быть пустым",
            binding.etEmail to "Поле не может быть пустым",
            binding.etPassword to "Поле не может быть пустым",
            binding.etConfirmPassword to "Поле не может быть пустым"
        )

        var allFieldsFilled = true

        for ((field, errorMsg) in fields) {
            if (field.text.toString().isEmpty()) {
                field.error = errorMsg
                allFieldsFilled = false
            }
        }

        if (!allFieldsFilled) {
            return
        }

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val middleName = binding.etMiddleName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etPasswordAccess.text.toString()

        if (password != confirmPassword) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userId = user?.uid
                if (userId != null) {
                    val userInfo: HashMap<String, String> = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "middleName" to middleName,
                        "email" to email
                    )
                    saveData(userId, userInfo)
                }
            } else {
                Toast.makeText(this, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveData(userId: String, userInfo: HashMap<String, String>) {
        val userReference = databaseReference.child(userId)
        userReference.setValue(userInfo).addOnCompleteListener { dbTask ->
            if (dbTask.isSuccessful) {
                val intent = Intent(this, RegistrationSuccessActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Ошибка сохранения данных: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
