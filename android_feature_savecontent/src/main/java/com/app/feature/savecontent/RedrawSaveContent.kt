package com.app.feature.savecontent

import com.app.feature.savecontent.databinding.ViewSharecontentBinding

val Redraw: (ViewSharecontentBinding, SaveContentViewState) -> Unit = { binding, state ->
    binding.textContent.text = state.resource
}