package com.example.myapplication.data.repository

import com.example.myapplication.data.db.User
import com.example.myapplication.data.db.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(login: String, passwordHash: String): Boolean {
        val existing = userDao.getUserByLogin(login)
        return if (existing == null) {
            userDao.insertUser(User(login, passwordHash))
            true
        } else {
            false
        }
    }

    suspend fun loginUser(login: String, passwordHash: String): Boolean {
        val user = userDao.getUserByLogin(login)
        return user != null && user.password == passwordHash
    }

    suspend fun getUser(login: String): User? = userDao.getUserByLogin(login)
} 