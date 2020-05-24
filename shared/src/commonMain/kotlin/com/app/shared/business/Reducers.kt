package com.app.shared.business

import com.app.shared.data.dto.TopicDTO
import com.app.shared.redux.Reducer
import com.app.shared.utils.copy
import com.app.shared.utils.toState
import com.app.shared.utils.toTopicState
import com.app.shared.utils.toBookmarkState

val AppStateReducer: Reducer<MainState> = { old, action ->
    when (action) {
        // bookmark/preview
        is Actions.Bookmark.Preview.Reset -> old.copy(preview = PreviewState())
        is Actions.Bookmark.Preview.Start -> old.copy(preview = old.preview.copy(isLoading = true))
        is Actions.Bookmark.Preview.Present -> old.copy(preview = old.preview.copy(isLoading = false, preview = action.dto.toState()))
        // bookmark/present
        is Actions.Bookmark.Load.Start -> old.copy(bookmarks = BookmarksState(date = action.time))
        is Actions.Bookmark.Load.Success -> old.copy(bookmarks = BookmarksState(date = action.time, bookmarks = action.bookmarks.toBookmarkState()))
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = BookmarksState(error = action.error))
        // bookmark/filter
        is Actions.Bookmark.Filter -> old.copy(bookmarks = BookmarksState(filterByTopic = action.topic, bookmarks = action.bookmarks.toBookmarkState()))
        // bookmark/search
        is Actions.Bookmark.Search -> old.copy(bookmarks = old.bookmarks.copy(searchTerm = action.term, bookmarks = action.results.toBookmarkState()))
        // bookmark/update
        is Actions.Bookmark.Update -> {

            val newBookmarks = old.bookmarks.bookmarks.map { if (it.id == action.state.id) action.state else it }

            old.copy(bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                editBookmark = old.editBookmark?.copy(bookmark = action.state))
        }
        // bookmark/edit
        is Actions.Bookmark.Edit -> {
            val selectedBookmarkFromExisting = old.bookmarks.bookmarks.firstOrNull { it.id == action.bookmarkId }
            val selectedBookmarkFromPreview = old.preview.preview
            val selectedBookmark = selectedBookmarkFromExisting ?: selectedBookmarkFromPreview
            val allTopics = old.topics.topics
            val newEditBookmarkState = selectedBookmark?.let {
                EditBookmarkState(bookmark = it, topics = allTopics)
            }

            old.copy(editBookmark = newEditBookmarkState)
        }
        // bookmark/delete
        is Actions.Bookmark.Delete -> {
            val newList = old.bookmarks.bookmarks.toMutableList()
            newList.removeAll { it.id == action.bookmarkId }
            old.copy(bookmarks = old.bookmarks.copy(bookmarks = newList.toList()))
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
        // topics/delete
        is Actions.Topics.Delete -> {
            val newTopics = old.topics.topics.filter { it.id != action.topicId }
            val newEditableTopics = old.editBookmark?.topics?.filter { it.id != action.topicId } ?: listOf()
            val newBookmarks = old.bookmarks.bookmarks.map {
                if (it.topic?.id == action.topicId) {
                    it.copy(withTopic = TopicDTO.GeneralTopic().toState())
                } else {
                    it
                }
            }

            old.copy(
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                topics = old.topics.copy(topics = newTopics),
                editBookmark = old.editBookmark?.copy(topics = newEditableTopics)
            )
        }
        // topics/edit
        is Actions.Topics.Edit -> {
            val topicToEdit = old.topics.topics.firstOrNull { it.id == action.topicId }
            topicToEdit?.let { old.copy(editTopic = EditTopicState(topic = it)) } ?: old
        }
        // topics/update
        is Actions.Topics.Update -> {
            val copyFunc: (TopicState) -> TopicState = {
                if (it.id == action.topicId) {
                    it.copy(name = action.newName)
                } else {
                    it
                }
            }

            val newTopics = old.topics.topics.map(copyFunc)
            val newSelected = old.editBookmark?.topics?.map(copyFunc) ?: listOf()
            val selectedTopic = newTopics.firstOrNull { it.id == action.topicId }
            val newBookmarks = old.bookmarks.bookmarks.map {
                if (it.topic?.id == selectedTopic?.id) {
                    it.copy(withTopic = selectedTopic)
                } else {
                    it
                }
            }

            old.copy(
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                topics = old.topics.copy(topics = newTopics),
                editBookmark = old.editBookmark?.copy(topics = newSelected)
            )
        }
        else -> old
    }
}