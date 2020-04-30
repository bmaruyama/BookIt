/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

import com.google.gson.annotations.SerializedName

data class Volumes(
    @SerializedName("totalItems")
    val totalItems: Long,
    @SerializedName("items")
    val items: List<Volume>
)