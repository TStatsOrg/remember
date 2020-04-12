package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.Bookmark
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SharedMainHubViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository
): MainHubViewModel {

    private val scope = provideViewModelScope()

    override fun loadBookmarks() {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Bookmark.Load(time = calendar.getTime()))

            val dtos = bookmarkRepository.getAll()
            val bookmarks: List<Bookmark> = dtos.mapNotNull {
                when(it.type) {
                    BookmarkDTO.Type.Text -> Bookmark.Text(id = it.id, value = it.content)
                    BookmarkDTO.Type.Link -> Bookmark.Link(id = it.id, url = it.content)
                    BookmarkDTO.Type.Unknown -> null
                }
            }

            store.dispatch(action = Actions.Bookmark.Loaded(time = calendar.getTime(), bookmarks = bookmarks))
        }
    }

    override fun observeBookmarkState(callback: (List<Bookmark>) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(DefaultDispatcher)
                .map { it.bookmarks.bookmarks }
                .collect {
                    callback(it)
                }
        }
    }
}