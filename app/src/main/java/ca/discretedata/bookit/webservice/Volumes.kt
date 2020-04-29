/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

data class Volumes(
    val totalItems: Long,
    val items: List<Volume>
)