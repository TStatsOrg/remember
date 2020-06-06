package com.app.shared.feature.preview

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.business.PreviewState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.InfiniteEmitter
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.toDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedPreviewViewModel<Input>(
    private val store: Store<MainState>,
    private val bookmarkRepository: BookmarkRepository,
    private val calendar: CalendarUtils,
    private val capture: RawDataCapture<Input>,
    private val process: RawDataProcess
): PreviewViewModel<Input> {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()
    private val emitter = InfiniteEmitter<Int>()

    override fun capture(input: Input) {

        store.dispatch(action = Actions.Bookmark.Preview.Reset)
        store.dispatch(action = Actions.Bookmark.Preview.Start)

        capture.capture(input = input) { capture ->
            process.process(capture = capture) { processedData ->

                // get the DTO from the processed data
                val dto = processedData.toDTO(date = calendar.getTime(), topic = TopicDTO.GeneralTopic())

                // send action
                if (dto != null) {
                    store.dispatch(action = Actions.Bookmark.Preview.Present(dto = dto))
                }
            }
        }
    }

    override fun save() {
        scope.launch(context = DispatcherFactory.main()) {
            store.state.preview.preview?.toDTO()?.let {
                bookmarkRepository.save(dto = it)
                store.dispatch(action = Actions.Bookmark.Add(dto = it))
                emitter.emit(value = it.id)
            }
        }
    }

    override fun observePreviewState(callback: (PreviewState) -> Unit) {
        storeObserver
            .map { it.preview }
            .collect(callback)
    }

    override fun observeBookmarkSaved(callback: (Int) -> Unit) {
        emitter.observe().collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
        emitter.cleanup()
    }
}