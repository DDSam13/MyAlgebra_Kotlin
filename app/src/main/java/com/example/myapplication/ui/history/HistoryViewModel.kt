package com.example.myapplication.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    fun loadHistory(userLogin: String) {
        viewModelScope.launch {
            val items = repository.getLast10ByUser(userLogin)
            val formatted = items.map { item ->
                val operationStr = when (item.type) {
                    com.example.myapplication.data.db.OperationType.LINE -> {
                        val (a, b) = item.coefficients.split(",").map { it.trim() }
                        "${'$'}{a}x+${'$'}{b}=0"
                    }
                    com.example.myapplication.data.db.OperationType.SQUARE -> {
                        val (a, b, c) = item.coefficients.split(",").map { it.trim() }
                        "${'$'}{a}x²+${'$'}{b}x+${'$'}{c}=0"
                    }
                    com.example.myapplication.data.db.OperationType.NOK -> {
                        val (a, b) = item.coefficients.split(",").map { it.trim() }
                        "НОК(${'$'}a, ${'$'}b)"
                    }
                    com.example.myapplication.data.db.OperationType.NOD -> {
                        val (a, b) = item.coefficients.split(",").map { it.trim() }
                        "НОД(${'$'}a, ${'$'}b)"
                    }
                    com.example.myapplication.data.db.OperationType.SYSTEM -> {
                        val (a1, b1, c1, a2, b2, c2) = item.coefficients.split(",").map { it.trim() }
                        "Система: ${'$'}{a1}x+${'$'}{b1}y=${'$'}{c1}; ${'$'}{a2}x+${'$'}{b2}y=${'$'}{c2}"
                    }
                }
                "$operationStr : ${'$'}{item.result}"
            }
            _history.value = formatted
        }
    }
}

class HistoryViewModelFactory(private val repository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
