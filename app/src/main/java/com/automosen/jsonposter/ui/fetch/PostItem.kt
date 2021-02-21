package com.automosen.jsonposter.ui.fetch

import android.graphics.Color
import com.automosen.jsonposter.R
import com.automosen.jsonposter.data.db.entities.Post
import com.automosen.jsonposter.databinding.ItemPostBinding
import com.xwray.groupie.databinding.BindableItem

class PostItem (
    private val post : Post
) : BindableItem<ItemPostBinding>(){
    override fun getLayout(): Int = R.layout.item_post

    override fun bind(viewBinding: ItemPostBinding, position: Int) {
        viewBinding.post = post
        when (post.userId) {
            3, 4 -> {
                viewBinding.post = post
                viewBinding.title.setBackgroundColor(Color.GRAY)
                post.title = "${post.userId} user  З попередженням"
            }
            7 -> {
                viewBinding.post = post
                viewBinding.title.setBackgroundColor(Color.RED)
                post.body = ""
                post.title = "Тут міг бути пост юзера ${post.userId}, але він забанений"
            }
            else -> {
                viewBinding.title.setBackgroundColor(Color.WHITE)
            }

        }
    }
}