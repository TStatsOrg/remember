package com.app.feature.preview.viewholders

import com.app.feature.preview.PreviewViewState
import com.app.feature.preview.databinding.ViewTextPreviewBinding

class TextPreviewViewHolder(
    private val binding: ViewTextPreviewBinding
): PreviewViewHolder<PreviewViewState.Text>(binding.root) {

    override fun redraw(viewState: PreviewViewState.Text) = with(viewState) {
        binding.bookmarkTitle.text = text
        binding.bookmarkSaveDate.text = date
        binding.bookmarkSource.text = source
    }
}