package com.app.feature.hub

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.app.feature.hub.databinding.ViewGetStartedBinding
import com.app.feature.hub.viewstates.BookmarksViewState

class GetStartedView: RelativeLayout {

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    init {
        ViewGetStartedBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun redraw(viewState: BookmarksViewState) = with(viewState) {
        visibility = getStartedVisibility
    }
}