package com.example.myapplication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Relation

@Entity(tableName = "users")
data class User(
    @PrimaryKey val login: String,
    val password: String
)

data class UserWithHistory(
    @Embedded val user: User,
    @Relation(
        parentColumn = "login",
        entityColumn = "userLogin"
    )
    val history: List<HistoryItem>
)