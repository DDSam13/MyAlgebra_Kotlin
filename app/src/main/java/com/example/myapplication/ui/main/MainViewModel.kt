package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utils.Methods
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
