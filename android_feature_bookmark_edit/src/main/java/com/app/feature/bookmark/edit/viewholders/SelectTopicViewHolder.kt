package com.app.feature.bookmark.edit.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.bookmark.edit.SelectTopicViewState
import com.app.feature.bookmark.edit.databinding.ViewSelectableTopicBinding

class SelectTopicViewHolder(
    private val binding: ViewSelectableTopicBinding
): RecyclerView.ViewHolder(binding.root) {

    var listener: Listener? = null

    fun redraw(state: SelectTopicViewState) = with(state) {
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