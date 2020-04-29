/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

import ca.discretedata.bookit.data.Book

object VolumeConverter {

    private val UNKNOWN_AUTHOR = "Unknown Author"
    private val UNKNOWN_TITLE = "Unknown Title"

    fun convertVolumes(volumes: Volumes, searchText: String): List<Book> {
        val books = mutableListOf<Book>()
        var index = 0L

        for (volume in volumes.items) {
            index++
            val id = volume.id
            val titleText = volume.volumeInfo.title ?: UNKNOWN_TITLE
            val authorText = volume.volumeInfo.authors?.joinToString() ?: UNKNOWN_AUTHOR
            val jacketLink = getImageLink(volume.volumeInfo.imageLinks)

            val book = Book(id, titleText, authorText, jacketLink, index, searchText)
            books.add(book)
        }

        return books
    }

     private fun getImageLink(links: ImageLinks?) : String {
        // Prefer smaller images to larger ones.
        return when {
            links == null -> ""
            !links.smallThumbnail.isNullOrBlank() -> links.smallThumbnail
            !links.thumbnail.isNullOrBlank() -> links.thumbnail
            !links.small.isNullOrBlank() -> links.small
            !links.medium.isNullOrBlank() -> links.medium
            !links.large.isNullOrBlank() -> links.large
            !links.extraLarge.isNullOrBlank() -> links.extraLarge
            else -> return ""
        }
    }
}