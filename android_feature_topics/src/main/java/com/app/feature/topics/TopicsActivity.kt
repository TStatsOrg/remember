package com.app.feature.topics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.feature.topics.databinding.ViewTopicsBinding
import com.app.feature.topics.viewholders.TopicViewHolder
import com.app.shared.feature.topics.TopicsViewModel
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class TopicsActivity: AppCompatActivity() {

    private val viewModel: TopicsViewModel by inject()
    private val navigator: AppNavigation by inject()
    private val adapter: TopicsAdapter by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()

    private val binding by lazy {
        ViewTopicsBinding.inflate(layoutInflater)
    }

    private val bookmarkId by lazy {
        intent.extras?.getInt(AppNavigation.BOOKMARK_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeTopicState {
            redraw(viewState = TopicsViewState(state = it))
        }

        viewModel.observeBookmarkUpdated {
            finish()
        }

        binding.addTopicButton.setOnClickListener {
            navigator.seeAddTopic(context = this)
        }

        binding.topicsRecyclerView.adapter = adapter
        binding.topicsRecyclerView.layoutManager = layoutManager
        binding.topicsRecyclerView.itemAnimator = animator
    }

    private fun redraw(viewState: TopicsViewState) {
        adapter.redraw(viewState = viewState.topics)

        adapter.listener = object : TopicViewHolder.Listener {
            override fun onTopicClick(id: Int) {
                when (viewState.editedBookmarkId) {
                    is Int -> viewModel.update(bookmark = viewState.editedBookmarkId, withTopic = id)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTopics(forBookmarkId = bookmarkId)
    }
}