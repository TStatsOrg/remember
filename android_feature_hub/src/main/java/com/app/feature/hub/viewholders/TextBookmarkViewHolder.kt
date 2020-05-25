package com.app.feature.hub.viewholders

import com.app.feature.hub.databinding.ViewTextBookmarkBinding
import com.app.views.viewstate.BookmarkViewState

class TextBookmarkViewHolder(
    private val binding: ViewTextBookmarkBinding
): BookmarkViewHolder<BookmarkViewState.Text>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Text) = with(viewState) {
        binding.bookmarkTitle.text = text
        binding.bookmarkInfo.text = info
        binding.bookmarkTopic.text = topic

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}