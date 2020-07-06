package com.app.shared.feature.bookmarks

import com.app.shared.business.Actions
import com.app.shared.business.BookmarksState
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.launch

class SharedBookmarksViewModel(
    private val store: Store<MainState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository
): BookmarksViewModel {

    private val scope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadBookmarks() {
        scope.launch(context = DispatcherFactory.main()) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load().filter { it !is BookmarkDTO.RSSFeedBookmarkDTO }

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }

    override fun search(byName: String) {
        scope.launch(context = DispatcherFactory.main()) {
            store.dispatch(action = Actions.Bookmark.Search(term = byName))
        }
    }

    override fun delete(bookmarkId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

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