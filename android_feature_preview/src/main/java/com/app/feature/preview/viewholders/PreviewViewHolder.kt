package com.app.feature.preview.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.feature.preview.PreviewViewState

abstract class PreviewViewHolder<ViewState: PreviewViewState>(initialView: View): RecyclerView.ViewHolder(initialView) {
    abstract fun redraw(viewState: ViewState)
}