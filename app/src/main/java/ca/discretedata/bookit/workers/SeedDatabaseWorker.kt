/*
 * Copyright (c) 2020. Discrete Data Inc.
 */

package ca.discretedata.bookit.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ca.discretedata.bookit.data.BookDatabase
import ca.discretedata.bookit.webservice.VolumeConverter
import ca.discretedata.bookit.webservice.Volumes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(GATSBY_FILE_NAME + ".json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val volumesType = object : TypeToken<Volumes>() {}.type
                    val volumes: Volumes = Gson().fromJson(jsonReader, volumesType)
                    val books = VolumeConverter.convertVolumes(volumes, GATSBY_FILE_NAME)

                    val database = BookDatabase.getInstance(applicationContext)
                    database.bookDao().insertBookList(books)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
        private val GATSBY_FILE_NAME = "gatsby"
    }
}