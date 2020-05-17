package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.AppState
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
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository
): MainHubViewModel {

    private val scope = provideViewModelScope()
    private val storeObserver = store.observe()

    init {
        MLogger.log("GABBOX2: Main hub observer $storeObserver\n")
    }

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

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = searched))
        }
    }

    override fun observeBookmarkState(callback: (BookmarksState) -> Unit) {
        storeObserver
            .map { it.bookmarks }
            .collect(callback)
    }

    override fun cleanup() {
        MLogger.log("GABBOX2: Removing main hub observer $storeObserver\n")
        store.remove(observer = storeObserver)
    }
}