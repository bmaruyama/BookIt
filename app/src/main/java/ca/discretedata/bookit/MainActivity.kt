/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import ca.discretedata.bookit.adapters.BookAdapter
import ca.discretedata.bookit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val recyclerView = binding.bookList
        recyclerView.adapter = BookAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Check for a search intent
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // Perform search and store the data in the database.
            }
        }

        setContentView(binding.root)
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
