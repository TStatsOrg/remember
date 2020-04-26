package com.app.shared.feature.preview

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarkState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.ObservableEmitter
import com.app.shared.observ.filterNotNull
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.redux.toEmitter
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.toDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedPreviewViewModel(
    private val store: Store<AppState>,
    private val bookmarkRepository: BookmarkRepository,
    private val calendar: CalendarUtils,
    private val processor: RawDataProcess
): PreviewViewModel {

    private var temporaryDTO: BookmarkDTO? = null

    private val scope: CoroutineScope = provideViewModelScope()
    private val emitter = ObservableEmitter<Int>()

    override fun clear() = store.dispatch(action = Actions.Bookmark.Preview.Reset)

    override fun present(capturedRawData: String?) {
        scope.launch(context = MainDispatcher) {

            // do this on a secondary thread
            val data = withContext(context = DefaultDispatcher) {
                return@withContext processor.process(capture = capturedRawData)
            }

            // transform to DTO
            val dto = data.toDTO(date = calendar.getTime(), topic = TopicDTO.GeneralTopic())

            // send action
            if (dto != null) {
                temporaryDTO = dto
                store.dispatch(action = Actions.Bookmark.Preview.Present(dto = dto))
            }
        }
    }

    override fun save() {
        scope.launch(context = MainDispatcher) {
            temporaryDTO?.let {
                bookmarkRepository.save(dto = it)
                emitter.emit(value = it.id)
            }
        }
    }

    override fun observePreviewState(callback: (BookmarkState) -> Unit) {
        store.toEmitter()
            .observer()
            .map { it.preview }
            .filterNotNull()
            .collect(callback)
    }

    override fun observeBookmarkSaved(callback: (Int) -> Unit) {
        emitter.observer().collect(callback)
    }
}