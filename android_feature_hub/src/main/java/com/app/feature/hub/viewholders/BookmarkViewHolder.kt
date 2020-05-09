package com.app.feature.hub.viewholders

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.views.viewstate.BookmarkViewState

abstract class BookmarkViewHolder<ViewState: BookmarkViewState>(initialView: View): RecyclerView.ViewHolder(initialView) {

    var listener: Listener? = null

    abstract fun redraw(viewState: ViewState)

    interface Listener {
        fun onLinkClick(url: Uri?)
        fun onLongClick(viewState: BookmarkViewState)
    }
}