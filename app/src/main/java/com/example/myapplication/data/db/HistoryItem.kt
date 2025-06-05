package com.example.myapplication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "history",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["login"],
            childColumns = ["userLogin"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val userLogin: String,
    val type: OperationType,
    val coefficients: String,
    val result: String
)