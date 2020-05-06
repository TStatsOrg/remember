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
        is Actions.Bookmark.Load.Start -> old.copy(bookmarks = BookmarksState(date = action.time))
        is Actions.Bookmark.Load.Success -> old.copy(bookmarks = old.bookmarks.copy(date = action.time, bookmarks = action.bookmarks.toBookmarkState()))
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = old.bookmarks.copy(error = action.error))
        // bookmark/suggestions
        is Actions.Bookmark.Suggestions.Load -> old.copy(bookmarks = old.bookmarks.copy(suggestions = action.suggestions.toTopicState()))
        is Actions.Bookmark.Suggestions.Clear -> old.copy(bookmarks = old.bookmarks.copy(suggestions = listOf()))
        // bookmarks/display
        is Actions.Bookmark.Display.Link -> old.copy(display = DisplayBookmarkState(link = action.dto.toState()))
        // bookmark/update
        is Actions.Bookmark.Update -> {

            val newBookmarks = old.bookmarks.bookmarks.map { if (it.id == action.state.id) action.state else it }

            old.copy(bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                editBookmark = old.editBookmark?.copy(bookmark = action.state))
        }
        // bookmark/edit
        is Actions.Bookmark.Edit -> {
            val selectedBookmarkFromExisting = old.bookmarks.bookmarks.firstOrNull { it.id == action.bookmarkId }
            val selectedBookmarkFromPreview = old.preview
            val selectedBookmark = selectedBookmarkFromExisting ?: selectedBookmarkFromPreview
            val allTopics = old.topics.topics
            val newEditBookmarkState = selectedBookmark?.let {
                EditBookmarkState(bookmark = it, topics = allTopics)
            }

            old.copy(editBookmark = newEditBookmarkState)
        }
        // topics/present
        is Actions.Topics.Load.Start -> old.copy(topics = TopicsState(date = action.time, isLoading = true))
        is Actions.Topics.Load.Success -> old.copy(topics = TopicsState(date = action.time, topics = action.topics.toTopicState()))
        is Actions.Topics.Load.Error -> old.copy(topics = TopicsState(date = action.time, error = action.error))
        // topics/add
        is Actions.Topics.Add -> {
            val existingTopics = old.topics.topics
            val newTopic = listOf(action.topic.toState())
            val newTopics = (newTopic + existingTopics).distinctBy { it.id }

            old.copy(topics = TopicsState(topics = newTopics),
                editBookmark = old.editBookmark?.copy(topics = newTopics))
        }
        else -> old
    }
}