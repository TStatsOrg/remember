package com.app.feature.preview.viewholders

import com.app.feature.preview.databinding.ViewTextPreviewBinding
import com.app.views.viewstate.BookmarkViewState

class TextPreviewViewHolder(
    private val binding: ViewTextPreviewBinding
): PreviewViewHolder<BookmarkViewState.Text>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Text) = with(viewState) {
        binding.bookmarkTitle.text = text
        binding.bookmarkInfo.text = info
    }
}