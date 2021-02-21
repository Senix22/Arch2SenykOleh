package com.automosen.jsonposter.ui.create

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.automosen.jsonposter.R
import com.automosen.jsonposter.databinding.CreatePostFragmentBinding
import com.automosen.jsonposter.util.hide
import com.automosen.jsonposter.util.show
import com.automosen.jsonposter.util.toast
import kotlinx.android.synthetic.main.create_post_fragment.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CreatePostFragment : Fragment(), KodeinAware, CreatePostInterface {

    companion object {
        fun newInstance() = CreatePostFragment()
    }

    override val kodein: Kodein by kodein()

    private val factory : CreatePostViewModelFactory by instance()
    private lateinit var viewModel: CreatePostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.create_post_fragment2, container, false)
        val binding: CreatePostFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.create_post_fragment,container,false)
        viewModel = ViewModelProviders.of(this,factory).get(CreatePostViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.createPostInterface = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onSuccess() {
        progress_bar.hide()
        context?.toast("Success add Post")
        findNavController().navigate(R.id.postsFragment)
    }

    override fun onFailed(message: String) {
        progress_bar.hide()
        context?.toast(message)
    }

    override fun onPostPone() {
        progress_bar.hide()
        context?.toast("Your post is postponed until the internet is connected")
        findNavController().navigate(R.id.postsFragment)
    }

    override fun onRequest() {
        progress_bar.show()
    }
}
