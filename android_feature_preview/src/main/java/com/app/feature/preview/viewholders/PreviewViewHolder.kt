package com.app.feature.preview.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BookmarkViewHolder<ViewState: BookmarkViewState>(initialView: View): RecyclerView.ViewHolder(initialView) {

    var listener: Listener? = null

    abstract fun redraw(viewState: ViewState)

    interface Listener {
        fun onTopicClick(viewState: BookmarkViewState)
        fun onLongClick(viewState: BookmarkViewState)
    }
}