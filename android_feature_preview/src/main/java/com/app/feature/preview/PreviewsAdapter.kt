package com.app.feature.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.preview.databinding.ViewImagePreviewBinding
import com.app.feature.preview.databinding.ViewLinkPreviewBinding
import com.app.feature.preview.databinding.ViewTextPreviewBinding
import com.app.feature.preview.viewholders.ImagePreviewViewHolder
import com.app.feature.preview.viewholders.LinkPreviewViewHolder
import com.app.feature.preview.viewholders.PreviewViewHolder
import com.app.feature.preview.viewholders.TextPreviewViewHolder
import com.app.views.viewstate.BookmarkViewState

class PreviewsAdapter(private val imageLoader: AndroidImageLoader): RecyclerView.Adapter<PreviewViewHolder<*>>() {

    private var viewState: List<BookmarkViewState> = listOf()
        set(value) {
            val result = DiffUtil.calculateDiff(BookmarkDif(field, value))
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun getItemViewType(position: Int): Int {
        return when (viewState[position]) {
            is BookmarkViewState.Text -> 0
            is BookmarkViewState.Image -> 1
            is BookmarkViewState.Link -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            2 -> LinkPreviewViewHolder(binding = ViewLinkPreviewBinding.inflate(inflater, parent, false), loader = imageLoader)
            1 -> ImagePreviewViewHolder(binding = ViewImagePreviewBinding.inflate(inflater, parent, false), loader = imageLoader)
            else -> TextPreviewViewHolder(binding = ViewTextPreviewBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int = viewState.size

    override fun onBindViewHolder(holder: PreviewViewHolder<*>, position: Int) {
        (holder as? PreviewViewHolder<BookmarkViewState>)?.redraw(viewState[position])
    }

    fun redraw(viewState: PreviewsViewState) {
        this.viewState = viewState.viewStates
    }

    internal class BookmarkDif(
        private val oldContent: List<BookmarkViewState>,
        private val newContent: List<BookmarkViewState>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldContent.size
        override fun getNewListSize(): Int = newContent.size
        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos].id == newContent[newPos].id
        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = oldContent[oldPos] == newContent[newPos]
    }
}