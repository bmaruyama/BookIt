/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ca.discretedata.bookit.adapters.BookAdapter
import ca.discretedata.bookit.data.BookDatabase
import ca.discretedata.bookit.data.BookRepository
import ca.discretedata.bookit.databinding.ActivityMainBinding
import ca.discretedata.bookit.viewmodels.BookSearchViewModel
import ca.discretedata.bookit.viewmodels.BookSearchViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bookViewModel: BookSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val recyclerView = binding.bookList
        val adapter = BookAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val repository = loadRepository(this)

        val viewModelFactory = BookSearchViewModelFactory(repository, this, savedInstanceState)
        bookViewModel = ViewModelProvider(this, viewModelFactory).get(BookSearchViewModel::class.java)
        bookViewModel.matchingBooks.observe(this, Observer {books ->
            books?.let {
                adapter.submitList(it)
            }
        })

        processIntent(intent)

        setContentView(binding.root)
    }

    private fun loadRepository(context: Context): BookRepository {
        val dao = BookDatabase.getInstance(context.applicationContext).bookDao()
        return BookRepository.getInstance(dao)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let {
            processIntent(it)
        }
    }

    private fun processIntent(intent: Intent) {
        // Check for a search intent
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // Set the query in the view model, which will trigger a call to the repository.
                bookViewModel.updateSearchText(query)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menuInflater.inflate(R.menu.options_menu, menu)

            // Using the search manager, associate the searchable configuration with the menu's SearchView.
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            (menu.findItem(R.id.search).actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
            }
        }

        return true
    }
}
