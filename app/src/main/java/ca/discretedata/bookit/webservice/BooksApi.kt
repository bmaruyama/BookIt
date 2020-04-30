/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.webservice

import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun listBooks(@Query("q") searchText: String): Volumes
}