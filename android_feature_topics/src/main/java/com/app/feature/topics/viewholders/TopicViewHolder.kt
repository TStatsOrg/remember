package com.app.feature.topics.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.topics.databinding.ViewTopicBinding
import com.app.views.viewstate.TopicViewState

class TopicViewHolder(
    private val binding: ViewTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    var listener: Listener? = null

    fun redraw(viewState: TopicViewState) = with(viewState) {
        binding.topicName.text = name
        binding.root.setOnClickListener {
            listener?.onClickTopic(state = viewState)
        }
        binding.root.setOnLongClickListener {
            listener?.onLongClickTopic(state = viewState)
            true
        }
    }

    interface Listener {
        fun onClickTopic(state: TopicViewState)
        fun onLongClickTopic(state: TopicViewState)
    }
}