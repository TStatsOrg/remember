package com.app.feature.hub

import androidx.recyclerview.widget.RecyclerView
import com.app.feature.hub.databinding.ViewBookmarkBinding

class BookmarkViewHolder(
    private val binding: ViewBookmarkBinding
): RecyclerView.ViewHolder(binding.root) {

    fun redraw(viewState: BookmarkViewState) = with(viewState) {
        binding.bookmarkType.text = type
        binding.bookmarkContent.text = content
    }
}