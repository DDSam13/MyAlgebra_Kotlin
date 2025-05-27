package com.example.myapplocation.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [User::class], version = 1)
abstract class UsersDateBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: UsersDateBase? = null

        fun getDatabase(context: Context): UsersDateBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDateBase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}