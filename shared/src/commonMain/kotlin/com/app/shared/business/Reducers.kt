package com.app.shared.business

import com.app.shared.redux.Reducer
import com.app.shared.utils.copy
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
        is Actions.Bookmark.Load.Success -> old.copy(bookmarks = BookmarksState(date = action.time, bookmarks = action.bookmarks.toBookmarkState()))
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = old.bookmarks.copy(error = action.error))
        // bookmark/update
        is Actions.Bookmark.Update.Topic -> {
            val newTopic = old.topics.topics.firstOrNull { it.id == action.topicId }
            val newBookmarks = old.bookmarks.bookmarks.map {
                if (it.id == action.bookmarkId) {
                    it.copy(withTopic = newTopic)
                } else {
                    it
                }
            }

            old.copy(bookmarks = old.bookmarks.copy(bookmarks = newBookmarks))
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

            old.copy(topics = TopicsState(topics = newTopics))
        }
        else -> old
    }
}