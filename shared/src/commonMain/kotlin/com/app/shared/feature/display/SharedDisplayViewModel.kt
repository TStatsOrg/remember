package com.app.shared.feature.display

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarkState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.filterNotNull
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.redux.toEmitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedDisplayViewModel(
    private val store: Store<AppState>,
    private val bookmarksRepository: BookmarkRepository
): DisplayViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun displayLink(bookmarkId: Int) {
        scope.launch(context = MainDispatcher) {
            val dto = bookmarksRepository.loadLink(id = bookmarkId)

            dto?.let {
                store.dispatch(action = Actions.Bookmark.Display.Link(dto = dto))
            }
        }
    }

    override fun observeDisplayLinkState(callback: (BookmarkState.Link) -> Unit) {
        store.toEmitter().observer()
            .map { it.display }
            .map { it.link }
            .filterNotNull()
            .collect(callback)
    }
}