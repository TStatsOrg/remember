package com.app.feature.preview.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.preview.databinding.ViewImagePreviewBinding
import com.app.views.viewstate.BookmarkViewState

class ImagePreviewViewHolder(
    private val binding: ViewImagePreviewBinding,
    private val loader: AndroidImageLoader
): PreviewViewHolder<BookmarkViewState.Image>(binding.root) {

    override fun redraw(viewState: BookmarkViewState.Image) = with(viewState) {
        loader.load(url, binding.bookmarkImage)
        binding.bookmarkInfo.text = info
    }
}