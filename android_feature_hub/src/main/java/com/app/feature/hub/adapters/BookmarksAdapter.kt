package com.app.feature.hub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.feature.hub.viewstates.BookmarksViewState
import com.app.feature.hub.databinding.ViewImageBookmarkBinding
import com.app.feature.hub.databinding.ViewLinkBookmarkBinding
import com.app.feature.hub.databinding.ViewTextBookmarkBinding
import com.app.feature.hub.viewholders.BookmarkViewHolder
import com.app.feature.hub.viewholders.ImageBookmarkViewHolder
import com.app.feature.hub.viewholders.LinkBookmarkViewHolder
import com.app.feature.hub.viewholders.TextBookmarkViewHolder
import com.app.views.viewstate.BookmarkViewState

class BookmarksAdapter(private val imageLoader: AndroidImageLoader): RecyclerView.Adapter<BookmarkViewHolder<*>>() {

    var listener: BookmarkViewHolder.Listener? = null

    private var viewState: List<BookmarkViewState> = listOf()
        set(value) {
            val result = DiffUtil.calculateDiff(
                BookmarkDif(
                    field,
                    value
                )
            )
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            2 -> LinkBookmarkViewHolder(binding = ViewLinkBookmarkBinding.inflate(inflater, parent, false), loader = imageLoader)
            1 -> ImageBookmarkViewHolder(binding = ViewImageBookmarkBinding.inflate(inflater, parent, false), loader = imageLoader)
            else -> TextBookmarkViewHolder(binding = ViewTextBookmarkBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int = viewState.size

    override fun onBindViewHolder(holder: BookmarkViewHolder<*>, position: Int) {
        (holder as? BookmarkViewHolder<BookmarkViewState>)?.redraw(viewState[position])
    }

    override fun onViewAttachedToWindow(holder: BookmarkViewHolder<*>) {
        super.onViewAttachedToWindow(holder)
        (holder as? BookmarkViewHolder<BookmarkViewState>)?.listener = listener
    }

    override fun onViewDetachedFromWindow(holder: BookmarkViewHolder<*>) {
        super.onViewDetachedFromWindow(holder)
        (holder as? BookmarkViewHolder<BookmarkViewState>)?.listener = null
    }

    fun redraw(viewState: BookmarksViewState) {
        this.viewState = viewState.bookmarksViewState
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