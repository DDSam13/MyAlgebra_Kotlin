package com.example.myapplication.ui.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.activity.viewModels
import com.example.myapplication.databinding.ActivityMenuBinding
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.launch
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.utils.provideUserRepository

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private var login: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login = intent.getStringExtra("login")

        val repository = provideUserRepository(this)
        val viewModel: MenuViewModel by viewModels { MenuViewModelFactory(repository) }

        lifecycleScope.launchWhenStarted {
            viewModel.deleteState.collect { deleted ->
                if (deleted == true) {
                    Toast.makeText(this@MenuActivity, "Аккаунт удалён!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MenuActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    viewModel.resetState()
                } else if (deleted == false) {
                    Toast.makeText(this@MenuActivity, "Ошибка удаления аккаунта", Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
            }
        }

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
            login?.let { viewModel.deleteUser(it) }
        }
    }
}