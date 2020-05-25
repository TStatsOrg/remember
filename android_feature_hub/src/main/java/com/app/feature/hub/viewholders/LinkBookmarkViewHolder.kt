package com.app.feature.hub.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.hub.databinding.ViewLinkBookmarkBinding
import com.app.shared.utils.MLogger
import com.app.views.viewstate.BookmarkViewState

class LinkBookmarkViewHolder(
    private val binding: ViewLinkBookmarkBinding,
    private val loader: AndroidImageLoader
): BookmarkViewHolder<BookmarkViewState.Link>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Link) = with(viewState) {
        binding.bookmarkInfo.text = info
        binding.bookmarkTitle.text = title
        binding.bookmarkTopic.text = topic
        loader.load(icon, binding.bookmarkIcon)
        binding.bookmarkIcon.visibility = iconVisibility

        binding.root.setOnClickListener {
            listener?.onLinkClick(url = viewState.destinationUrl)
        }

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}