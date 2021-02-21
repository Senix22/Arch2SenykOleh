package com.automosen.jsonposter.ui.fetch

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.automosen.jsonposter.R
import com.automosen.jsonposter.data.repository.PostRepository
import com.automosen.jsonposter.util.lazyDeferred

class PostsViewModel (
    private val repository: PostRepository
) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getPosts()
    }

    fun onFabClick(view : View){
//        Intent(view.context, CreatePostActivity::class.java).also{
//            view.context.startActivity(it)
//        }
        view.findNavController().navigate(R.id.createPostFragment)
    }
}