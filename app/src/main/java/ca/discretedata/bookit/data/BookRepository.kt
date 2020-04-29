/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.ListenableWorker
import ca.discretedata.bookit.webservice.VolumeConverter
import ca.discretedata.bookit.webservice.Volumes
import ca.discretedata.bookit.workers.SeedDatabaseWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class BookRepository private constructor(private val bookDao: BookDao) {

    fun getBooksForSearchText(searchText: String): LiveData<List<Book>> {
        return bookDao.getSearchTextBooks(searchText)
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return  bookDao.getAllBooks()
    }

    suspend fun insert(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun insertAll(books: List<Book>) {
        bookDao.insertBookList(books)
    }

    suspend fun remove(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun removeAllBooks() {
        bookDao.deleteAll()
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(bookDao: BookDao) = instance ?: synchronized(this) {
            instance ?: BookRepository(bookDao).also { instance = it }
        }
    }
}