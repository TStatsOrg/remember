package com.app.feature.hub.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.feature.hub.BookmarkViewState

abstract class BookmarkViewHolder<ViewState: BookmarkViewState>(initialView: View): RecyclerView.ViewHolder(initialView) {
    abstract fun redraw(viewState: ViewState)
}