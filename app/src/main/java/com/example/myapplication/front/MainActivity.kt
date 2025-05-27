package com.example.myapplication.front

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import myapplication.database.UsersDateBase
import myapplication.database.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val db = UsersDateBase.getDatabase(this@MainActivity)
                val user = db.userDao().getUserByLogin(login)
                if (user != null && user.password == password) {
                    Toast.makeText(this@MainActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, MenuActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val db = UsersDateBase.getDatabase(this@MainActivity)
                val existingUser = db.userDao().getUserByLogin(login)
                if (existingUser == null) {
                    db.userDao().insertUser(User(login, password))
                    Toast.makeText(this@MainActivity, "Пользователь зарегистрирован!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, MenuActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}