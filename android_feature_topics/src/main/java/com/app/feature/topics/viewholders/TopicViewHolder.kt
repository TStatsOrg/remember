package com.app.feature.topics.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.topics.TopicViewState
import com.app.feature.topics.databinding.ViewTopicBinding

class TopicViewHolder(
    private val binding: ViewTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    var listener: Listener? = null

    fun redraw(viewState: TopicViewState) = with(viewState) {
        binding.topicName.text = name
        binding.topicCheckMark.visibility = checkMarkVisibility

        binding.root.setOnClickListener {
            when (isEditing) {
                true -> listener?.onTopicClick(id = id)
                else -> Unit
            }
        }
    }

    interface Listener {
        fun onTopicClick(id: Int)
    }
}