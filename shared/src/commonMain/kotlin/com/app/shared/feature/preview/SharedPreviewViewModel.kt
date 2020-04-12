package com.app.shared.feature.preview

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarkState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.DataCapture
import com.app.shared.data.capture.DataProcess
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.MLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class SharedPreviewViewModel(
    private val store: Store<AppState>,
    private val bookmarkRepository: BookmarkRepository,
    private val process: DataProcess
): PreviewViewModel {

    override var delegate: PreviewViewModel.Delegate? = null

    private val scope: CoroutineScope = provideViewModelScope()

    override fun clear() = store.dispatch(action = Actions.Bookmark.Preview.Reset)

    override fun capture(capture: DataCapture.Item) {
        scope.launch(context = MainDispatcher) {
            val result = process.process(capture = capture)

            MLogger.log(message = "Result is $result")
        }
    }

    override fun handle(previewData: PreviewData) {
        scope.launch(context = MainDispatcher) {
            when (val unboxed = previewData.unbox()) {
                is PreviewDataType.Text -> store.dispatch(action = Actions.Bookmark.Preview.Text(content = unboxed.content))
                is PreviewDataType.Link -> store.dispatch(action = Actions.Bookmark.Preview.Link(url = unboxed.url))
                is PreviewDataType.Unsupported -> Unit
            }
        }
    }

    override fun save(previewData: PreviewData) {
        scope.launch(context = MainDispatcher) {

            val dto = when (val unboxed = previewData.unbox()) {
                is PreviewDataType.Text -> object : BookmarkDTO {
                    override val id: Int = unboxed.content.hashCode()
                    override val content: String = unboxed.content
                    override val type: BookmarkDTO.Type = BookmarkDTO.Type.Text
                }
                is PreviewDataType.Link -> object : BookmarkDTO {
                    override val id: Int = unboxed.url.hashCode()
                    override val content: String = unboxed.url
                    override val type: BookmarkDTO.Type = BookmarkDTO.Type.Link
                }
                else -> null
            }

            if (dto != null) {
                bookmarkRepository.save(bookmark = dto)
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