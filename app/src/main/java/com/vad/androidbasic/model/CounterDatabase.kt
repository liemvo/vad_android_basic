package com.vad.androidbasic.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Counter::class)], version = 1)
abstract class CounterDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao
    companion object {
        private const val databaseName = "counter_database"

        @Volatile
        private var INSTANCE: CounterDatabase? = null

        fun getDataBase(context: Context): CounterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    CounterDatabase::class.java,
                    databaseName
                ).build().apply {
                    INSTANCE = this
                }
            }
        }
    }

}