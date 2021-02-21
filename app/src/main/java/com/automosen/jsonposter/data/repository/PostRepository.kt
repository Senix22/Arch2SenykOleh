package com.automosen.jsonposter.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.automosen.jsonposter.data.db.AppDatabase
import com.automosen.jsonposter.data.db.entities.Post
import com.automosen.jsonposter.data.network.Api
import com.automosen.jsonposter.data.network.SafeApiRequest
import com.automosen.jsonposter.data.preferences.PreferenceProvider
import com.automosen.jsonposter.data.worker.PostWorker
import com.automosen.jsonposter.data.worker.PostWorkerFactory
import com.automosen.jsonposter.util.Coroutines
import com.automosen.jsonposter.util.now
import com.automosen.jsonposter.util.timediff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val MINIMUM_INTERVAL = 300 // 5 menit

class PostRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val posts = MutableLiveData<List<Post>>()

    init {
        posts.observeForever {
            savePosts(it)
        }
    }

    suspend fun getPosts(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            fetchPosts()
            db.getPostDao().getPosts()
        }
    }
    suspend fun addPost(title : String, body: String){
        return withContext(Dispatchers.IO){
            val response = apiRequest { api.addPost(Post(null, null, title, body)) }
            db.getPostDao().addPost(response)
        }
    }


    private suspend fun fetchPosts() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)) {
            val response = apiRequest { api.getPosts() }
            posts.postValue(response)
        }
    }

    private fun savePosts(posts: List<Post>) {
        Coroutines.io {
            prefs.saveLastSavedAt(now())
            db.getPostDao().savePosts(posts)
        }
    }

    private fun isFetchNeeded(lastSavedTime: String): Boolean {
        return timediff(lastSavedTime, now()) > MINIMUM_INTERVAL
    }


}