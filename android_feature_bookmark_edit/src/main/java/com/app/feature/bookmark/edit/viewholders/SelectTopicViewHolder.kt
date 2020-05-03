package com.app.feature.bookmark.edit.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.bookmark.edit.databinding.ViewSelectableTopicBinding
import com.app.views.viewstate.TopicViewState

class SelectTopicViewHolder(
    private val binding: ViewSelectableTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    var listener: Listener? = null

    fun redraw(state: TopicViewState.Selectable) = with(state) {
        binding.topicName.text = name
        binding.topicSelectedCheck.visibility = checkMarkVisibility

        binding.root.setOnClickListener {
            listener?.onClickTopic(id = id)
        }
    }

    interface Listener {
        fun onClickTopic(id: Int)
    }
}