package com.automosen.jsonposter

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.automosen.jsonposter.data.db.AppDatabase
import com.automosen.jsonposter.data.network.Api
import com.automosen.jsonposter.data.network.NetworkConnectionInterceptor
import com.automosen.jsonposter.data.preferences.PreferenceProvider
import com.automosen.jsonposter.data.repository.PostRepository
import com.automosen.jsonposter.data.worker.PostWorkerFactory
import com.automosen.jsonposter.ui.fetch.PostsViewModelFactory
import com.automosen.jsonposter.ui.create.CreatePostViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@MVVMApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { Api(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }


        bind() from provider { PostRepository(instance(), instance(), instance()) }
        bind() from provider { PostsViewModelFactory(instance()) }
        bind() from provider { CreatePostViewModelFactory(instance()) }
        bind() from provider { PostWorkerFactory(instance()) }

    }

    private val factory : PostWorkerFactory by instance()

    private fun initWorkManager() {
        WorkManager.initialize(this, Configuration.Builder().run {
            setWorkerFactory(factory)
            build()
        })
    }

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
    }
}