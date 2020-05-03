package com.app.feature.topics.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.topics.databinding.ViewTopicBinding
import com.app.views.viewstate.TopicViewState

class TopicViewHolder(
    private val binding: ViewTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    fun redraw(viewState: TopicViewState) = with(viewState) {
        binding.topicName.text = name
    }
}