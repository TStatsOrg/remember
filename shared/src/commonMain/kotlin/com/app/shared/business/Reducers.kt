package com.app.shared.business

import com.app.shared.redux.Reducer
import com.app.shared.utils.toState

val AppStateReducer: Reducer<AppState> = { old, action ->
    when (action) {
        is Actions.Bookmark.Preview.Reset -> old.copy(preview = null)
        is Actions.Bookmark.Preview.Present -> old.copy(preview = action.dto.toState())
        is Actions.Bookmark.Load.Start -> old.copy(bookmarks = SavedBookmarksState(date = action.time))
        is Actions.Bookmark.Load.Success -> old.copy(bookmarks = SavedBookmarksState(date = action.time, bookmarks = action.bookmarks.toState()))
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = old.bookmarks.copy(error = action.error))
        else -> old
    }
}