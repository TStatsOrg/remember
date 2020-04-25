package com.app.feature.topics.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.topics.TopicViewState
import com.app.feature.topics.databinding.ViewTopicBinding

class TopicViewHolder(
    private val binding: ViewTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    fun redraw(viewState: TopicViewState) = with(viewState) {
        binding.topicName.text = name
    }
}