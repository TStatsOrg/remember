package com.app.feature.topics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dependencies.navigation.Navigation
import com.app.feature.topics.databinding.ViewTopicsBinding
import com.app.feature.topics.viewholders.TopicViewHolder
import com.app.shared.feature.topics.TopicsViewModel
import com.app.views.viewstate.TopicViewState
import org.koin.android.ext.android.inject

class TopicsActivity: AppCompatActivity() {

    private val viewModel: TopicsViewModel by inject()
    private val navigator: Navigation by inject()
    private val adapter: TopicsAdapter by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()

    private val binding by lazy {
        ViewTopicsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeTopicState {
            redraw(viewState = TopicsViewState(state = it))
        }

        adapter.listener = object : TopicViewHolder.Listener {
            override fun onClickTopic(state: TopicViewState) {
                viewModel.filter(byTopic = state.topicState)
                finish()
            }
        }

        binding.addTopicButton.setOnClickListener {
            navigator.seeAddTopic(context = this)
        }

        binding.topicsRecyclerView.adapter = adapter
        binding.topicsRecyclerView.layoutManager = layoutManager
        binding.topicsRecyclerView.itemAnimator = animator

        viewModel.loadTopics()
    }

    private fun redraw(viewState: TopicsViewState) {
        adapter.redraw(viewState = viewState.topics)
    }

    override fun onDestroy() {
        viewModel.cleanup()
        super.onDestroy()
    }
}