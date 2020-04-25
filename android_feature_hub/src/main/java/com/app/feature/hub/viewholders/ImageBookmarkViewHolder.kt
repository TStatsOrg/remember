package com.app.feature.hub.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.hub.BookmarkViewState
import com.app.feature.hub.databinding.ViewImageBookmarkBinding

class ImageBookmarkViewHolder(
    private val binding: ViewImageBookmarkBinding,
    private val loader: AndroidImageLoader
): BookmarkViewHolder<BookmarkViewState.Image>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Image) = with(viewState) {
        loader.load(url, binding.bookmarkImage)
        binding.bookmarkSaveDate.text = date
        binding.bookmarkSource.text = source
        binding.bookmarkTopic.text = topic

        binding.root.setOnClickListener {
            listener?.onClick(viewState = viewState)
        }

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}