/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        // Singleton
        @Volatile
        private var instance: BookDatabase? = null
        private const val DB_NAME = "Books.db"

        fun getInstance(context: Context): BookDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BookDatabase {
            return Room.databaseBuilder(context, BookDatabase::class.java, DB_NAME)
                .build()
        }
    }
}