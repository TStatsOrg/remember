package com.app.feature.preview.viewholders

import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.preview.PreviewViewState
import com.app.feature.preview.databinding.ViewImagePreviewBinding

class ImagePreviewViewHolder(
    private val binding: ViewImagePreviewBinding,
    private val loader: AndroidImageLoader
): PreviewViewHolder<PreviewViewState.Image>(binding.root) {

    override fun redraw(viewState: PreviewViewState.Image) = with(viewState) {
        loader.load(url, binding.bookmarkImage)
        binding.bookmarkSaveDate.text = date
        binding.bookmarkSource.text = source
    }
}