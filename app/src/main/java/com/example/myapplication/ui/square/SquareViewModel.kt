package com.example.myapplication.ui.square

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

class SquareViewModel(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModel() {
    private val _result = MutableStateFlow<String>("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun solve(aText: String, bText: String, cText: String) {
        viewModelScope.launch {
            val res = try {
                val a = aText.toDouble()
                val b = bText.toDouble()
                val c = cText.toDouble()
                val answer = Math.square(a, b, c)
                historyRepository.insertHistory(
                    HistoryItem(
                        userLogin = userLogin,
                        type = OperationType.SQUARE,
                        coefficients = "$a,$b,$c",
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

class SquareViewModelFactory(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SquareViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SquareViewModel(historyRepository, userLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 