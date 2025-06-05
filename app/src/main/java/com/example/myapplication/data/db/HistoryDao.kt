package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item: HistoryItem)

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 10")
    suspend fun getLast10(): List<HistoryItem>

    @Query("SELECT * FROM history WHERE userLogin = :userLogin ORDER BY id DESC LIMIT 10")
    suspend fun getLast10ByUser(userLogin: String): List<HistoryItem>

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<HistoryItem>
}