/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.viewmodels

import androidx.lifecycle.*
import ca.discretedata.bookit.data.Book
import ca.discretedata.bookit.data.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel internal constructor(
    private val repository: BookRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val searchText: MutableLiveData<String> = state.getLiveData(SEARCH_TEXT_SAVED_STATE_KEY, "")
//    val matchingBooks: LiveData<List<Book>> = repository.getBooksForSearchText(searchText.toString())
    val matchingBooks: LiveData<List<Book>> = repository.getAllBooks()

    // Use a coroutine to insert on the I/O thread.
    fun insertBooks(books: List<Book>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAll(books)
    }

    fun updateSearchText(query: String) {
        // Set the new value in the saved state
        state.set(SEARCH_TEXT_SAVED_STATE_KEY, query)
        // Update the live data object with the query text
        searchText.value = query
    }

    companion object {
        private const val SEARCH_TEXT_SAVED_STATE_KEY = "SEARCH_TEXT_SAVED_STATE_KEY"
    }
}
