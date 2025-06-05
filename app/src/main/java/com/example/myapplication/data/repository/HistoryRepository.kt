package com.example.myapplication.data.repository

import com.example.myapplication.data.db.HistoryDao
import com.example.myapplication.data.db.HistoryItem

class HistoryRepository(private val historyDao: HistoryDao) {
    suspend fun getLast10ByUser(userLogin: String) = historyDao.getLast10ByUser(userLogin)
    suspend fun insertHistory(item: HistoryItem) = historyDao.insert(item)
} 