package com.app.shared.business

import com.app.shared.redux.Reducer
import com.app.shared.utils.toState
import com.app.shared.utils.toTopicState
import com.app.shared.utils.toBookmarkState

val AppStateReducer: Reducer<AppState> = { old, action ->
    when (action) {
        // bookmark/preview
        is Actions.Bookmark.Preview.Reset -> old.copy(preview = null)
        is Actions.Bookmark.Preview.Present -> old.copy(preview = action.dto.toState())
        // bookmark/present
        is Actions.Bookmark.Load.Start -> old.copy(bookmarks = SavedBookmarksState(date = action.time))
        is Actions.Bookmark.Load.Success -> old.copy(bookmarks = SavedBookmarksState(date = action.time, bookmarks = action.bookmarks.toBookmarkState()))
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = old.bookmarks.copy(error = action.error))
        // topics/present
        is Actions.Topics.Load.Start -> old.copy(topics = TopicsState(date = action.time, isLoading = true))
        is Actions.Topics.Load.Success.ForViewing -> old.copy(topics = TopicsState(date = action.time, topics = action.topics.toTopicState()))
        is Actions.Topics.Load.Success.ForEditing -> {

            val bookmarkInEdit = old.bookmarks.bookmarks.firstOrNull { it.id == action.bookmarkId }
            val bookmarkTopicId = bookmarkInEdit?.topic?.id

            old.copy(topics = TopicsState(
                date = action.time,
                topics = action.topics.toTopicState().map { it.copy(isSelected = it.id == bookmarkTopicId) },
                mode = TopicsState.Mode.Editing(bookmarkId = action.bookmarkId)
            ))
        }
        is Actions.Topics.Load.Error -> old.copy(topics = TopicsState(date = action.time, error = action.error))
        else -> old
    }
}