package com.app.feature.preview.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.views.viewstate.BookmarkViewState

abstract class PreviewViewHolder<ViewState: BookmarkViewState>(initialView: View): RecyclerView.ViewHolder(initialView) {
    abstract fun redraw(viewState: ViewState)
}