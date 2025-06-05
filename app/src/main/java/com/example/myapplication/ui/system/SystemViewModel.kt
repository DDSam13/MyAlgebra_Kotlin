package com.example.myapplication.ui.system

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

class SystemViewModel(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModel() {
    private val _result = MutableStateFlow<String>("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun solve(
        a1Text: String, b1Text: String, c1Text: String,
        a2Text: String, b2Text: String, c2Text: String
    ) {
        viewModelScope.launch {
            val res = try {
                val a1 = a1Text.toDouble()
                val b1 = b1Text.toDouble()
                val c1 = c1Text.toDouble()
                val a2 = a2Text.toDouble()
                val b2 = b2Text.toDouble()
                val c2 = c2Text.toDouble()
                val answer = Math.solveSystem(a1, b1, c1, a2, b2, c2)
                historyRepository.insertHistory(
                    HistoryItem(
                        userLogin = userLogin,
                        type = OperationType.SYSTEM,
                        coefficients = "$a1,$b1,$c1,$a2,$b2,$c2",
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

class SystemViewModelFactory(private val historyRepository: HistoryRepository, private val userLogin: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SystemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SystemViewModel(historyRepository, userLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 