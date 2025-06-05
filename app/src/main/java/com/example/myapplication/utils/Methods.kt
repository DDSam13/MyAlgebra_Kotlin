package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.OperationType
import com.example.myapplication.data.db.HistoryItem
import java.security.MessageDigest
import com.example.myapplication.data.repository.UserRepository

object Methods {
    suspend fun saveOperation(
        context: Context,
        type: OperationType,
        coefficients: List<Number>,
        result: String
    ) {
        val db = HistoryDatabase.getDatabase(context)
        val coeffStr = coefficients.joinToString(",")
        db.historyDao().insert(
            HistoryItem(
                type = type,
                coefficients = coeffStr,
                result = result
            )
        )
    }

    suspend fun getHistory(context: Context): List<String> {
        val db = HistoryDatabase.getDatabase(context)
        return db.historyDao().getLast10().map { item ->
            val operationStr = when (item.type) {
                OperationType.LINE -> {
                    val (a, b) = item.coefficients.split(",").map { it.trim() }
                    "${a}x+${b}=0"
                }
                OperationType.SQUARE -> {
                    val (a, b, c) = item.coefficients.split(",").map { it.trim() }
                    "${a}x²+${b}x+${c}=0"
                }
                OperationType.NOK -> {
                    val (a, b) = item.coefficients.split(",").map { it.trim() }
                    "НОК($a, $b)"
                }
                OperationType.NOD -> {
                    val (a, b) = item.coefficients.split(",").map { it.trim() }
                    "НОД($a, $b)"
                }
                OperationType.SYSTEM -> {
                    val (a1, b1, c1, a2, b2, c2) = item.coefficients.split(",").map { it.trim() }
                    "Система: ${a1}x+${b1}y=${c1}; ${a2}x+${b2}y=${c2}"
                }
            }
            "$operationStr : ${item.result}"
        }
    }

    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

fun provideUserRepository(context: Context): UserRepository {
    val db = AppDatabase.getDatabase(context)
    return UserRepository(db.userDao())
}