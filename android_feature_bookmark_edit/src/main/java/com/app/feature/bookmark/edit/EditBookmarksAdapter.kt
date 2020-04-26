package com.app.feature.bookmark.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.feature.bookmark.edit.databinding.ViewSelectableTopicBinding
import com.app.feature.bookmark.edit.viewholders.SelectTopicViewHolder

class EditBookmarksAdapter: RecyclerView.Adapter<SelectTopicViewHolder>() {

    var listener: SelectTopicViewHolder.Listener? = null

    private var viewState: List<SelectTopicViewState> = listOf()
        set(value) {
            val result = DiffUtil.calculateDiff(TopicsDiff(field, value))
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectTopicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SelectTopicViewHolder(binding = ViewSelectableTopicBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = viewState.size

    override fun onBindViewHolder(holder: SelectTopicViewHolder, position: Int) {
        holder.redraw(state = viewState[position])
    }

    override fun onViewAttachedToWindow(holder: SelectTopicViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.listener = listener
    }

    override fun onViewDetachedFromWindow(holder: SelectTopicViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.listener = null
    }

    fun redraw(viewState: List<SelectTopicViewState>) {
        this.viewState = viewState
    }

    internal class TopicsDiff(
        private val oldContent: List<SelectTopicViewState>,
        private val newContent: List<SelectTopicViewState>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldContent.size
        override fun getNewListSize(): Int = newContent.size
        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos].id == newContent[newPos].id
        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos] == newContent[newPos]
    }
}