package com.app.shared.business

import com.app.shared.redux.Reducer

val AppStateReducer: Reducer<AppState> = { old, action ->
    when (action) {
        is Actions.Preview.Reset -> old.copy(preview = null)
        is Actions.Preview.Text -> old.copy(preview = Bookmark.Text(value = action.content))
        is Actions.Preview.Link -> old.copy(preview = Bookmark.Link(url = action.url))
        is Actions.SaveBookmark -> {
            val oldArray = old.bookmarks.toMutableList()

            if (old.preview != null) {
                oldArray.add(old.preview)
            }

            old.copy(bookmarks = oldArray)
        }
        else -> old
    }
}