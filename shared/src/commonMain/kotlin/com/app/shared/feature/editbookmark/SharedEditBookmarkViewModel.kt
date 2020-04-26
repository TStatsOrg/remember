package com.app.shared.feature.editbookmark

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.EditBookmarkState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.copy
import com.app.shared.utils.toDTO
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SharedEditBookmarkViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository,
    private val topicsRepository: TopicsRepository
): EditBookmarkViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    private var callback: (() -> Unit)? = null

    override fun loadEditableBookmark(forId: Int) {
        scope.launch(context = MainDispatcher) {

            // in case topics are empty, pre-populate them
            if (store.state.topics.topics.isEmpty()) {
                val topics = topicsRepository.load()
                store.dispatch(action = Actions.Topics.Load.Success(topics = topics, time = calendar.getTime()))
            }

            // and then set the editable state correctly
            store.dispatch(action = Actions.Bookmark.Edit(bookmarkId = forId))
        }
    }

    override fun update(bookmark: Int, withTopic: Int) {
        scope.launch(context = MainDispatcher) {

            val bookmarkToEdit = store.state.bookmarks.bookmarks.first { it.id == bookmark }
            val newTopic = store.state.topics.topics.first { it.id == withTopic }
            val newState = bookmarkToEdit.copy(withTopic = newTopic)
            val newDto = newState.toDTO()

            newDto?.let {
                bookmarkRepository.save(dto = newDto)
                store.dispatch(action = Actions.Bookmark.Update(state = newState))
                callback?.invoke()
            }
        }
    }

    override fun observeEditBookmarkState(callback: (EditBookmarkState) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(DefaultDispatcher)
                .map { it.editBookmark }
                .filterNotNull()
                .collect {
                    callback(it)
                }
        }
    }

    override fun observeBookmarkUpdated(callback: () -> Unit) {
        this.callback = callback
    }
}