package com.app.shared.feature.preview

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.Bookmark
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class SharedPreviewViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils
): PreviewViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun clear() = store.dispatch(action = Actions.Bookmark.Preview.Reset)

    override fun handle(previewData: PreviewData) {
        scope.launch(context = MainDispatcher) {
            when (val unboxed = previewData.unbox()) {
                is PreviewDataType.Text -> store.dispatch(action = Actions.Bookmark.Preview.Text(content = unboxed.content))
                is PreviewDataType.Link -> store.dispatch(action = Actions.Bookmark.Preview.Link(url = unboxed.url))
                is PreviewDataType.Unsupported -> Unit
            }
        }
    }

    override fun save() {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Bookmark.Save(time = calendar.getTime()))
        }
    }

    override fun observePreviewState(callback: (Bookmark) -> Unit) {
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