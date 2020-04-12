package com.app.shared.business

import com.app.shared.redux.Reducer
import com.app.shared.utils.toState

val AppStateReducer: Reducer<AppState> = { old, action ->
    when (action) {
        is Actions.Bookmark.Preview.Reset -> old.copy(preview = null)
        is Actions.Bookmark.Preview.Text -> old.copy(preview = BookmarkState.Text(id = action.content.hashCode(), value = action.content))
        is Actions.Bookmark.Preview.Link -> old.copy(preview = BookmarkState.Link(id = action.url.hashCode(), url = action.url))
        is Actions.Bookmark.Load -> old.copy(bookmarks = old.bookmarks.copy(time = action.time))
        is Actions.Bookmark.Loaded -> old.copy(bookmarks = BookmarksState(bookmarks = action.bookmarks, time = action.time))
        else -> old
    }
}

val AppState2Reducer: Reducer<AppState2> = { old, action ->
    when (action) {
        is Actions2.Bookmark.Preview.Reset -> old.copy(preview = null)
        is Actions2.Bookmark.Preview.Present -> old.copy(preview = action.dto.toState())
        is Actions2.Bookmark.Load.Start -> old.copy(bookmarks = SavedBookmarksState(date = action.time))
        is Actions2.Bookmark.Load.Success -> old.copy(bookmarks = SavedBookmarksState(date = action.time, bookmarks = action.bookmarks.toState()))
        is Actions2.Bookmark.Load.Error -> old.copy(bookmarks = old.bookmarks.copy(error = action.error))
        else -> old
    }
}