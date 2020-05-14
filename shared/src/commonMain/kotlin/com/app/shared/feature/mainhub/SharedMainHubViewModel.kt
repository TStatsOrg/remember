package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarksState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.redux.toEmitter
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.launch

class SharedMainHubViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository,
    private val topicsRepository: TopicsRepository
): MainHubViewModel {

    private val scope = provideViewModelScope()

    override fun loadBookmarks() {
        scope.launch(context = MainDispatcher) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }

    override fun loadSuggestions() {
        scope.launch(context = MainDispatcher) {
            val dtos = topicsRepository.load()

            store.dispatch(action = Actions.Bookmark.Suggestions.Load(suggestions = dtos))
        }
    }

    override fun clearSuggestions() {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Bookmark.Suggestions.Clear)
        }
    }

    override fun filterSuggestions(byName: String) {
        scope.launch(context = MainDispatcher) {
            // start
            val dtos = topicsRepository.load()
            val filtered = dtos.filter { it.name.contains(byName, ignoreCase = true) }
            store.dispatch(action = Actions.Bookmark.Suggestions.Load(suggestions = filtered))
        }
    }

    override fun filter(byTopic: String) {
        scope.launch(context = MainDispatcher) {
            val dtos = bookmarkRepository.load()

            val filtered = dtos.filter { it.topic?.name.equals(byTopic, ignoreCase = true) }

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = filtered))
        }
    }

    override fun search(byName: String) {
        scope.launch(context = MainDispatcher) {
            val dtos = bookmarkRepository.load()

            val searched = dtos.filter {
                return@filter when (it) {
                    is BookmarkDTO.LinkBookmarkDTO -> it.title?.contains(byName, ignoreCase = true) ?: false
                    is BookmarkDTO.TextBookmarkDTO -> it.text.contains(byName, ignoreCase = true)
                    is BookmarkDTO.ImageBookmarkDTO -> it.topic?.name?.contains(byName, ignoreCase = true) ?: false
                    else -> false
                }
            }

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = searched))
        }
    }

    override fun observeBookmarkState(callback: (BookmarksState) -> Unit) {
        store.toEmitter().observer()
            .map { it.bookmarks }
            .collect(callback)
    }
}