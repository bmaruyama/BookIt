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
import com.bumptech.glide.RequestManager

class BookAdapter(private val requestManager: RequestManager) :
    ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), requestManager)
    }

    class ViewHolder(
        private val binding: BookListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(book: Book, requestManager: RequestManager) {
            with(binding) {
                bookTitle.text = book.title
                bookAuthors.text = book.authors
                // TODO: leave jacket image display for later
                if (book.jacketUrl.isEmpty()) {
                    // An empty URL means only show title and authors
                    imageLayout.visibility = View.GONE
                    textLayout.visibility = View.VISIBLE
                    requestManager.clear(bookJacket)
                } else {
                    // A valid URL means show the jacket only
                    imageLayout.visibility = View.VISIBLE
                    textLayout.visibility = View.GONE
                    requestManager.load(book.jacketUrl).into(bookJacket)
                }
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