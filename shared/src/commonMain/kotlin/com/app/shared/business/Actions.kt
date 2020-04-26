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

        sealed class Update : Bookmark() {
            data class Topic(val bookmarkId: Int, val topicId: Int): Update()
        }
    }

    sealed class Topics: Actions() {

        sealed class Load: Topics() {
            data class Start(val time: Long): Load()
            sealed class Success: Load() {
                data class ForViewing(val time: Long, val topics: List<TopicDTO>): Success()
                data class ForEditing(val time: Long, val topics: List<TopicDTO>, val bookmarkId: Int): Success()
            }
            data class Error(val time: Long, val error: Throwable): Load()
        }
    }
}