package com.app.shared.business

import com.app.shared.redux.Reducer

val AppStateReducer: Reducer<AppState> = { old, action ->
    when (action) {
        is Actions.PreviewContent.Reset -> old.copy(previewContent = null)
        is Actions.PreviewContent.Text -> old.copy(previewContent = SavedContent.Text(value = action.content))
        is Actions.PreviewContent.Link -> old.copy(previewContent = SavedContent.Link(url = action.url))
        is Actions.SaveContent -> {
            val oldArray = old.allSavedContent.toMutableList()

            if (old.previewContent != null) {
                oldArray.add(old.previewContent)
            }

            old.copy(allSavedContent = oldArray)
        }
        else -> old
    }
}