package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryItem::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}