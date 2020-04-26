package com.app.feature.hub.viewholders

import com.app.feature.hub.BookmarkViewState
import com.app.feature.hub.databinding.ViewTextBookmarkBinding

class TextBookmarkViewHolder(
    private val binding: ViewTextBookmarkBinding
): BookmarkViewHolder<BookmarkViewState.Text>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Text) = with(viewState) {
        binding.bookmarkTitle.text = text
        binding.bookmarkSaveDate.text = date
        binding.bookmarkSource.text = source
        binding.bookmarkTopic.text = topic

        binding.bookmarkTopic.setOnClickListener {
            listener?.onTopicClick(viewState = viewState)
        }

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}