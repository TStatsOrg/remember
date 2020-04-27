package com.app.feature.preview.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.preview.PreviewViewState
import com.app.feature.preview.databinding.ViewLinkPreviewBinding

class LinkPreviewViewHolder(
    private val binding: ViewLinkPreviewBinding,
    private val loader: AndroidImageLoader
): PreviewViewHolder<PreviewViewState.Link>(binding.root) {

    override fun redraw(viewState: PreviewViewState.Link) = with(viewState) {
        binding.bookmarkSaveDate.text = date
        binding.bookmarkTitle.text = title
        binding.bookmarkSource.text = source
    }
}