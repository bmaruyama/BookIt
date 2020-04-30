/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.viewmodels

import androidx.lifecycle.*
import ca.discretedata.bookit.data.Book
import ca.discretedata.bookit.data.BookRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel internal constructor(
    private val repository: BookRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val searchText: MutableLiveData<String> = state.getLiveData(SEARCH_TEXT_SAVED_STATE_KEY, "")
//    val matchingBooks: LiveData<List<Book>> = repository.getBooksForSearchText(searchText.toString())
    val matchingBooks: LiveData<List<Book>> = repository.getAllBooks()

    val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        throwable.printStackTrace()
        // TODO: use LiveData to show Toast to user.
    }

    // Use a coroutine to insert on the I/O thread.
    fun insertBooks(books: List<Book>) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        repository.insertAll(books)
    }

    fun updateSearchText(query: String) {
        // Do a heavy-handed means of syncing data. Delete all books in the database
        val deleteJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.removeAllBooks()
        }
        // Retrieve books matching the query from Google Books and put them in the database.
        // Verify if this is backed by a thread pool or if the launches will be serialized.
        val retrieveJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val books = repository.retrieveVolumes(query)
            if (books.size > 0) {
                repository.insertAll(books)
            }
        }

        // Set the new value in the saved state
        state.set(SEARCH_TEXT_SAVED_STATE_KEY, query)
        // Update the live data object with the query text (if it's required)
        searchText.value = query
    }

    companion object {
        private const val SEARCH_TEXT_SAVED_STATE_KEY = "SEARCH_TEXT_SAVED_STATE_KEY"
    }
}
