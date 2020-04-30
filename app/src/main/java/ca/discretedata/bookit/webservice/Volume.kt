/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

import com.google.gson.annotations.SerializedName

data class Volume(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)