package com.app.feature.hub.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.hub.BookmarkViewState
import com.app.feature.hub.databinding.ViewLinkBookmarkBinding

class LinkBookmarkViewHolder(
    private val binding: ViewLinkBookmarkBinding,
    private val loader: AndroidImageLoader
): BookmarkViewHolder<BookmarkViewState.Link>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Link) = with(viewState) {
        loader.load(icon, binding.bookmarkIcon)
        binding.bookmarkSaveDate.text = date
        binding.bookmarkTitle.text = title
        binding.bookmarkCaption.text = caption
    }
}