package com.app.feature.preview.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.preview.databinding.ViewLinkPreviewBinding
import com.app.views.viewstate.BookmarkViewState

class LinkPreviewViewHolder(
    private val binding: ViewLinkPreviewBinding,
    private val loader: AndroidImageLoader
): PreviewViewHolder<BookmarkViewState.Link>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Link) = with(viewState) {
        binding.bookmarkSaveDate.text = date
        binding.bookmarkTitle.text = title
        binding.bookmarkSource.text = source
        loader.load(icon, binding.previewIcon)
        binding.previewIcon.visibility = iconVisibility
    }
}