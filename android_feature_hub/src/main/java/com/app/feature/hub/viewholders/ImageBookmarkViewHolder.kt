package com.app.feature.hub.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.hub.databinding.ViewImageBookmarkBinding
import com.app.views.viewstate.BookmarkViewState

class ImageBookmarkViewHolder(
    private val binding: ViewImageBookmarkBinding,
    private val loader: AndroidImageLoader
): BookmarkViewHolder<BookmarkViewState.Image>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Image) = with(viewState) {
        loader.load(url, binding.bookmarkImage)
        binding.bookmarkInfo.text = info
        binding.bookmarkTopic.text = topic

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}