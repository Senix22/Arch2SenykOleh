package com.automosen.jsonposter.ui.fetch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.automosen.jsonposter.R
import com.automosen.jsonposter.data.db.entities.Post
import com.automosen.jsonposter.databinding.PostsFragmentBinding
import com.automosen.jsonposter.util.Coroutines
import com.automosen.jsonposter.util.hide
import com.automosen.jsonposter.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.posts_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PostsFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val kodein: Kodein by kodein()
    private val factory : PostsViewModelFactory by instance()
    private lateinit var viewModel: PostsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : PostsFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.posts_fragment,container,false)
        viewModel = ViewModelProviders.of(this,factory).get(PostsViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()

    }
    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toPostItem())
        })
    }

    private fun initRecyclerView(postItems: List<PostItem>){
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(postItems)
        }
        recycler_view.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
    private fun List<Post>.toPostItem() : List<PostItem>{
        return this.map {
            PostItem(it)
        }
    }

}
