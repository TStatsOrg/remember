package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.business.BookmarksState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.MLogger
import kotlinx.coroutines.launch

class SharedMainHubViewModel(
        private val store: Store<MainState>,
        private val calendar: CalendarUtils,
        private val bookmarkRepository: BookmarkRepository
): MainHubViewModel {

    private val scope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadBookmarks() {
        scope.launch(context = MainDispatcher) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
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

            store.dispatch(action = Actions.Bookmark.Search(term = byName, results = searched))
        }
    }

    override fun delete(bookmarkId: Int) {
        scope.launch(context = MainDispatcher) {

            bookmarkRepository.delete(bookmarkId = bookmarkId)
            store.dispatch(action = Actions.Bookmark.Delete(bookmarkId = bookmarkId))
        }
    }

    override fun observeBookmarkState(callback: (BookmarksState) -> Unit) {
        storeObserver
            .map { it.bookmarks }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}