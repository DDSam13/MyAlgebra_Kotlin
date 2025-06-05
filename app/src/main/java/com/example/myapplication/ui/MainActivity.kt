package com.example.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.Methods
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.User
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = provideUserRepository(this)
        val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }

        lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { state ->
                when (state) {
                    is MainViewModel.AuthState.Success -> {
                        Toast.makeText(this@MainActivity, "Успешно!", Toast.LENGTH_SHORT).show()
                        val login = binding.loginEditText.text.toString().trim()
                        val intent = Intent(this@MainActivity, MenuActivity::class.java)
                        intent.putExtra("login", login)
                        startActivity(intent)
                        finish()
                        viewModel.resetState()
                    }
                    is MainViewModel.AuthState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(login, password)
        }

        binding.registerButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.register(login, password)
        }
    }
}

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    sealed class AuthState {
        object Idle : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val hashed = Methods.hashPassword(password)
            val success = repository.loginUser(login, hashed)
            _authState.value = if (success) AuthState.Success else AuthState.Error("Неверный логин или пароль")
        }
    }

    fun register(login: String, password: String) {
        viewModelScope.launch {
            val hashed = Methods.hashPassword(password)
            val success = repository.registerUser(login, hashed)
            _authState.value = if (success) AuthState.Success else AuthState.Error("Пользователь с таким логином уже существует")
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

class MainViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}