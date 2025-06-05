package com.example.myapplication.ui.line

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utils.Math
import com.example.myapplication.data.db.OperationType
import com.example.myapplication.data.db.HistoryItem
import com.example.myapplication.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LineViewModel(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModel() {
    private val _result = MutableStateFlow<String>("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun solve(aText: String, bText: String) {
        viewModelScope.launch {
            val res = try {
                val a = aText.toDouble()
                val b = bText.toDouble()
                val answer = Math.line(a, b)
                historyRepository.insertHistory(
                    HistoryItem(
                        userLogin = userLogin,
                        type = OperationType.LINE,
                        coefficients = "$a,$b",
                        result = answer
                    )
                )
                answer
            } catch (e: Exception) {
                "Ошибка ввода!"
            }
            _result.value = res
        }
    }
}

class LineViewModelFactory(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LineViewModel(historyRepository, userLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 