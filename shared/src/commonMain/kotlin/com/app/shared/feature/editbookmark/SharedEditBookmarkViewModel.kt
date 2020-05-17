package com.app.shared.feature.editbookmark

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.EditBookmarkState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.*
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.MLogger
import com.app.shared.utils.copy
import com.app.shared.utils.toDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedEditBookmarkViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository,
    private val topicsRepository: TopicsRepository
): EditBookmarkViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()
    private val emitter = InfiniteEmitter<Boolean>()

    init {
        MLogger.log("GABBOX2: Shardd edit obserer $storeObserver\n")
    }

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

            val bookmarkToEdit = store.state.editBookmark?.bookmark
            val newTopic = store.state.topics.topics.first { it.id == withTopic }
            val newState = bookmarkToEdit?.copy(withTopic = newTopic)

            newState?.let {
                store.dispatch(action = Actions.Bookmark.Update(state = newState))
            }
        }
    }

    override fun save() {
        scope.launch(context = MainDispatcher) {
            val bookmarkToSave = store.state.editBookmark?.bookmark
            val newDTO = bookmarkToSave?.toDTO()

            newDTO?.let {
                bookmarkRepository.save(dto = newDTO)
                emitter.emit(value = true)
            }
        }
    }

    override fun observeEditBookmarkState(callback: (EditBookmarkState) -> Unit) {
        storeObserver
            .map { it.editBookmark }
            .filterNotNull()
            .collect(callback)
    }

    override fun observeBookmarkSaved(callback: (Boolean) -> Unit) {
        emitter.observe().collect(callback)
    }

    override fun cleanup() {
        MLogger.log("GABBOX2: cleaning up after $storeObserver | ${store.emitter.currentObservers()}\n")
        store.remove(observer = storeObserver)
        MLogger.log("GABBOX2: Cleaned up ${store.emitter.currentObservers()}\n")
        emitter.cleanup()
    }
}