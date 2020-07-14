package com.app.shared.business

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.FeedItemDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class Bookmark: Actions() {

        sealed class Preview: Bookmark() {
            object Start: Preview()
            data class Present(val dto: BookmarkDTO): Preview()
        }

        data class Add(val dto: BookmarkDTO): Bookmark()

        sealed class Load: Bookmark() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val bookmarks: List<BookmarkDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        data class Search(val term: String): Bookmark()

        data class Edit(val bookmarkId: Int): Bookmark()
        data class Update(val state: BookmarkState): Bookmark()

        data class Filter(val topic: TopicState): Bookmark()

        data class Delete(val bookmarkId: Int): Bookmark()

        sealed class Favourite: Bookmark() {
            data class Add(val bookmarkId: Int): Favourite()
            data class Remove(val bookmarkId: Int): Favourite()
        }

        @Deprecated(message = "Name as something else")
        data class UpdateDate(val id: Int, val lastUpdate: Long): Bookmark()

        @Deprecated(message = "Name as something else")
        data class MarkDate(val id: Int): Bookmark()
    }

    sealed class Topics: Actions() {

        sealed class Load: Topics() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val topics: List<TopicDTO>): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }

        data class Add(val topic: TopicDTO): Topics()

        data class Edit(val topicId: Int): Topics()

        data class Update(val topicId: Int, val newName: String): Topics()

        data class Delete(val topicId: Int): Topics()
    }

    sealed class Feed: Actions() {

        sealed class Detail: Feed() {
            data class Present(val dto: BookmarkDTO.FeedBookmarkDTO): Detail()
            sealed class LoadItems: Detail() {
                object Start: LoadItems()
                data class Success(val items: List<FeedItemDTO>): LoadItems()
                data class Error(val error: Throwable): LoadItems()
            }
        }
    }

    sealed class Display: Actions() {
        sealed class Load: Display() {
            data class Start(val time: Long): Load()
            data class Success(val time: Long, val url: String, val title: String?, val caption: String?, val icon: String?): Load()
            data class Error(val time: Long, val error: Throwable): Load()
        }
    }
}