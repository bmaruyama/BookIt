/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("title")
    val title: String?,
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?
)
