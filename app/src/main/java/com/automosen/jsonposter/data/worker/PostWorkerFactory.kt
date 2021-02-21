package com.automosen.jsonposter.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.automosen.jsonposter.data.repository.PostRepository

class PostWorkerFactory(
    private val repository: PostRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return PostWorker(appContext,workerParameters, repository)
    }


}