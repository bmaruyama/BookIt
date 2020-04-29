/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import ca.discretedata.bookit.data.Book
import ca.discretedata.bookit.databinding.BookListItemBinding
import ca.discretedata.bookit.viewmodels.BookSearchViewModel

class BookAdapter : ListAdapter<Book, BookAdapter.ViewHolder>(
    BookDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: BookListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(book: Book) {
            with(binding) {
                bookTitle.text = book.title
                bookAuthors.text = book.authors
                // TODO: leave jacket image display for later
//                if (book.jacketUrl.isNullOrEmpty()) {
//                    // An empty URL means only show title and authors
//                    bookJacket.visibility = View.GONE
//                    bookTitle.visibility = View.VISIBLE
//                    bookAuthors.visibility = View.VISIBLE
//                } else {
//                    // A valid URL means show the jacket only
//                    bookJacket.visibility = View.VISIBLE
//                    bookTitle.visibility = View.GONE
//                    bookAuthors.visibility = View.GONE
//                }
            }
        }
    }
}

private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean {
        return oldItem.apiId == newItem.apiId
    }

    override fun areContentsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean {
        return oldItem == newItem
    }
}