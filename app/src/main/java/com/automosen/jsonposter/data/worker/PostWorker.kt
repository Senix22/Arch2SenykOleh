package com.automosen.jsonposter.data.worker

import android.content.Context
import androidx.work.*
import com.automosen.jsonposter.data.db.AppDatabase
import com.automosen.jsonposter.data.network.Api
import com.automosen.jsonposter.data.network.NetworkConnectionInterceptor
import com.automosen.jsonposter.data.preferences.PreferenceProvider
import com.automosen.jsonposter.data.repository.PostRepository
import com.automosen.jsonposter.util.ApiException
import com.automosen.jsonposter.util.Coroutines
import com.automosen.jsonposter.util.toast

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class PostWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val repository: PostRepository
) :
    CoroutineWorker(context, params){


    override suspend fun doWork(): Result {
        try {
            val title = inputData.getString("title")
            val body = inputData.getString("body")
            try {
                repository.addPost(title!!, body!!)
            } catch (e: ApiException) {
                return Result.failure()
            }
            return Result.success()
        } catch (error: Throwable) {
            return Result.failure()
        }
    }

}