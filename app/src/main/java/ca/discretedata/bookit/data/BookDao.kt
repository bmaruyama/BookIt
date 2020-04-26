/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    /**
     * Get books associated with the search text.
     */
    @Query("SELECT * FROM books WHERE search_text = :searchText ORDER BY query_order ASC")
    fun getSearchTextBooks(searchText: String): LiveData<List<Book>>

    /**
     * Get all books
     */
    @Query("SELECT * FROM books ORDER BY query_order ASC")
    fun getAllBooks(): LiveData<List<Book>>

    /**
     * Get a single book
     */
    @Query("SELECT * FROM books WHERE id = :bookId ORDER BY query_order")
    fun getBook(bookId: Long): LiveData<Book>

    /**
     * Insert a book
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    /**
     * Insert a list of books. Assumes single inserts will involve multiple transactions.
     */
    @Insert
    suspend fun insertBookList(books: List<Book>)

    /**
     * Delete a book
     */
    @Delete
    suspend fun deleteBook(book: Book)

    /**
     * Delete all books
     */
    @Query("DELETE FROM books")
    suspend fun deleteAll()
}