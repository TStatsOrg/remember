package com.app.feature.hub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.feature.hub.databinding.ViewBookmarkBinding

class BookmarksAdapter: RecyclerView.Adapter<BookmarkViewHolder>() {

    private var viewState: List<BookmarkViewState> = listOf()
        set(value) {
            val result = DiffUtil.calculateDiff(BookmarkDif(field, value))
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(binding = ViewBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = viewState.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.redraw(viewState = viewState[position])
    }

    fun redraw(viewState: List<BookmarkViewState>) {
        this.viewState = viewState
    }

    internal class BookmarkDif(
        private val oldContent: List<BookmarkViewState>,
        private val newContent: List<BookmarkViewState>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldContent.size
        override fun getNewListSize(): Int = newContent.size
        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos].id == newContent[newPos].id
        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos] == newContent[newPos]
    }
}