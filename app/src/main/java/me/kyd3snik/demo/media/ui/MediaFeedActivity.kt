package me.kyd3snik.demo.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import me.kyd3snik.demo.databinding.ViewMediaFeedBinding
import me.kyd3snik.demo.lce.ui.LCEActivity
import me.kyd3snik.demo.media.ui.adapter.PostsAdapter
import me.kyd3snik.demo.media.ui.adapter.TopicsAdapter
import me.kyd3snik.demo.simple.disposeOnDestroy

class MediaFeedActivity : LCEActivity() {

    private val viewModel: MediaFeedViewModel by viewModels { MediaFeedViewModelFactory() }
    private val topicsAdapter = TopicsAdapter { viewModel.selectTopic(it) }
    private val postsAdapter = PostsAdapter { viewModel.openPost(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ViewMediaFeedBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(viewBinding.root)
        with(viewBinding) {
            topics.adapter = topicsAdapter
            posts.adapter = postsAdapter
        }

        viewModel.observeState()
            .subscribe { state ->
                when {
                    state.isLoading -> showLoading()
                    state.errorOrNull != null -> showError(state.errorOrNull, viewModel::retry)
                    else -> showContent()
                }
                topicsAdapter.submitList(state.topics)
                postsAdapter.submitList(state.posts)

            }.disposeOnDestroy()

        viewModel.fetchData() //TODO: move into factory and call once after create to support configuration change?

    }
}