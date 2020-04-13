package com.app.shared.feature.preview

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarkState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.DataProcess
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.toDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class SharedPreviewViewModel(
    private val store: Store<AppState>,
    private val bookmarkRepository: BookmarkRepository,
    private val calendarUtils: CalendarUtils
): PreviewViewModel {

    override var delegate: PreviewViewModel.Delegate? = null

    private val scope: CoroutineScope = provideViewModelScope()

    override fun clear() = store.dispatch(action = Actions.Bookmark.Preview.Reset)

    override fun present(processed: DataProcess.Item) {
        scope.launch(context = MainDispatcher) {
            val bookmarkDTO = processed.toDTO(date = calendarUtils.getTime())

            if (bookmarkDTO != null) {
                store.dispatch(action = Actions.Bookmark.Preview.Present(dto = bookmarkDTO))
            }
        }
    }

    override fun save(processed: DataProcess.Item) {
        scope.launch(context = MainDispatcher) {
            val bookmarkDTO = processed.toDTO(date = calendarUtils.getTime())

            if (bookmarkDTO != null) {
                bookmarkRepository.save(dto = bookmarkDTO)
                delegate?.didSaveBookmark()
            }
        }
    }

    override fun observePreviewState(callback: (BookmarkState) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(context = DefaultDispatcher)
                .mapNotNull { it.preview }
                .collect {
                    callback(it)
                }
        }
    }
}