package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?

    @Query("DELETE FROM users WHERE login = :login")
    suspend fun deleteUserByLogin(login: String)

    @Transaction
    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserWithHistory(login: String): UserWithHistory?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}