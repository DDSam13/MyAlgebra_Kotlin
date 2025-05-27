package com.example.myapplication.front

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMenuBinding
import myapplication.database.UsersDateBase
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private var login: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login = intent.getStringExtra("login")

        binding.btnLine.setOnClickListener {
            startActivity(Intent(this, ActivityLine::class.java))
        }
        binding.btnSquare.setOnClickListener {
            startActivity(Intent(this, ActivitySquare::class.java))
        }
        binding.btnNok.setOnClickListener {
            startActivity(Intent(this, ActivityNok::class.java))
        }
        binding.btnNod.setOnClickListener {
            startActivity(Intent(this, ActivityNod::class.java))
        }
        binding.btnSystem.setOnClickListener {
            startActivity(Intent(this, ActivitySystem::class.java))
        }

        binding.btnDeleteAccount.setOnClickListener {
            val userLogin = login
            lifecycleScope.launch {
                val db = UsersDateBase.getDatabase(this@MenuActivity)
                db.userDao().deleteUserByLogin(userLogin)
                Toast.makeText(this@MenuActivity, "Аккаунт удалён!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
}