/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    // Keep the id from the API if disambiguation would be required in the future.
    @ColumnInfo(name = "api_id")
    val apiId: String,

    val title: String,

    val authors: String, // convert authors array to single string?

    @ColumnInfo(name = "jacket_url")
    val jacketUrl: String = "",

    @ColumnInfo(name = "query_order")
    val queryOrder: Long,

    // Index this column as this is a simplified way of gathering the records together.
    // If the app required search history, then a Search table with a foreign key would be useful.
    @ColumnInfo(name = "search_text", index = true)
    val searchText: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookId: Long = 0

    override fun toString() = title
}