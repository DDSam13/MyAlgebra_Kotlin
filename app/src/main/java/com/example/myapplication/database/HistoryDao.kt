package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item: HistoryItem)

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 10")
    suspend fun getLast10(): List<HistoryItem>
}