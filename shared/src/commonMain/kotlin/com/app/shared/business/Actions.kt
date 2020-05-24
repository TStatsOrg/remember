package com.app.shared.business

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class Bookmark: Actions() {

        sealed class Preview: Bookmark() {
            object Reset: Preview()
            object Start: Preview()
            data class Present(val dto: BookmarkDTO): Preview()
        }

        data class Save(val dto: BookmarkDTO): Bookmark()

        sealed class Load: Bookmark() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val bookmarks: List<BookmarkDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        data class Search(val term: String, val results: List<BookmarkDTO>): Bookmark()

        data class Edit(val bookmarkId: Int): Bookmark()
        data class Update(val state: BookmarkState): Bookmark()

        data class Filter(val topic: TopicState, val bookmarks: List<BookmarkDTO>): Bookmark()

        data class Delete(val bookmarkId: Int): Bookmark()
    }

    sealed class Topics: Actions() {

        sealed class Load: Topics() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val topics: List<TopicDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        data class Add(val topic: TopicDTO): Topics()

        data class Delete(val topicId: Int): Topics()

        data class Edit(val topicId: Int): Topics()

        data class Update(val topicId: Int, val newName: String): Topics()
    }
}