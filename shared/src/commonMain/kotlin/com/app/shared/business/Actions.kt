package com.app.shared.business

import com.app.shared.data.dto.Bookmark2DTO
import com.app.shared.redux.Action

sealed class Actions2: Action {
    sealed class Bookmark: Actions2() {

        sealed class Preview: Bookmark() {
            object Reset: Preview()
            data class Present(val dto: Bookmark2DTO): Preview()
        }

        sealed class Load: Bookmark() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val bookmarks: List<Bookmark2DTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }
    }
}

sealed class Actions: Action {

    sealed class Bookmark: Actions() {

        sealed class Preview: Bookmark() {
            object Reset: Preview()
            data class Text(val content: String): Preview()
            data class Link(val url: String): Preview()
        }

        data class Load(val time: Long): Bookmark()

        data class Loaded(val time: Long, val bookmarks: List<BookmarkState>): Bookmark()
    }
}