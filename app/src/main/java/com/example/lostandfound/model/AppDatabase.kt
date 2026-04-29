package com.example.lostandfound.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AdvertEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun advertDao(): AdvertDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lost_found_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}