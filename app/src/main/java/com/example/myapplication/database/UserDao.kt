package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?

    @Query("DELETE FROM users WHERE login = :login")
    suspend fun deleteUserByLogin(login: String)
}