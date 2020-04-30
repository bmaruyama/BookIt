/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import androidx.lifecycle.LiveData
import ca.discretedata.bookit.webservice.BooksApi
import ca.discretedata.bookit.webservice.VolumeConverter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookRepository private constructor(private val bookDao: BookDao) {

    private val booksApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    fun getBooksForSearchText(searchText: String): LiveData<List<Book>> {
        return bookDao.getSearchTextBooks(searchText)
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookDao.getAllBooks()
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

    suspend fun retrieveVolumes(query: String): List<Book> {
        val volumes = booksApi.listBooks(query)
        return VolumeConverter.convertVolumes(volumes, query)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(bookDao: BookDao) = instance ?: synchronized(this) {
            instance ?: BookRepository(bookDao).also { instance = it }
        }

        private val baseUrl = "https://www.googleapis.com/books/v1/"
    }
}