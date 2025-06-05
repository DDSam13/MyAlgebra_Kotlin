package com.example.myapplication.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context

class MenuViewModel(private val repository: UserRepository) : ViewModel() {
    private val _deleteState = MutableStateFlow<Boolean?>(null)
    val deleteState: StateFlow<Boolean?> = _deleteState.asStateFlow()

    fun deleteUser(login: String) {
        viewModelScope.launch {
            repository.getUser(login)?.let {
                repository.userDao.deleteUserByLogin(login)
                _deleteState.value = true
            } ?: run {
                _deleteState.value = false
            }
        }
    }

    fun resetState() {
        _deleteState.value = null
    }
}

class MenuViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 