package com.app.feature.hub.viewholders

import com.app.feature.hub.BookmarkViewState
import com.app.feature.hub.databinding.ViewTextBookmarkBinding

class TextBookmarkViewHolder(
    private val binding: ViewTextBookmarkBinding
): BookmarkViewHolder<BookmarkViewState.Text>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Text) = with(viewState) {
        binding.bookmarkText.text = text
        binding.bookmarkSaveDate.text = date
    }
}