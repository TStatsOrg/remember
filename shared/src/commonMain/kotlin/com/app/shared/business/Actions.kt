package com.app.shared.business

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class Bookmark: Actions() {

        sealed class Preview: Bookmark() {
            object Reset: Preview()
            data class Present(val dto: BookmarkDTO): Preview()
        }

        sealed class Load: Bookmark() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val bookmarks: List<BookmarkDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        sealed class Suggestions: Bookmark() {
            data class Load(val suggestions: List<TopicDTO>): Suggestions()
            object Clear: Suggestions()
        }

        data class Edit(val bookmarkId: Int): Bookmark()
        data class Update(val state: BookmarkState): Bookmark()

        sealed class Display: Bookmark() {
            data class Link(val dto: BookmarkDTO.LinkBookmarkDTO): Display()
        }
    }

    sealed class Topics: Actions() {

        sealed class Load: Topics() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val topics: List<TopicDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        data class Add(val topic: TopicDTO): Topics()
    }
}