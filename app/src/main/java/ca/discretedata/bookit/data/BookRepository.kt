/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import androidx.lifecycle.LiveData

class BookRepository private constructor(private val bookDao: BookDao) {

    fun getBooksForSearchText(searchText: String): LiveData<List<Book>> {
        return bookDao.getSearchTextBooks(searchText)
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
        @Volatile private var instance: BookRepository? = null

        fun getInstance(bookDao: BookDao) =
            instance ?: synchronized(this) {
                instance ?: BookRepository(bookDao).also { instance = it }
            }
    }
}