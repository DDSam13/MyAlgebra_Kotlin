package com.example.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: OperationType,
    val coefficients: String,
    val result: String
)