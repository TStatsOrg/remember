package com.app.feature.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.feature.topics.databinding.ViewTopicBinding
import com.app.feature.topics.viewholders.TopicViewHolder

class TopicsAdapter: RecyclerView.Adapter<TopicViewHolder>() {

    private var viewState: List<TopicViewState> = listOf()
        set(value) {
            val result = DiffUtil.calculateDiff(TopicsDiff(field, value))
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TopicViewHolder(binding = ViewTopicBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = viewState.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.redraw(viewState = viewState[position])
    }

    fun redraw(viewState: List<TopicViewState>) {
        this.viewState = viewState
    }

    internal class TopicsDiff(
        private val oldContent: List<TopicViewState>,
        private val newContent: List<TopicViewState>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldContent.size
        override fun getNewListSize(): Int = newContent.size
        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos].id == newContent[newPos].id
        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos] == newContent[newPos]
    }
}