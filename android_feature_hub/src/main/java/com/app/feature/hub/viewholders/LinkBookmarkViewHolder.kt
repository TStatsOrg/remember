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
        binding.bookmarkSaveDate.text = date
        binding.bookmarkTitle.text = title
        binding.bookmarkSource.text = source
        binding.bookmarkTopic.text = topic
        loader.load(icon, binding.bookmarkIcon)
        binding.bookmarkIcon.visibility = iconVisibility

        binding.root.setOnClickListener {
            MLogger.log("View State $viewState")
            listener?.onLinkClick(url = viewState.destinationUrl)
        }

        binding.bookmarkTopic.setOnClickListener {
            listener?.onTopicClick(viewState = viewState)
        }

        binding.root.setOnLongClickListener {
            listener?.onLongClick(viewState = viewState)
            true
        }
    }
}